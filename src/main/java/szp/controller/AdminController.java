package szp.controller;

import szp.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController{
    @GetMapping("/")
    public ResponseEntity<Role> testMechanicController() {
        return ResponseEntity.ok(Role.ADMIN);
    }
}
