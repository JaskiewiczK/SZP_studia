package szp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import szp.model.*;
import szp.repository.*;
import szp.service.*;

import java.util.Collections;
import java.util.Optional;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private VacationRepository vacationRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    private void mockJwtService() {
        when(jwtService.getToken(any(HttpServletRequest.class), any(JwtService.TokenType.class)))
                .thenReturn(Optional.of("mockToken"));
        when(jwtService.extractUsername(anyString())).thenReturn("mockUser");
        when(employeeRepository.findByLogin(anyString())).thenReturn(Optional.of(new EmployeeModel()));
    }

    @Test
    public void testGetHomePage() throws Exception {
        mockJwtService();

        mockMvc.perform(get("/employee/"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee"))
                .andExpect(model().attributeExists("currentUser"));

        verify(employeeRepository, times(1)).findByLogin(anyString());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new EmployeeModel()));

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/updateEmployee"))
                .andExpect(model().attributeExists("employee"));

        verify(employeeRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeModel employee = new EmployeeModel();
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        mockMvc.perform(post("/employee/1/save")
                        .param("firstName", "John")
                        .param("secondName", "M")
                        .param("lastName", "Doe")
                        .param("birthDate", "2000-01-01")
                        .param("pesel", "1234567890")
                        .param("login", "john.doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/"));

        verify(employeeRepository, times(1)).save(any(EmployeeModel.class));
    }

    @Test
    public void testSendVacationRequest() throws Exception {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new EmployeeModel()));

        mockMvc.perform(post("/employee/vacationRequest/1")
                        .param("beginning", "2023-06-01")
                        .param("ending", "2023-06-10")
                        .param("type", "ANNUAL"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/success"));

        verify(vacationRepository, times(1)).save(any(VacationModel.class));
    }

    @Test
    public void testShowVacationRequestForm() throws Exception {
        mockJwtService();

        mockMvc.perform(get("/employee/vacation-request"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/vacationRequest"))
                .andExpect(model().attributeExists("employee"));

        verify(employeeRepository, times(1)).findByLogin(anyString());
    }

    @Test
    public void testShowVacation() throws Exception {
        when(vacationRepository.findByEmployee_EmployeeId(anyInt()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employee/vacation/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/vacation"))
                .andExpect(model().attributeExists("vacations"));

        verify(vacationRepository, times(1)).findByEmployee_EmployeeId(anyInt());
    }
}
