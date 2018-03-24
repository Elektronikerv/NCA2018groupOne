package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;
import ncadvanced2018.groupeone.parent.service.WorkingDayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class WorkingDayController {

    private WorkingDayService workingDayService;

    public WorkingDayController(WorkingDayService workingDayService){
        this.workingDayService = workingDayService;
    }

    @PostMapping("create/{id}")
    public ResponseEntity<WorkingDay> createWorkingDay(@RequestBody WorkingDay workingDay){
        WorkingDay createdWorkingDay = workingDayService.create(workingDay);
        return new ResponseEntity<>(createdWorkingDay, HttpStatus.CREATED);
    }

    @PostMapping("create/calendarDay")
    public ResponseEntity <MonthCalendarDay> createWorkingDay(@RequestBody @Valid MonthCalendarDay monthCalendarDay) {
        System.out.println(monthCalendarDay);
        MonthCalendarDay createdWorkingDay = workingDayService.create(monthCalendarDay);
        return new ResponseEntity <>(createdWorkingDay, HttpStatus.CREATED);
    }

    @GetMapping("days/{id}")
    public ResponseEntity<WorkingDay> getWorkingDayById(@PathVariable Long id){
        WorkingDay wDay = workingDayService.findById(id);
        return new ResponseEntity<>(wDay, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<WorkingDay>> getAll(){
        List<WorkingDay> wDays = workingDayService.getAll();
        return new ResponseEntity<>(wDays, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<WorkingDay>> getWorkingDaysByUserId(@PathVariable Long id){
        List<WorkingDay> wDays = workingDayService.findByUserId(id);
        return new ResponseEntity<>(wDays, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingDay> updateWorkingDay(@RequestBody WorkingDay workingDay){
        WorkingDay wDay = workingDayService.update(workingDay);
        return new ResponseEntity<>(wDay, HttpStatus.CREATED);
    }

    @PutMapping("update/calendarDay")
    public ResponseEntity <MonthCalendarDay> updateWorkingDay(@RequestBody @Valid MonthCalendarDay monthCalendarDay) {
        MonthCalendarDay result = workingDayService.update(monthCalendarDay);
        return new ResponseEntity <>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/calendarDay/{id}")
    public ResponseEntity deleteWorkingDay(@PathVariable Long id){
        workingDayService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
