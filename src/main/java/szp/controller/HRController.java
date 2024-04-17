package szp.controller;

import szp.model.LoginRequestDTO;
import szp.model.TitleModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class HRController implements EmployeeResource {
    @GetMapping("/")
    public ResponseEntity<TitleModel> testMechanicController() {
        TitleModel hr = new TitleModel();
        hr.setName("hr");
        hr.setTitleId(1);
        return ResponseEntity.ok(hr);
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
