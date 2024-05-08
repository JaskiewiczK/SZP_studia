package szp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import szp.model.EmployeeModel;
import szp.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szp.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/hr")
public class HRController{
    private final EmployeeService employeeService;

    @Autowired
    public HRController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/")
    public ResponseEntity<Role> testHRController() {
        return ResponseEntity.ok(Role.HR);
    }

    @GetMapping("/employees")
    public String getEmployeesInfo(Model model) {

        List<EmployeeModel> employees = employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        return "hr/employees";
    }







    @GetMapping("/workstations")
    public ResponseEntity<String> getWorkstationsInfo() {
        return null;
    }

    @PostMapping("/workstations")
    public ResponseEntity<String> addWorkstation(@RequestBody String employeeRequest){
        return null;
    }

    @GetMapping("/clients")
    public ResponseEntity<String> getClients(){
        return null;
    }

    @PostMapping("/assignments")
    public ResponseEntity<String> addAssignment(@RequestBody String employeeRequest){
        return null;
    }

    @GetMapping("/vacations")
    public ResponseEntity<String> getVacations(@RequestBody String vacationPeriodRequest){
        return null;
    }




}
