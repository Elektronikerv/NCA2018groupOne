package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.service.FulfillmentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FulfillmentOrderServiceImpl implements FulfillmentOrderService {

    private FulfillmentOrderDao fulfillmentOrderDao;

    @Autowired
    public FulfillmentOrderServiceImpl(FulfillmentOrderDao fulfillmentOrderDao) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }

    @Override
    public FulfillmentOrder create(FulfillmentOrder fulfillmentOrder) {
        return null;
    }

    @Override
    public FulfillmentOrder findById(Long id) {
        return null;
    }

    @Override
    public FulfillmentOrder update(FulfillmentOrder fulfillmentOrder) {
        return null;
    }

    @Override
    public boolean delete(FulfillmentOrder fulfillmentOrder) {
        return false;
    }

    @Override
    public List<FulfillmentOrder> findFulfillmentOrderByStatusByCourier(Long statusId, Long courierId) {
        if (statusId == null) {
            log.info("Parameter statusId is null in moment of finding by status by courier");
            throw new IllegalArgumentException();
        }
        if (courierId == null) {
            log.info("Parameter courierId is null in moment of finding by status by courier");
            throw new IllegalArgumentException();
        }
        return fulfillmentOrderDao.findByStatusByCourier(statusId, courierId);
    }
}


