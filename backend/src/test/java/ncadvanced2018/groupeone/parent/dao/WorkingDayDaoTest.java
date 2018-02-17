package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkingDayDaoTest {

    @Autowired
    private WorkingDayDao workingDayDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    @Rollback()
    public void insertWorkingDayTest() {
        WorkingDay expected = new RealWorkingDay();
        expected.setUser(userDao.findById(1L));
        expected.setWorkdayEnd(LocalDateTime.now());
        expected.setWorkdayStart(LocalDateTime.now());
        expected.setWordedOut(false);

        workingDayDao.create(expected);
        WorkingDay actual = workingDayDao.findById(expected.getId());

        Assert.assertEquals(expected.getWorkdayStart(), actual.getWorkdayStart());
    }

    @Test
    @Transactional
    @Rollback()
    public void updateWorkingDayTest() {
        WorkingDay expected = workingDayDao.findById(10L);
        expected.setUser(userDao.findById(1L));
        expected.setWorkdayEnd(LocalDateTime.now());
        expected.setWorkdayStart(LocalDateTime.now());
        expected.setWordedOut(true);

        workingDayDao.update(expected);
        WorkingDay actual = workingDayDao.findById(10L);

        Assert.assertEquals(expected.getWorkdayEnd(), actual.getWorkdayEnd());
    }

    @Test
    @Transactional
    @Rollback()
    public void deleteWorkingDayTest() {
        WorkingDay expected = new RealWorkingDay();
        expected.setUser(userDao.findById(1L));
        expected.setWorkdayEnd(LocalDateTime.now());
        expected.setWorkdayStart(LocalDateTime.now());
        expected.setWordedOut(true);

        WorkingDay actual = workingDayDao.create(expected);
        workingDayDao.delete(actual);

        Assert.assertNull(workingDayDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback()
    public void findWorkingDayByIdTest() {
        Long expected = 11L;
        WorkingDay actual = workingDayDao.findById(expected);

        log.info("Fetched Working Day by id: {}", actual.getId());
        Assert.assertEquals(actual.getId(), expected);
    }
}
