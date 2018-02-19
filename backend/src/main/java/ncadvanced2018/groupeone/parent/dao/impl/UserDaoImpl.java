package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyAddress;
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
import java.util.Objects;

@Repository
@NoArgsConstructor
public class UserDaoImpl implements UserDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert userInsert;
    private UserWithDetailExtractor userWithDetailExtractor;
    private AddressDao addressDao;
    private QueryService queryService;

    @Autowired
    public UserDaoImpl(AddressDao addressDao, QueryService queryService) {
        this.addressDao = addressDao;
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.userWithDetailExtractor = new UserWithDetailExtractor();
    }

    @Override
    public User create(User user) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("password", user.getPassword())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("email", user.getEmail())
                .addValue("address_id", Objects.isNull(user.getAddress()) ? null : user.getAddress().getId())
                .addValue("manager_id", Objects.isNull(user.getManager()) ? null : user.getManager().getId())
                .addValue("registration_date", Objects.isNull(user.getRegistrationDate()) ? null : Timestamp.valueOf(user.getRegistrationDate()));
        Long id = userInsert.executeAndReturnKey(sqlParameters).longValue();
        user.setId(id);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String findUserByEmailQuery = queryService.getQuery("user.findByEmail");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", email);
        List <User> users = jdbcTemplate.query(findUserByEmailQuery, parameterSource, userWithDetailExtractor);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("user.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <User> users = jdbcTemplate.query(findUserByIdQuery, parameterSource, userWithDetailExtractor);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean deleteByEmail(String email) {
        String deleteByEmail = queryService.getQuery("user.deleteByEmail");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", email);
        int deletedRows = jdbcTemplate.update(deleteByEmail, parameterSource);
        return deletedRows > 0;
    }

    @Override
    public boolean update(User user) {
        String update = queryService.getQuery("user.update");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("password", user.getPassword())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("email", user.getEmail())
                .addValue("address_id", Objects.isNull(user.getAddress()) ? null : user.getAddress().getId())
                .addValue("manager_id", Objects.isNull(user.getManager()) ? null : user.getManager().getId())
                .addValue("registration_date", Timestamp.valueOf(user.getRegistrationDate()));
        int updatedRows = jdbcTemplate.update(update, sqlParameters);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(User user) {
        return delete(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("user.deleteById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    @Override
    public List <User> findEmployeesByLastName(String lastName) {
        String findEmployeesByLastNameQuery = queryService.getQuery("user.findEmployeesByLastName");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("last_name", lastName);
        List<User> employees = jdbcTemplate.query(findEmployeesByLastNameQuery, parameterSource, userWithDetailExtractor);
        return employees;
    }

    @Override
    public List <User> findEmployeesByManager(User manager) {
        String findEmployeesByManagerQuery = queryService.getQuery("user.findEmployeesByManager");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", manager.getId());
        List<User> employees = jdbcTemplate.query(findEmployeesByManagerQuery, parameterSource, userWithDetailExtractor);
        return employees;
    }

    @Override
    public List <User> findAllEmployees() {
        String findAllEmployeesQuery = queryService.getQuery("user.findEmployees");
        List <User> employees = jdbcTemplate.query(findAllEmployeesQuery, userWithDetailExtractor);
        return employees;
    }

    @Override
    public boolean createEmployee(User employee) {
        String insertEmployeeQuery = queryService.getQuery("user.createEmployee");
        //Street, house, flat, floor when null?
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", employee.getEmail())
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("password", employee.getPassword())
                .addValue("phone_number", employee.getPhoneNumber())
                .addValue("registration_date", employee.getRegistrationDate())
                .addValue("house", Objects.isNull(employee.getAddress()) ? null : employee.getAddress().getHouse())
                .addValue("flat", Objects.isNull(employee.getAddress()) ? null : employee.getAddress().getFlat())
                .addValue("floor", Objects.isNull(employee.getAddress()) ? null : employee.getAddress().getFloor())
                .addValue("street", Objects.isNull(employee.getAddress()) ? null : employee.getAddress().getStreet());

        int insertedRows = jdbcTemplate.update(insertEmployeeQuery, parameterSource);
        return insertedRows == 1;
    }

    private final class UserWithDetailExtractor implements ResultSetExtractor <List <User>>, TimestampExtractor {

        @Override
        public List <User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <User> users = new ArrayList <>();
            while (rs.next()) {
                User user = new RealUser();
                user.setId(rs.getLong("id"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));

                Long managerId = rs.getLong("manager_id");
                if (managerId != 0) {
                    User manager = new ProxyUser(UserDaoImpl.this);
                    manager.setId(managerId);
                    user.setManager(manager);
                }

                Long addressId = rs.getLong("address_id");
                if (addressId != 0) {
                    Address address = new ProxyAddress(addressDao);
                    address.setId(addressId);
                    user.setAddress(address);
                }

                user.setRegistrationDate(getLocalDateTime(rs.getTimestamp("registration_date")));
                users.add(user);
            }
            return users;
        }
    }
}