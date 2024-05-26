package szp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import szp.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import szp.model.EmployeeModel;
import szp.repository.EmployeeRepository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;


    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testSaveUser() {
        // Given
        EmployeeModel employee = EmployeeModel.builder()
                .role(Role.ADMIN)
                .firstName("Janek")
                .lastName("Pork")
                .birthDate(Date.valueOf("1986-05-15"))
                .pesel("12345678902")
                .login("janpork11")
                .password("adminPassword")
                .build();


        when(passwordEncoder.encode("adminPassword")).thenReturn("encodedPassword");
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employee);
        when(employeeRepository.findByLogin("janpork11")).thenReturn(Optional.of(employee));

        // When
        employeeService.saveUser(employee);

        // Then
        EmployeeModel savedEmployee = employeeRepository.findByLogin("janpork11").orElse(null);
        assertNotNull(savedEmployee);
        assertEquals("Janek", savedEmployee.getFirstName());
        assertEquals("Pork", savedEmployee.getLastName());
        assertEquals("janpork11", savedEmployee.getLogin());
    }
    @Test
    void testGetAllEmployees() {
        List<EmployeeModel> employees = Arrays.asList(
                EmployeeModel.builder()
                        .employeeId(1)
                        .firstName("John")
                        .lastName("Doe")
                        .birthDate(Date.valueOf("1990-01-01"))
                        .pesel("12345678901")
                        .login("johndoe")
                        .password("password1")
                        .role(Role.EMPLOYEE)
                        .build(),
                EmployeeModel.builder()
                        .employeeId(2)
                        .firstName("Jane")
                        .lastName("Smith")
                        .birthDate(Date.valueOf("1985-05-15"))
                        .pesel("23456789012")
                        .login("janesmith")
                        .password("password2")
                        .role(Role.ADMIN)
                        .build()
        );

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeService.getAllEmployees()).thenReturn(employees);
        List<EmployeeModel> result = employeeService.getAllEmployees();


        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }
}