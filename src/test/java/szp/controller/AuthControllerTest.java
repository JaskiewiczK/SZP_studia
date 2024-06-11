package szp.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import szp.model.AuthRequestDTO;
import szp.model.EmployeeModel;
import szp.model.RefreshToken;
import szp.model.Role;
import szp.repository.EmployeeRepository;
import szp.service.JwtService;
import szp.service.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static szp.service.JwtService.TokenType.REFRESH_TOKEN;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController.accessTokenCookieExpiry = 3600;
        authController.refreshTokenExpiry = 7200;
    }

    @Test
    void testLoginPage() {
        String viewName = authController.loginPage();
        assertEquals("login-page", viewName);
    }

    @Test
    void testGetLoginRedirectUrl_HR() {
        when(request.isUserInRole(Role.HR.toString())).thenReturn(true);
        EmployeeModel user = new EmployeeModel();
        when(jwtService.getToken(any(), any())).thenReturn(Optional.of("token"));
        when(jwtService.extractUsername(any())).thenReturn("user");
        when(employeeRepository.findByLogin(any())).thenReturn(Optional.of(user));

        String viewName = authController.getLoginRedirectUrl(model, request);
        assertEquals("redirect:/hr/", viewName);
    }

    @Test
    void testAuthenticateAndGetToken_Success() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("user");
        authRequestDTO.setPassword("pass");

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(auth);

        when(jwtService.generateToken(any())).thenReturn("access-token");
        when(refreshTokenService.createRefreshToken(any())).thenReturn(
                RefreshToken.builder()
                        .id(1)
                        .token("refresh-token")
                        .expireDate(Instant.now().plusSeconds(7200))
                        .employeeModel(new EmployeeModel())
                        .build()
        );

        String viewName = authController.authenticateAndGetToken(model, authRequestDTO, response);
        assertEquals("redirect:/auth/login/success/redirect", viewName);

        verify(response, times(2)).addCookie(any(Cookie.class));
    }

    @Test
    void testAuthenticateAndGetToken_Failure() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("user");
        authRequestDTO.setPassword("pass");

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Incorrect username or password."));

        String viewName = authController.authenticateAndGetToken(model, authRequestDTO, response);
        assertEquals("login-page", viewName);

        verify(model, times(1)).addAttribute("errorMessage", "Incorrect username or password.");
    }

    @Test
    void testLogout() {
        when(jwtService.getToken(any(), any())).thenReturn(Optional.of("token"));
        when(jwtService.extractUsername(any())).thenReturn("user");

        String viewName = authController.logout(request, response);
        assertEquals("redirect:/auth/", viewName);

        verify(refreshTokenService, times(1)).deleteTokenIfExistsByUsername("user");
        verify(response, times(2)).addCookie(any(Cookie.class));
    }

//    @Test
//    void testRefreshToken_Success() {
//        String refreshToken = "refresh-token";
//        when(jwtService.getToken(request, REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
//        RefreshToken token = RefreshToken.builder()
//                .id(1)
//                .token(refreshToken)
//                .expireDate(Instant.now().plusSeconds(7200))
//                .employeeModel(EmployeeModel.builder().login("user").build())
//                .build();
//
//        when(refreshTokenService.findByToken(refreshToken)).thenReturn(Optional.of(token));
//        when(jwtService.generateToken(any())).thenReturn("new-access-token");
//
//        ResponseEntity<Object> responseEntity = authController.refreshToken(request, response);
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(response, times(1)).addCookie(any(Cookie.class));
//    }

    @Test
    void testRefreshToken_Failure() {
        when(jwtService.getToken(request, REFRESH_TOKEN)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authController.refreshToken(request, response));
    }
}
