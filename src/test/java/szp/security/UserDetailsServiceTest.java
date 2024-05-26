package szp.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import szp.model.EmployeeModel;
import szp.model.Role;
import szp.repository.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsService userDetailsService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testuser";
        EmployeeModel employee = EmployeeModel.builder()
                .login(username)
                .password("password")
                .role(Role.EMPLOYEE)
                .build();

        when(employeeRepository.findByLogin(username)).thenReturn(Optional.of(employee));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE")));

        verify(employeeRepository, times(1)).findByLogin(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "unknownuser";

        when(employeeRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        verify(employeeRepository, times(1)).findByLogin(username);
    }
}