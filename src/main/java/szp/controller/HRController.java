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
}
