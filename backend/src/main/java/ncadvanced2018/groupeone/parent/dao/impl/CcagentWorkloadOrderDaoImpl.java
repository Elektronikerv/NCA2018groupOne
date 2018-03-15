package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.CcagentWorkloadOrderDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import ncadvanced2018.groupeone.parent.dto.Fulfillment;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
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
import java.util.*;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
public class CcagentWorkloadOrderDaoImpl implements CcagentWorkloadOrderDao<Fulfillment, CcagentWorkload> {

    private NamedParameterJdbcOperations jdbcTemplate;
    private CcagentWorkloadOrderDaoImpl.CcagentWorkloadOrderExtractor ccagentWorkloadOrderExtractor;
    private CcagentWorkloadOrderDaoImpl.FulfillmentOrderExtractor fulfillmentOrderExtractor;
    private QueryService queryService;

    @Autowired
    public CcagentWorkloadOrderDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }


    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.ccagentWorkloadOrderExtractor = new CcagentWorkloadOrderExtractor();
        this.fulfillmentOrderExtractor = new FulfillmentOrderExtractor();
    }

    @Override
    public List<CcagentWorkload> findWorkingCcagents() {
        String findByStatusByCourierQuery = queryService.getQuery("ccagent.findWorkingCcagents");

        List<CcagentWorkload> fulfillmentOrders = jdbcTemplate.query(findByStatusByCourierQuery, ccagentWorkloadOrderExtractor);
        return fulfillmentOrders.isEmpty() ? Collections.emptyList() : fulfillmentOrders;

    }

    @Override
    public List<Fulfillment> findFulfillmentsForExecuting() {
        String findByStatusByCourierQuery = queryService.getQuery("ccagent.find_open_fullfilments");

        List<Fulfillment> fulfillmentOrders = jdbcTemplate.query(findByStatusByCourierQuery, fulfillmentOrderExtractor);
        return fulfillmentOrders.isEmpty() ? Collections.emptyList() : fulfillmentOrders;

    }


    @Override
    public List<Fulfillment> updateFulfillmentOrders(Queue<Fulfillment> fulfillmentOrders) {
        String update = queryService.getQuery("fulfillment_order.updateCcagent"); //
        List<SqlParameterSource> params = new LinkedList<>();

        while (fulfillmentOrders.peek() != null){
            Fulfillment fulfillment = fulfillmentOrders.poll();
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("id", fulfillment.getFulfillmentOrderId())
                    .addValue("ccagent_id", fulfillment.getCcagentId() );
            params.add(parameterSource);
        }

        jdbcTemplate.batchUpdate(update, params.toArray(new SqlParameterSource[]{}));
        return new LinkedList<>(fulfillmentOrders);
    }


    private final class CcagentWorkloadOrderExtractor implements ResultSetExtractor<List<CcagentWorkload>>, TimestampExtractor {

        @Override
        public List<CcagentWorkload> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<CcagentWorkload> ccagentWorkloads = new LinkedList<>();
            while (rs.next()) {
                CcagentWorkload ccagentWorkload = new CcagentWorkload();

                ccagentWorkload.setId(rs.getLong("id"));
                ccagentWorkload.setProcessingOrders(rs.getInt("processing_orders"));
                ccagentWorkload.setDailyPerformance(rs.getInt("daily_performance"));
                ccagentWorkload.setWorkdayEnd(getLocalDateTime(rs.getTimestamp("workday_end")));
                ccagentWorkloads.add(ccagentWorkload);
            }
            return ccagentWorkloads;
        }
    }

    private final class FulfillmentOrderExtractor implements ResultSetExtractor<List<Fulfillment>>, TimestampExtractor {

        @Override
        public List<Fulfillment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Fulfillment> fulfillments = new LinkedList<>();
            while (rs.next()) {
                Fulfillment fulfillment = new Fulfillment();
                fulfillment.setRowNum(rs.getLong("row_number"));
                fulfillment.setFulfillmentOrderId(rs.getLong("fulfillment_order_id"));
                fulfillment.setCcagentId(rs.getLong("ccagent_id"));
                fulfillment.setClientRole(Role.valueOf(rs.getLong("role_id")));
                fulfillment.setCreationTime(getLocalDateTime(rs.getTimestamp("creation_time")));
                fulfillments.add(fulfillment);
            }
            return fulfillments;
        }
    }

}
