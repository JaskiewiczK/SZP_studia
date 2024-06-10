package szp.controller;

import szp.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is the AdminController class that handles all the admin related requests.
 * It is annotated with @RestController, meaning it's ready for use by Spring MVC to handle web requests.
 * @RequestMapping("/admin") maps all HTTP operations by default.
 */
@RestController
@RequestMapping("/admin")
public class AdminController{

    /**
     * This method is a simple test method that returns a Role object with the role of ADMIN.
     * It is mapped to the URL "/admin/" via HTTP GET method.
     * @return ResponseEntity with Role of ADMIN.
     */
    @GetMapping("/")
    public ResponseEntity<Role> testMechanicController() {
        return ResponseEntity.ok(Role.ADMIN);
    }
}