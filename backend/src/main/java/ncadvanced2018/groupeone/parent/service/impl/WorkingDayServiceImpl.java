package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;
import ncadvanced2018.groupeone.parent.service.WorkingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WorkingDayServiceImpl implements WorkingDayService {

    private WorkingDayDao workingDayDao;
    private UserDao userDao;

    @Autowired
    public WorkingDayServiceImpl(WorkingDayDao workingDayDao, UserDao userDao) {
        this.workingDayDao = workingDayDao;
        this.userDao = userDao;
    }


    @Override
    public WorkingDay create(WorkingDay workingDay) {

        if (workingDay == null) {
            log.info("WorkingDay object is null by creation");
            throw new EntityNotFoundException("WorkingDay object is null");
        }

        if (userDao.findById(workingDay.getUser().getId()) == null) {
            log.info("No such user entity");
            throw new EntityNotFoundException("User with such ID is not found");
        }

        return workingDayDao.create(workingDay);
    }

    @Override
    public MonthCalendarDay create(MonthCalendarDay monthCalendarDay) {

        WorkingDay workingDay = new RealWorkingDay();
        workingDay.setWordedOut(monthCalendarDay.isWorkedOut());
        workingDay.setWorkdayEnd(monthCalendarDay.getEndWork());
        workingDay.setWorkdayStart(monthCalendarDay.getStartWork());
        workingDay.setUser(userDao.findById(monthCalendarDay.getUserId()));

        System.out.println(monthCalendarDay);

        WorkingDay created = workingDayDao.create(workingDay);

        System.out.println(created);

        return workingDayDao.findMonthCalendarByUser(monthCalendarDay.getId()).get((int) monthCalendarDay.getId() - 1);

    }

    @Override
    public WorkingDay findById(Long id) {
        if (id <= 0) {
            log.info("Illegal ID");
            throw new IllegalArgumentException("Illegal workingDay ID");
        }

        return workingDayDao.findById(id);
    }

    @Override
    public List<WorkingDay> findByUserId(Long id) {
        if (id <= 0) {
            log.info("Illegal user ID");
            throw new IllegalArgumentException("Illegal user ID");
        }
        if (userDao.findById(id) == null) {
            log.info("No such user entity");
            throw new EntityNotFoundException("User with such ID is not found");
        }

        return workingDayDao.findByUserId(id);
    }

    @Override
    public List<WorkingDay> findActualByUserId(Long id) {
        if (id <= 0) {
            log.info("Illegal user ID");
            throw new IllegalArgumentException("Illegal user ID");
        }
        if (userDao.findById(id) == null) {
            log.info("No such user entity");
            throw new EntityNotFoundException("User with such ID is not found");
        }

        return workingDayDao.findActualByUserId(id);
    }

    @Override
    public List<WorkingDay> getAll() {
        return workingDayDao.getAll();
    }

    @Override
    public WorkingDay update(WorkingDay workingDay) {
        if (workingDay == null) {
            log.info("WorkingDay object is null");
            throw new EntityNotFoundException("Working Day object is null");
        }

        if (userDao.findById(workingDay.getUser().getId()) == null) {
            log.info("No such user entity");
            throw new EntityNotFoundException("User with such ID is not found");
        }

        return workingDayDao.update(workingDay);
    }

    @Override
    public MonthCalendarDay update(MonthCalendarDay workingDay) {
        if (workingDay == null) {
            log.info("WorkingDay object is null");
            throw new EntityNotFoundException("Working Day object is null");
        }

        WorkingDay workingToDao = new RealWorkingDay();
        workingToDao.setUser(userDao.findById(workingDay.getUserId()));
        workingToDao.setWorkdayStart(workingDay.getStartWork());
        workingToDao.setWorkdayEnd(workingDay.getEndWork());
        workingToDao.setWordedOut(workingDay.isWorkedOut());
        workingToDao.setId(workingDay.getWdId());

        WorkingDay updated = workingDayDao.update(workingToDao);

        return workingDayDao.findMonthCalendarByUser(updated.getUser().getId()).get((int) workingDay.getId() - 1);
    }

    @Override
    public boolean delete(WorkingDay workingDay) {
        if (workingDay == null) {
            log.info("WorkingDay object is null");
            throw new EntityNotFoundException("Working Day object is null");
        }

        return workingDayDao.delete(workingDay);
    }

    @Override
    public boolean deleteById(Long id) {
        if (id <= 0) {
            log.info("Illegal ID");
            throw new IllegalArgumentException("Illegal workingDay ID");
        }
        WorkingDay workingDay = workingDayDao.findById(id);
        if (workingDayDao.findById(workingDay.getId()) == null) {
            log.info("No such Working Day entity");
            throw new NoSuchEntityException("WorkingDay with such ID is not found");
        }

        return workingDayDao.delete(id);
    }

}
