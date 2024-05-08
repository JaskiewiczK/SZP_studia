package development;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import szp.model.EmployeeModel;
import szp.model.Role;
import szp.service.EmployeeService;

import java.sql.Date;

 // Execute the main function to add sample employees to database.
 @SpringBootApplication(scanBasePackages = {"szp"})
public class SampleEmployees {
    public static void main(String [] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SampleEmployees.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        EmployeeModel admin = EmployeeModel.builder()
                .role(Role.ADMIN)
                .firstName("John")
                .lastName("Doe")
                .birthDate(Date.valueOf("1985-05-15"))
                .pesel("12345678901")
                .login("admin")
                .password("admin")
                .build();

        EmployeeModel hr = EmployeeModel.builder()
                .role(Role.HR)
                .firstName("Alice")
                .lastName("Smith")
                .birthDate(Date.valueOf("1990-10-20"))
                .pesel("98765432109")
                .login("hr")
                .password("hr")
                .build();

        EmployeeModel employee = EmployeeModel.builder()
                .role(Role.EMPLOYEE)
                .firstName("Bob")
                .lastName("Johnson")
                .birthDate(Date.valueOf("1995-03-25"))
                .pesel("56789012345")
                .login("em")
                .password("em")
                .build();

        employeeService.saveUser(admin);
        employeeService.saveUser(hr);
        employeeService.saveUser(employee);
        context.close();
    }
}