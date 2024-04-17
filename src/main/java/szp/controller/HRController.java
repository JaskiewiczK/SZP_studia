package szp.controller;

import szp.model.LoginRequestDTO;
import szp.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class HRController implements EmployeeResource {
    @GetMapping("/")
    public ResponseEntity<Role> testHRController() {
        return ResponseEntity.ok(Role.HR);
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
