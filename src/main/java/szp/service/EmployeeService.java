package szp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szp.model.EmployeeModel;
import szp.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public void saveUser(EmployeeModel employee) {
//        saves user encrypting his password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
    }

    public List<EmployeeModel> getAllEmployees(){
       return employeeRepository.findAll();
    }
}
