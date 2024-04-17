package szp.controller;

import szp.model.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeResource {
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest);
}
