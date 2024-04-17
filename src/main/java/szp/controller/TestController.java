package szp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szp.service.EmployeeService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    private final EmployeeService employeeService;
    @GetMapping("/")
    public ResponseEntity<String> saveSamples() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
