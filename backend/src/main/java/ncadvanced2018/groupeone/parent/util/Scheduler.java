package ncadvanced2018.groupeone.parent.util;

import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    private UserService userService;

    @Autowired
    public Scheduler(UserService userService) {
        this.userService = userService;
    }

    //    @Scheduled(fixedRate = 5 * 1000)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void checkUnverifiedUsers() {
        userService.deleteUnverifiedUsers();
    }

}
