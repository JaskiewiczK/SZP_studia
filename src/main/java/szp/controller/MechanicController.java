package szp.controller;

import szp.model.LoginRequestDTO;
import szp.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanic")
public class MechanicController implements EmployeeResource{
    @GetMapping("/")
    public ResponseEntity<Role> testMechanicController() {
        return ResponseEntity.ok(Role.MECHANIC);
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
