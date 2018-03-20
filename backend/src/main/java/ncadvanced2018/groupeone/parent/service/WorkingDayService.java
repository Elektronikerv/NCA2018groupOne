package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;

import java.util.List;


public interface WorkingDayService {

    WorkingDay create(WorkingDay workingDay);

    WorkingDay findById(Long id);

    List<WorkingDay> findByUserId(Long id);

    List<WorkingDay> findActualByUserId(Long id);

    List<WorkingDay> getAll();

    WorkingDay update(WorkingDay workingDay);

    boolean delete(WorkingDay workingDay);

    boolean deleteById(Long id);
}
