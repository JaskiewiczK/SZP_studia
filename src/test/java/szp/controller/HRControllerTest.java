package szp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

public class HRControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private WorkstationRepository workstationRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private VacationService vacationService;

    @InjectMocks
    private HRController hrController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
    }

    @Test
    public void testGetHomePage() throws Exception {
        mockMvc.perform(get("/hr/"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/hr"));
    }

    @Test
    public void testGetEmployeesInfo() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hr/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/employees"))
                .andExpect(model().attributeExists("employees"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(post("/hr/employees")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/employees"));

        verify(employeeRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testEmployeeLeavesPage() throws Exception {
        when(vacationService.getAllVacationsInRange(any(), any(), anyBoolean()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hr/leaves")
                        .param("startDate", "2020-01-01")
                        .param("endDate", "2020-12-31")
                        .param("includeAllMatching", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/leaves"))
                .andExpect(model().attributeExists("vacations"));

        verify(vacationService, times(1))
                .getAllVacationsInRange(any(), any(), eq(true));
    }

    @Test
    public void testGetAllWorkstations() throws Exception {
        when(workstationRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hr/workstations"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/workstations"))
                .andExpect(model().attributeExists("workstations"));

        verify(workstationRepository, times(1)).findAll();
    }

    @Test
    public void testAddWorkstation() throws Exception {
        mockMvc.perform(post("/hr/workstations")
                        .param("name", "New Workstation"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/workstations"));

        verify(workstationRepository, times(1)).save(any(WorkstationModel.class));
    }

    @Test
    public void testDeleteWorkstation() throws Exception {
        mockMvc.perform(post("/hr/workstations/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/workstations"));

        verify(workstationRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testGetAllAssignments() throws Exception {
        when(assignmentRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hr/assignments"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/assignments"))
                .andExpect(model().attributeExists("assignments"));

        verify(assignmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetAssignmentForm() throws Exception {
        mockMvc.perform(get("/hr/assignments/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/assignment-form"));
    }

    @Test
    public void testAddAssignment() throws Exception {
        EmployeeModel employee = new EmployeeModel();
        WorkstationModel workstation = new WorkstationModel();
        ClientModel client = new ClientModel();

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(workstationRepository.findById(anyInt())).thenReturn(Optional.of(workstation));
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));

        mockMvc.perform(post("/hr/assignments/add")
                        .param("employeeId", "1")
                        .param("workstationId", "1")
                        .param("customerId", "1")
                        .param("cost", "1000")
                        .param("description", "Test Assignment"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/success-assignment"));

        verify(assignmentRepository, times(1)).save(any(AssignmentModel.class));
    }

    @Test
    public void testChangeAssignmentStatus() throws Exception {
        AssignmentModel assignment = new AssignmentModel();
        when(assignmentRepository.findById(anyInt())).thenReturn(Optional.of(assignment));

        mockMvc.perform(post("/hr/assignments/status")
                        .param("id", "1")
                        .param("status", "DONE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/assignments"));

        verify(assignmentRepository, times(1)).save(any(AssignmentModel.class));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hr/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/clients"))
                .andExpect(model().attributeExists("clients"));

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void testAddCustomer() throws Exception {
        mockMvc.perform(post("/hr/customers/add")
                        .param("bankAccount", "123456789")
                        .param("email", "test@example.com")
                        .param("phoneNumber", "123456789")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("secondName", "Middle"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/customers"));

        verify(clientRepository, times(1)).save(any(ClientModel.class));
    }

    @Test
    public void testAddCustomerForm() throws Exception {
        mockMvc.perform(get("/hr/customers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("hr/customer-form"));
    }

    @Test
    public void testRemoveCustomer() throws Exception {
        mockMvc.perform(post("/hr/customers/remove")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hr/customers"));

        verify(clientRepository, times(1)).deleteById(anyInt());
    }
}
