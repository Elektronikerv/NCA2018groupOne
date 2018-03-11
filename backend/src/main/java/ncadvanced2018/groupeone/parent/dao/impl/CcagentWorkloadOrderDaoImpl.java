package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.CcagentWorkloadOrderDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
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

@Repository
@NoArgsConstructor
public class CcagentWorkloadOrderDaoImpl implements CcagentWorkloadOrderDao<CcagentWorkload, FulfillmentOrder> {

    private NamedParameterJdbcOperations jdbcTemplate;
    private CcagentWorkloadOrderDaoImpl.CcagentWorkloadOrderExtractor ccagentWorkloadOrderExtractor;
    private QueryService queryService;

    @Autowired
    public CcagentWorkloadOrderDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }


    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.ccagentWorkloadOrderExtractor = new CcagentWorkloadOrderExtractor();
    }

//    @Override
//    public SortedSet<FulfillmentOrder> findWorkingCcagents() {
//        String findByStatusByCourierQuery = queryService.getQuery("ccagent.findWorkingCcagents");
//
//        SortedSet<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findByStatusByCourierQuery, fulfillmentOrderWithDetailExtractor);
//        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders;
//
//    }


//    @Override
    public List<FulfillmentOrder> updateFulfillmentOrders(List<FulfillmentOrder> fulfillmentOrders) {
        String update = queryService.getQuery("fulfillment_order.updateCcagent"); //

        SqlParameterSource parameterSources[] = new SqlParameterSource[fulfillmentOrders.size()];


        for (int i = 0; i < fulfillmentOrders.size(); i++) {
            FulfillmentOrder fulfillmentOrder = fulfillmentOrders.get(i);
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("id", fulfillmentOrder.getId())
                    .addValue("ccagent_id", fulfillmentOrder.getCcagent().getId());
            parameterSources[i] = parameterSource;
        }

        jdbcTemplate.batchUpdate(update, parameterSources);
        return fulfillmentOrders;

    }


    private final class CcagentWorkloadOrderExtractor implements ResultSetExtractor<SortedSet<CcagentWorkload>>, TimestampExtractor {

        @Override
        public SortedSet<CcagentWorkload> extractData(ResultSet rs) throws SQLException, DataAccessException {
            SortedSet<CcagentWorkload> ccagentWorkloads = new TreeSet<>();
            while (rs.next()) {
                CcagentWorkload ccagentWorkload = new CcagentWorkload();

                ccagentWorkload.setId(rs.getLong("id"));
                ccagentWorkload.setProcessingOrders(rs.getInt("processing_orders"));
                ccagentWorkload.setWorkdayEnd(getLocalDateTime(rs.getTimestamp("workday_end")));

            }
            return ccagentWorkloads;
        }
    }

    private final class FulfillmentOrderExtractor implements ResultSetExtractor<Queue<FulfillmentOrder>>, TimestampExtractor {

        @Override
        public Queue<FulfillmentOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Queue<FulfillmentOrder> fulfillmentOrders = new LinkedList<>();
            while (rs.next()) {
                FulfillmentOrder fulfillmentOrder = new RealFulfillmentOrder();

                fulfillmentOrder.setId(rs.getLong("id"));

            }
            return fulfillmentOrders;
        }
    }

}
