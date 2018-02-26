package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.EmployeeEmailService;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/empl")
public class EmployeeController {

    private EmployeeEmailService emailService;
    private UserService userService;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeEmailService emailService, UserService userService, EmployeeService employeeService) {
        this.emailService = emailService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

//    @PostMapping("/registration")
//    public void createEmployee(@RequestBody User user) {
//        User createdUser = userService.create(user);
//        emailService.sendEmail(createdUser);
//    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody RealUser user) {
        User createdEmployee = employeeService.create(user);
        emailService.sendEmail(createdEmployee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> fetchEmployeesAll(){
        List<User> allEmployees = employeeService.findAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getEmployee(@PathVariable Long id){
        User employee = employeeService.findById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id){
        employeeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@RequestBody User employee){
        User updatedEmployee = employeeService.update(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.CREATED);
    }

}
