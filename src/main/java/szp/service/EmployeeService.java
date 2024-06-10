package szp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szp.model.EmployeeModel;
import szp.repository.EmployeeRepository;

import java.util.List;

/**
 * This is the EmployeeService class that handles the business logic related to employees.
 * It is annotated with @Service, meaning it's a Spring service component and it's ready to be autowired as a bean.
 * @RequiredArgsConstructor is a Lombok annotation to generate a constructor with required fields (final fields and fields with @NonNull annotation).
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    /**
     * This is the password encoder that encodes passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * This is the repository for EmployeeModel entities.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * This method saves an EmployeeModel entity, encrypting its password.
     * @param employee The EmployeeModel entity to be saved.
     */
    public void saveUser(EmployeeModel employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
    }

    /**
     * This method retrieves all EmployeeModel entities.
     * @return A list of EmployeeModel entities.
     */
    public List<EmployeeModel> getAllEmployees(){
       return employeeRepository.findAll();
    }
}