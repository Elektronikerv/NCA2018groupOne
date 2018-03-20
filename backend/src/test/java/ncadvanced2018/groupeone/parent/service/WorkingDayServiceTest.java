package ncadvanced2018.groupeone.parent.service;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkingDayServiceTest {
    @Autowired
    private WorkingDayService workingDayService;

    @Test
    @Transactional
    @Rollback
    public void createTest(){
        LocalDateTime start = Timestamp.valueOf("2018-03-16 09:00:00").toLocalDateTime();
        LocalDateTime end = Timestamp.valueOf("2018-03-16 18:00:00").toLocalDateTime();
        Long user_id = 9L;
        WorkingDay wDay = new RealWorkingDay();
        User user = new RealUser();
        user.setId(user_id);
        wDay.setUser(user);
        wDay.setWorkdayStart(start);
        wDay.setWorkdayEnd(end);
        wDay.setWordedOut(false);
        WorkingDay createdDay = workingDayService.create(wDay);

        Assert.assertEquals(user_id, createdDay.getUser().getId(), 0);
        Assert.assertEquals(start, createdDay.getWorkdayStart());
        Assert.assertEquals(end, createdDay.getWorkdayEnd());
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdTest(){
        Long expectedId = 1L;
        WorkingDay resultDay = workingDayService.findById(1L);

        Assert.assertEquals(expectedId, resultDay.getId(), 0);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUserIdTest(){
        Long user_id = 9L;
        User user = new RealUser();
        user.setId(user_id);
        LocalDateTime startOne = Timestamp.valueOf("2018-03-16 09:00:00").toLocalDateTime();
        LocalDateTime endOne = Timestamp.valueOf("2018-03-16 18:00:00").toLocalDateTime();
        LocalDateTime startTwo = Timestamp.valueOf("2018-03-17 09:00:00").toLocalDateTime();
        LocalDateTime endTwo = Timestamp.valueOf("2018-03-17 18:00:00").toLocalDateTime();
        WorkingDay wDayOne = new RealWorkingDay();
        WorkingDay wDayTwo = new RealWorkingDay();

        wDayOne.setUser(user);
        wDayOne.setWorkdayStart(startOne);
        wDayOne.setWorkdayEnd(endOne);
        wDayOne.setWordedOut(false);
        workingDayService.create(wDayOne);

        wDayTwo.setUser(user);
        wDayTwo.setWorkdayStart(startTwo);
        wDayTwo.setWorkdayEnd(endTwo);
        wDayTwo.setWordedOut(false);
        workingDayService.create(wDayTwo);

        List<WorkingDay> wDays = workingDayService.findByUserId(user_id);
        int size = wDays.size();
        int expectedSize = 2;

        Assert.assertEquals(expectedSize, size);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest(){
        LocalDateTime start = Timestamp.valueOf("2018-03-16 09:00:00").toLocalDateTime();
        LocalDateTime end = Timestamp.valueOf("2018-03-16 18:00:00").toLocalDateTime();
        Long user_id = 9L;
        WorkingDay wDay = new RealWorkingDay();
        User user = new RealUser();
        user.setId(user_id);
        wDay.setUser(user);
        wDay.setWorkdayStart(start);
        wDay.setWorkdayEnd(end);
        wDay.setWordedOut(false);
        WorkingDay createdDay = workingDayService.create(wDay);

        LocalDateTime startTest = Timestamp.valueOf("2018-03-18 09:00:00").toLocalDateTime();
        LocalDateTime endTest = Timestamp.valueOf("2018-03-18 18:00:00").toLocalDateTime();
        createdDay.setWorkdayStart(startTest);
        createdDay.setWorkdayEnd(endTest);
        WorkingDay testDay = workingDayService.update(createdDay);

        Assert.assertEquals(startTest, testDay.getWorkdayStart());
        Assert.assertEquals(endTest, testDay.getWorkdayEnd());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest(){
        LocalDateTime start = Timestamp.valueOf("2018-03-16 09:00:00").toLocalDateTime();
        LocalDateTime end = Timestamp.valueOf("2018-03-16 18:00:00").toLocalDateTime();
        Long user_id = 9L;
        WorkingDay wDay = new RealWorkingDay();
        User user = new RealUser();
        user.setId(user_id);
        wDay.setUser(user);
        wDay.setWorkdayStart(start);
        wDay.setWorkdayEnd(end);
        wDay.setWordedOut(false);

        WorkingDay createdDay = workingDayService.create(wDay);
        boolean isDeleted = workingDayService.delete(createdDay);
        WorkingDay actualDay = workingDayService.findById(createdDay.getId());

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualDay);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteByIdTest(){
        LocalDateTime start = Timestamp.valueOf("2018-03-16 09:00:00").toLocalDateTime();
        LocalDateTime end = Timestamp.valueOf("2018-03-16 18:00:00").toLocalDateTime();
        Long user_id = 9L;
        WorkingDay wDay = new RealWorkingDay();
        User user = new RealUser();
        user.setId(user_id);
        wDay.setUser(user);
        wDay.setWorkdayStart(start);
        wDay.setWorkdayEnd(end);
        wDay.setWordedOut(false);

        WorkingDay createdDay = workingDayService.create(wDay);
        Long dayId = createdDay.getId();
        boolean isDeleted = workingDayService.deleteById(dayId);
        WorkingDay actualDay = workingDayService.findById(dayId);

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualDay);
    }

}
