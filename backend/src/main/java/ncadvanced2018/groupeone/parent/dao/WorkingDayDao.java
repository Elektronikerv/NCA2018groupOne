package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;

import java.util.List;

public interface WorkingDayDao extends CrudDao <WorkingDay, Long> {
    List<WorkingDay> findByUserId(Long id);

    List<WorkingDay> findActualByUserId(Long user_id);
    List<WorkingDay> getAll();

    List <MonthCalendarDay> findMonthCalendarByUser(Long id);
}
