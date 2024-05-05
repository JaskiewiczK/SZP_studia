package szp.controller;

import szp.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanic")
public class MechanicController{
    @GetMapping("/")
    public ResponseEntity<Role> testMechanicController() {
        return ResponseEntity.ok(Role.MECHANIC);
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getMechanicInfoById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/vacation")
    public ResponseEntity<String> requestVacation(@RequestBody String vacationRequest) {

        return null;
    }


}
