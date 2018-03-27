package ncadvanced2018.groupeone.parent.util;

public interface OrderFlowScheduler {


    void reopenUncompletedOrdersForYesterday();

    void deleteObsoleteDrafts();

    void transitionFromDeliveredToFeedback();
}
