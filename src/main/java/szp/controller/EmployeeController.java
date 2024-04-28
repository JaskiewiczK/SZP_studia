package szp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szp.model.Role;

@RestController
@RequestMapping("/employee")
public class EmployeeController{
    @GetMapping("/")
    public ResponseEntity<Role> testEmployeeController() {
        return ResponseEntity.ok(Role.EMPLOYEE);
    }
}
