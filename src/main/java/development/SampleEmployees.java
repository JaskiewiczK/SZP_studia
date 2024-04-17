package development;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import szp.model.EmployeeModel;
import szp.model.Role;
import szp.service.EmployeeService;

import java.sql.Date;

//    it adds sample employees to database with encrypted passwords
@SpringBootApplication(scanBasePackages = {"szp"})
public class SampleEmployees {
    public static void main(String [] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SampleEmployees.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        EmployeeModel admin = new EmployeeModel();
        admin.setRole(Role.ADMIN);
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setBirthDate(Date.valueOf("1985-05-15"));
        admin.setPesel("12345678901");
        admin.setLogin("admin");
        admin.setPassword("adminPassword");
        employeeService.saveUser(admin);

        EmployeeModel hr = new EmployeeModel();
        hr.setRole(Role.HR);
        hr.setFirstName("Alice");
        hr.setLastName("Smith");
        hr.setBirthDate(Date.valueOf("1990-10-20"));
        hr.setPesel("98765432109");
        hr.setLogin("hr");
        hr.setPassword("hrPassword");
        employeeService.saveUser(hr);

        EmployeeModel mechanic = new EmployeeModel();
        mechanic.setRole(Role.MECHANIC);
        mechanic.setFirstName("Bob");
        mechanic.setLastName("Johnson");
        mechanic.setBirthDate(Date.valueOf("1995-03-25"));
        mechanic.setPesel("56789012345");
        mechanic.setLogin("mechanic");
        mechanic.setPassword("mechanicPassword");
        employeeService.saveUser(mechanic);
        context.close();
    }
}