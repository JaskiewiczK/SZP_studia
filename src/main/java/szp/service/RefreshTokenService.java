package szp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import szp.model.RefreshToken;
import szp.repository.EmployeeRepository;
import szp.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * This is the RefreshTokenService class that handles the business logic related to refresh tokens.
 * It is annotated with @Service, meaning it's a Spring service component and it's ready to be autowired as a bean.
 * @RequiredArgsConstructor is a Lombok annotation to generate a constructor with required fields (final fields and fields with @NonNull annotation).
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    /**
     * This is the repository for RefreshToken entities.
     */
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * This is the repository for EmployeeModel entities.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * This is the expiry time for the refresh token.
     */
    @Value("${jwt.refresh-token.expiry}")
    private int refreshTokenExpiry;

    /**
     * This method creates a refresh token for a username.
     * @param username The username for which the refresh token is to be created.
     * @return RefreshToken object representing the created refresh token.
     */
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .employeeModel(employeeRepository.findByLogin(username).orElseThrow(EntityNotFoundException::new))
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(refreshTokenExpiry * 1000l))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * This method finds a RefreshToken entity by its token.
     * @param token The token of the refresh token.
     * @return An Optional that may contain the RefreshToken entity if found.
     */
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * This method verifies the expiration of the refresh token.
     * @param token The RefreshToken entity to be verified.
     * @return RefreshToken object if the token is not expired.
     * @throws RuntimeException if the token is expired.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpireDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired.");
        }
        return token;
    }

    /**
     * This method deletes a RefreshToken entity if it exists by the login of the associated EmployeeModel.
     * @param username The login of the employee.
     */
    @Transactional
    public void deleteTokenIfExistsByUsername(String username) {
        if (refreshTokenRepository.existsByEmployeeModel_Login(username)) {
            refreshTokenRepository.deleteByEmployeeModel_Login(username);
        }
    }

    /**
     * This method sets the expiry time for the refresh token.
     * @param refreshTokenExpiry The expiry time for the refresh token.
     */
    public void setRefreshTokenExpiry(int refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}