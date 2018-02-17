package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;

import java.time.LocalDateTime;

public class ProxyWorkingDay implements WorkingDay {
    private WorkingDay realWorkingDay;
    private WorkingDayDao dao;
    private Long id;

    public ProxyWorkingDay(WorkingDayDao dao) {
        this.dao = dao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public User getUser() {
        return getRealWorkingDay().getUser();
    }

    @Override
    public void setUser(User user) {
        getRealWorkingDay().setUser(user);
    }

    @Override
    public LocalDateTime getWorkdayStart() {
        return getRealWorkingDay().getWorkdayStart();
    }

    @Override
    public void setWorkdayStart(LocalDateTime workdayStart) {
        getRealWorkingDay().setWorkdayStart(workdayStart);
    }

    @Override
    public LocalDateTime getWorkdayEnd() {
        return getRealWorkingDay().getWorkdayEnd();
    }

    @Override
    public void setWorkdayEnd(LocalDateTime workdayEnd) {
        getRealWorkingDay().setWorkdayEnd(workdayEnd);
    }

    @Override
    public Boolean getWordedOut() {
        return getRealWorkingDay().getWordedOut();
    }

    @Override
    public void setWordedOut(Boolean wordedOut) {
        getRealWorkingDay().setWordedOut(wordedOut);
    }

    private WorkingDay getRealWorkingDay() {
        if (realWorkingDay == null) {
            realWorkingDay = dao.findById(id);
        }
        return realWorkingDay;
    }
}
