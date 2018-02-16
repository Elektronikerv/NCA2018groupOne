package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class WorkingDayDaoImpl implements WorkingDayDao {

    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert workingDayInsert;
    private WorkingDayWithDetailExtractor workingDayWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public WorkingDayDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.workingDayInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("working_days")
                .usingGeneratedKeyColumns("id");
        workingDayWithDetailExtractor = new WorkingDayWithDetailExtractor();
    }

    @Override
    public WorkingDay create(WorkingDay entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", entity.getUser())
                .addValue("workday_start", entity.getWorkdayStart())
                .addValue("workday_end", entity.getWorkdayEnd())
                .addValue("worded_out", entity.getWordedOut());
        Long id = workingDayInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public WorkingDay findById(Long id) {
        String findUserByIdQuery  = queryService.getQuery("working_day.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<WorkingDay> workingDays = jdbcTemplate.query(findUserByIdQuery, parameterSource, workingDayWithDetailExtractor);
        if (workingDays.isEmpty()) {
            return null;
        }
        return workingDays.get(0);
    }
    @Override
    public boolean update(WorkingDay entity) {
        String update = queryService.getQuery("working_day.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("user_id", entity.getUser())
                .addValue("workday_start", entity.getWorkdayStart())
                .addValue("workday_end", entity.getWorkdayEnd())
                .addValue("worded_out", entity.getWordedOut());

        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(WorkingDay entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById  = queryService.getQuery("working_day.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private static final class WorkingDayWithDetailExtractor implements ResultSetExtractor<List<WorkingDay>>, TimestampExtractor {

        @Autowired
        private UserDao userDao;

        @Override
        public List<WorkingDay> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<WorkingDay> workingDays = new ArrayList<>();
            while (rs.next()) {
                WorkingDay workingDay = new WorkingDay();
                workingDay.setId(rs.getLong("id"));
                workingDay.setUser(userDao.findById(rs.getLong("user_id")));
                workingDay.setWorkdayStart(getLocalDateTime(rs.getTimestamp("workday_start")));
                workingDay.setWorkdayEnd(getLocalDateTime(rs.getTimestamp("workday_end")));
                workingDay.setWordedOut((rs.getBoolean("worked_out")));
                workingDays.add(workingDay);
            }
            return workingDays;
        }
    }

}


