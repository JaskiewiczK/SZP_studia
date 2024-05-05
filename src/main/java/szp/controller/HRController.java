package szp.controller;

import szp.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class HRController{
    @GetMapping("/")
    public ResponseEntity<Role> testHRController() {
        return ResponseEntity.ok(Role.HR);
    }

    @GetMapping("/employees")
    public ResponseEntity<String> getEmployeesInfo() {
        return null;
    }

    @PutMapping("/employees")
    public ResponseEntity<String> addEmployee(@RequestBody String employeeRequest){
        return null;
    }

    @DeleteMapping("/employees")
    public ResponseEntity<String> removeEmployee(@RequestBody String employeeID){
        return null;
    }

    @GetMapping("/workstations")
    public ResponseEntity<String> getWorkstationsInfo() {
        return null;
    }

    @PutMapping("/workstations")
    public ResponseEntity<String> addWorkstation(@RequestBody String employeeRequest){
        return null;
    }

    @GetMapping("/clients")
    public ResponseEntity<String> getClients(){
        return null;
    }

    @PutMapping("/assignments")
    public ResponseEntity<String> addAssignment(@RequestBody String employeeRequest){
        return null;
    }

    @GetMapping("/vacations")
    public ResponseEntity<String> getVacations(@RequestBody String vacationPeriodRequest){
        return null;
    }




}
