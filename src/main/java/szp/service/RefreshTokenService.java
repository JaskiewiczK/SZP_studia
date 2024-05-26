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

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeRepository employeeRepository;
    @Value("${jwt.refresh-token.expiry}")
    private int refreshTokenExpiry;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .employeeModel(employeeRepository.findByLogin(username).orElseThrow(EntityNotFoundException::new))
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(refreshTokenExpiry * 1000l))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpireDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired.");
        }
        return token;
    }

    @Transactional
    public void deleteTokenIfExistsByUsername(String username) {
        if (refreshTokenRepository.existsByEmployeeModel_Login(username)) {
            refreshTokenRepository.deleteByEmployeeModel_Login(username);
        }
    }

    public void setRefreshTokenExpiry(int refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}
