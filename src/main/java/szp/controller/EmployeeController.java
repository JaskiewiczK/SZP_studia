package szp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szp.controller.dto.EmployeeDTO;
import szp.model.Role;
import szp.repository.EmployeeRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
    @GetMapping("/")
    public ResponseEntity<Role> testEmployeeController() {
        return ResponseEntity.ok(Role.EMPLOYEE);
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@PathVariable("employee_id") Integer employeeId) {
        return ResponseEntity.of(employeeRepository.findById(employeeId)
                .map(model -> new EmployeeDTO(model)));
    }
}
