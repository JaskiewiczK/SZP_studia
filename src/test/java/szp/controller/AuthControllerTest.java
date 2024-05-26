package szp.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import szp.model.AuthRequestDTO;
import szp.model.EmployeeModel;
import szp.model.RefreshToken;
import szp.repository.EmployeeRepository;
import szp.service.JwtService;
import szp.service.RefreshTokenService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginPage() {
        String view = authController.loginPage();
        assertEquals("login-page", view);
    }

    @Test
    public void testGetLoginRedirectUrl() {
        EmployeeModel employee = new EmployeeModel();
        when(jwtService.getToken(any(), eq(JwtService.TokenType.ACCESS_TOKEN))).thenReturn(Optional.of("token"));
        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(employeeRepository.findByLogin(anyString())).thenReturn(Optional.of(employee));

        String view = authController.getLoginRedirectUrl(model, request);
        verify(model, times(1)).addAttribute("currentEmployee", employee);
        assertEquals("redirect:/projects/employee", view);
    }
//    @Test
//    public void testLogin() {
//        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
//        authRequestDTO.setUsername("username");
//        authRequestDTO.setPassword("password");
//
//        EmployeeModel employee = new EmployeeModel();
//        employee.setLogin("username");
//
//        when(employeeRepository.findByLogin(authRequestDTO.getUsername())).thenReturn(Optional.of(employee));
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(mock(Authentication.class));
//        when(jwtService.generateToken(anyString())).thenReturn("accessToken");
//        when(jwtService.generateRefreshToken(anyString())).thenReturn("refreshToken");
//
//        String view = authController.login(authRequestDTO, response);
//
//        ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
//        verify(response, times(2)).addHeader(eq(HttpHeaders.SET_COOKIE), headerCaptor.capture());
//
//        // Verify cookies
//        String accessTokenCookie = headerCaptor.getAllValues().get(0);
//        String refreshTokenCookie = headerCaptor.getAllValues().get(1);
//        assert(accessTokenCookie.contains(JwtService.TokenType.ACCESS_TOKEN.getHeader()));
//        assert(refreshTokenCookie.contains(JwtService.TokenType.REFRESH_TOKEN.getHeader()));
//
//        assertEquals("redirect:/auth/", view);
//    }


}