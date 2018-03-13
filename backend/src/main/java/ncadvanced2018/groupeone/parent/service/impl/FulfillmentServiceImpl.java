package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FulfillmentServiceImpl implements FulfillmentService {

    private FulfillmentOrderDao fulfillmentOrderDao;

    @Autowired
    public FulfillmentServiceImpl(FulfillmentOrderDao fulfillmentOrderDao) {

        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }



    @Override
    public List<FulfillmentOrder> findFulfillmentForCcagent(Long ccagentId) {
        return fulfillmentOrderDao.findFulfillmentForCcagent(ccagentId);
    }

    @Override
    public FulfillmentOrder findById(Long id) {
        return fulfillmentOrderDao.findById(id);
    }


}
