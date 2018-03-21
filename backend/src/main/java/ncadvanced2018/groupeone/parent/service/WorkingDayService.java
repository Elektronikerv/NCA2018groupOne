package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;

import java.util.List;


public interface WorkingDayService {

    WorkingDay create(WorkingDay workingDay);

    MonthCalendarDay create(MonthCalendarDay monthCalendarDay);

    WorkingDay findById(Long id);

    List<WorkingDay> findByUserId(Long id);

    List<WorkingDay> findActualByUserId(Long id);

    List<WorkingDay> getAll();

    WorkingDay update(WorkingDay workingDay);

    MonthCalendarDay update(MonthCalendarDay workingDay);

    boolean delete(WorkingDay workingDay);

    boolean deleteById(Long id);

}
