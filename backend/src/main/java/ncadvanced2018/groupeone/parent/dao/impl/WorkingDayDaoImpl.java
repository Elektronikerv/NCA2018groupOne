package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyUser;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
public class WorkingDayDaoImpl implements WorkingDayDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert workingDayInsert;
    private WorkingDayWithDetailExtractor workingDayWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;

    @Autowired
    public WorkingDayDaoImpl(QueryService queryService, UserDao userDao) {
        this.queryService = queryService;
        this.userDao = userDao;
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
    public WorkingDay create(WorkingDay workingDay) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", workingDay.getUser().getId())
                .addValue("workday_start", Timestamp.valueOf(workingDay.getWorkdayStart()))
                .addValue("workday_end", Timestamp.valueOf(workingDay.getWorkdayEnd()))
                .addValue("worded_out", workingDay.getWordedOut());
        Long id = workingDayInsert.executeAndReturnKey(parameterSource).longValue();
        workingDay.setId(id);
        return workingDay;
    }

    @Override
    public WorkingDay findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("working_day.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<WorkingDay> workingDays = jdbcTemplate.query(findUserByIdQuery, parameterSource, workingDayWithDetailExtractor);
        return workingDays.isEmpty() ? null : workingDays.get(0);
    }

    @Override
    public WorkingDay update(WorkingDay workingDay) {
        String update = queryService.getQuery("working_day.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", workingDay.getId())
                .addValue("user_id", workingDay.getUser().getId())
                .addValue("workday_start", Timestamp.valueOf(workingDay.getWorkdayStart()))
                .addValue("workday_end", Timestamp.valueOf(workingDay.getWorkdayEnd()))
                .addValue("worked_out", workingDay.getWordedOut());
        jdbcTemplate.update(update, parameterSource);
        return workingDay;
    }

    @Override
    public boolean delete(WorkingDay workingDay) {
        return delete(workingDay.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("working_day.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class WorkingDayWithDetailExtractor implements ResultSetExtractor<List<WorkingDay>>, TimestampExtractor {

        @Override
        public List<WorkingDay> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<WorkingDay> workingDays = new ArrayList<>();
            while (rs.next()) {
                WorkingDay workingDay = new RealWorkingDay();
                workingDay.setId(rs.getLong("id"));

                Long userId = rs.getLong("user_id");
                if (userId != 0) {
                    User user = new ProxyUser(userDao);
                    user.setId(userId);
                    workingDay.setUser(user);
                }

                workingDay.setWorkdayStart(getLocalDateTime(rs.getTimestamp("workday_start")));
                workingDay.setWorkdayEnd(getLocalDateTime(rs.getTimestamp("workday_end")));
                workingDay.setWordedOut(rs.getBoolean("worked_out"));
                workingDays.add(workingDay);
            }
            return workingDays;
        }
    }
}