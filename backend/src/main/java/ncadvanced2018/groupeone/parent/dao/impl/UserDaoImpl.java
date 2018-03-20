package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.RoleDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dto.EmpProfile;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyAddress;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyUser;
import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDateTime;
import java.util.*;

@Repository
@NoArgsConstructor
public class UserDaoImpl implements UserDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert userInsert;
    private UserWithDetailExtractor userWithDetailExtractor;
    private EmpProfileExtractor empProfileExtractor;
    private UserWithoutPasswordExtractor userWithoutPasswordExtractor;
    private AddressDao addressDao;
    private QueryService queryService;
    private RoleDao roleDao;
    @Value("5")
    private Long maxQuantityOfOrdersForOneCourier;

    @Autowired
    public UserDaoImpl(AddressDao addressDao, QueryService queryService, RoleDao roleDao) {
        this.addressDao = addressDao;
        this.queryService = queryService;
        this.roleDao = roleDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.userWithDetailExtractor = new UserWithDetailExtractor();
        this.empProfileExtractor = new EmpProfileExtractor();
        this.userWithoutPasswordExtractor = new UserWithoutPasswordExtractor();
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
                .addValue("registration_date", Objects.isNull(user.getRegistrationDate()) ? Timestamp.valueOf(LocalDateTime.now()) : Timestamp.valueOf(user.getRegistrationDate()));
        Long id = userInsert.executeAndReturnKey(sqlParameters).longValue();
        user.setId(id);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String findUserByEmailQuery = queryService.getQuery("user.findByEmail");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", email);
        List<User> users = jdbcTemplate.query(findUserByEmailQuery, parameterSource, userWithDetailExtractor);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("user.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<User> users = jdbcTemplate.query(findUserByIdQuery, parameterSource, userWithDetailExtractor);
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
    public User updateUserInfo(User user) {
        String update = queryService.getQuery("user.update_user_info");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("email", user.getEmail())
                .addValue("address_id", Objects.isNull(user.getAddress()) ? null : user.getAddress().getId())
                .addValue("manager_id", Objects.isNull(user.getManager()) ? null : user.getManager().getId());
        jdbcTemplate.update(update, sqlParameters);
        return findById(user.getId());
    }

    @Override
    public User update(User user) {
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
                .addValue("registration_date", Objects.isNull(user.getRegistrationDate()) ? Timestamp.valueOf(LocalDateTime.now()) : Timestamp.valueOf(user.getRegistrationDate()));
        jdbcTemplate.update(update, sqlParameters);
        return findById(user.getId());
    }

    @Override
    public User updatePassword(User user) {
        String update = queryService.getQuery("user.update_password");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("password", user.getPassword());
        jdbcTemplate.update(update, sqlParameters);
        return findById(user.getId());
    }

    @Override
    public boolean delete(User user) {
        return delete(user.getId());
    }


    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("user.deleteById");
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    @Override
    public boolean deleteRole(User user, Role role) {
        String deleteRole = queryService.getQuery("user.deleteRole");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("user_id", user.getId())
                .addValue("role_id", role.getId());
        int deletedRows = jdbcTemplate.update(deleteRole, sqlParameters);
        user.getRoles().remove(role);
        return deletedRows > 0;
    }

    @Override
    public boolean addRole(User user, Role role) {
        String addRole = queryService.getQuery("user.addRole");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("user_id", user.getId())
                .addValue("role_id", role.getId());
        int addRows = jdbcTemplate.update(addRole, sqlParameters);
        if (user.getRoles() != null) {
            user.getRoles().add(role);
        } else {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }
        return addRows > 0;
    }

    @Override
    public User updateClientRoleToVIP(User user) {
        String update = queryService.getQuery("user.update_client_role_to_vip");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("user_id", user.getId());
        jdbcTemplate.update(update, sqlParameters);
        return findById(user.getId());
    }

    @Override
    public User updateClientRoleToClient(User user) {
        String update = queryService.getQuery("user.update_client_role_to_client");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("user_id", user.getId());
        jdbcTemplate.update(update, sqlParameters);
        return findById(user.getId());
    }

    @Override
    public List<User> findEmployeesByLastName(String lastName) {
        String findEmployeesByLastNameQuery = queryService.getQuery("user.findEmployeesByLastName");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("last_name", lastName);
        return jdbcTemplate.query(findEmployeesByLastNameQuery, parameterSource, userWithDetailExtractor);
    }

    @Override
    public List<User> findEmployeesByManager(User manager) {
        String findEmployeesByManagerQuery = queryService.getQuery("user.findEmployeesByManager");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", manager.getId());
        return jdbcTemplate.query(findEmployeesByManagerQuery, parameterSource, userWithDetailExtractor);
    }

    @Override
    public List<EmpProfile> findEmployeesByManagerAndLastNameWithCounts(Long id, String lastName) {
        String findEmployeesByManagerWithCountsQuery = queryService.getQuery("user.findEmployeesByManagerAndLastNameWithCounts");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("last_name", lastName);
        return jdbcTemplate.query(findEmployeesByManagerWithCountsQuery, parameterSource, empProfileExtractor);
    }

    @Override
    public List<EmpProfile> findEmployeesByManagerWithCounts(Long id) {
        String findEmployeesByManagerWithCountsQuery = queryService.getQuery("user.findEmployeesByManagerWithCounts");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(findEmployeesByManagerWithCountsQuery, parameterSource, empProfileExtractor);
    }

    @Override
    public List<User> findAllManagers() {
        String findAllManagers = queryService.getQuery("user.findAllManagers");
        return jdbcTemplate.query(findAllManagers, userWithDetailExtractor);
    }

    @Override
    public User findManagerByEmployeeId(Long employeeId) {
        String findManagerByEmployeeId = queryService.getQuery("user.findManagerByEmployeeId");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("employeeId", employeeId);
        List<User> users = jdbcTemplate.query(findManagerByEmployeeId, parameterSource, userWithDetailExtractor);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findAllEmployees() {
        String findAllEmployeesQuery = queryService.getQuery("user.findEmployees");
        return jdbcTemplate.query(findAllEmployeesQuery, userWithDetailExtractor);
    }

    @Override
    public List<User> findAllCouriers() {
        String findAllCouriersQuery = queryService.getQuery("courier.findAllCouriers");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("courier_role_id", Role.COURIER.getId())
                .addValue("execution_status_id", OrderStatus.EXECUTION.getId())
                .addValue("delivering_status_id", OrderStatus.DELIVERING.getId());
        return jdbcTemplate.query(findAllCouriersQuery, parameterSource, userWithoutPasswordExtractor);
    }

    public List<User> findAllAvailableCouriers() {
        String findAllAvailableCouriers = queryService.getQuery("courier.findAllAvailableCouriers");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("courier_role_id", Role.COURIER.getId())
                .addValue("execution_status_id", OrderStatus.EXECUTION.getId())
                .addValue("delivering_status_id", OrderStatus.DELIVERING.getId())
                .addValue("max_orders", maxQuantityOfOrdersForOneCourier);
        return jdbcTemplate.query(findAllAvailableCouriers, parameterSource, userWithoutPasswordExtractor);
    }

    public List<User> findAllFreeCouriers() {
        String findAllFreeCouriers = queryService.getQuery("courier.findAllFreeCouriers");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("courier_role_id", Role.COURIER.getId())
                .addValue("execution_status_id", OrderStatus.EXECUTION.getId())
                .addValue("delivering_status_id", OrderStatus.DELIVERING.getId());
        return jdbcTemplate.query(findAllFreeCouriers, parameterSource, userWithoutPasswordExtractor);
    }

    private final class UserWithDetailExtractor implements ResultSetExtractor<List<User>>, TimestampExtractor {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new RealUser();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setRoles(roleDao.findByUserId(user.getId()));


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

                Long currentPositionId = rs.getLong("current_position_id");
                if (currentPositionId != 0) {
                    Address address = new ProxyAddress(addressDao);
                    address.setId(currentPositionId);
                    user.setCurrentPosition(address);
                }

                user.setRegistrationDate(getLocalDateTime(rs.getTimestamp("registration_date")));
                users.add(user);
            }
            return users;
        }
    }

    private final class UserWithoutPasswordExtractor implements ResultSetExtractor<List<User>>, TimestampExtractor {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new RealUser();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setRoles(roleDao.findByUserId(user.getId()));


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

                Long currentPositionId = rs.getLong("current_position_id");
                if (currentPositionId != 0) {
                    Address address = new ProxyAddress(addressDao);
                    address.setId(currentPositionId);
                    user.setCurrentPosition(address);
                }

                user.setRegistrationDate(getLocalDateTime(rs.getTimestamp("registration_date")));
                users.add(user);
            }
            return users;
        }
    }

    private final class EmpProfileExtractor implements ResultSetExtractor<List<EmpProfile>> {

        @Override
        public List<EmpProfile> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<EmpProfile> empProfiles = new ArrayList<>();
            while (rs.next()) {
                EmpProfile empProfile = new EmpProfile();
                empProfile.setId(rs.getLong("id"));
                empProfile.setFirstName(rs.getString("first_name"));
                empProfile.setLastName(rs.getString("last_name"));
                empProfile.setRoles(roleDao.findByUserId(empProfile.getId()));
                empProfile.setCcagentProcessingOrdersToday(rs.getLong("processing"));
                empProfile.setCcagentCancelledOrConfirmedOrdersToday(rs.getLong("confirmed_cancelled"));
                empProfile.setCourierDeliveringOrExecutionOrdersToday(rs.getLong("delivering_execution"));
                empProfile.setCourierDeliveredOrProblemOrdersToday(rs.getLong("delivered_other"));
                empProfile.setCountWorkingDays(rs.getLong("working_days"));
                empProfile.setWorkingNow(rs.getBoolean("working_now"));
                empProfiles.add(empProfile);
            }
            return empProfiles;
        }
    }
}