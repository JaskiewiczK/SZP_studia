package szp.controller;

import szp.model.LoginRequestDTO;
import szp.model.TitleModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanic")
public class MechanicController implements EmployeeResource{
    @GetMapping("/")
    public ResponseEntity<TitleModel> testMechanicController() {
        TitleModel mechanic = new TitleModel();
        mechanic.setName("mechanic");
        mechanic.setTitleId(1);
        return ResponseEntity.ok(mechanic);
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
