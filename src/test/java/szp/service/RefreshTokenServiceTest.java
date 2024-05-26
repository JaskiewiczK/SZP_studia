package szp.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szp.model.EmployeeModel;
import szp.model.RefreshToken;
import szp.repository.EmployeeRepository;
import szp.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class RefreshTokenServiceTest {
    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        refreshTokenService.setRefreshTokenExpiry(3600); // Set the refresh token expiry time for testing
    }

    @Test
    void testCreateRefreshToken() {
        String username = "testuser";
        EmployeeModel employee = new EmployeeModel();
        employee.setLogin(username);

        when(employeeRepository.findByLogin(username)).thenReturn(Optional.of(employee));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);

        assertNotNull(refreshToken);
        assertEquals(employee, refreshToken.getEmployeeModel());
        assertEquals(3600 * 1000L, refreshToken.getExpireDate().toEpochMilli() - Instant.now().toEpochMilli(), 1000);
        assertNotNull(refreshToken.getToken());

        verify(employeeRepository, times(1)).findByLogin(username);
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void testFindByToken() {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);

        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> foundToken = refreshTokenService.findByToken(token);
        assertTrue(foundToken.isPresent());
        assertEquals(token, foundToken.get().getToken());

        verify(refreshTokenRepository, times(1)).findByToken(token);
    }

    @Test
    void testVerifyExpiration_ValidToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpireDate(Instant.now().plusMillis(3600 * 1000L));

        RefreshToken result = refreshTokenService.verifyExpiration(refreshToken);
        assertEquals(refreshToken, result);
    }

    @Test
    void testVerifyExpiration_ExpiredToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpireDate(Instant.now().minusMillis(1000L));

        doNothing().when(refreshTokenRepository).delete(refreshToken);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.verifyExpiration(refreshToken);
        });
        assertEquals("Refresh token expired.", exception.getMessage());

        verify(refreshTokenRepository, times(1)).delete(refreshToken);
    }

    @Test
    void testDeleteTokenIfExistsByUsername() {
        String username = "testuser";

        when(refreshTokenRepository.existsByEmployeeModel_Login(username)).thenReturn(true);
        doNothing().when(refreshTokenRepository).deleteByEmployeeModel_Login(username);

        refreshTokenService.deleteTokenIfExistsByUsername(username);

        verify(refreshTokenRepository, times(1)).existsByEmployeeModel_Login(username);
        verify(refreshTokenRepository, times(1)).deleteByEmployeeModel_Login(username);
    }

}