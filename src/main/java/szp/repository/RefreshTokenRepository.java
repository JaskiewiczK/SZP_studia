package szp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import szp.model.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    boolean existsByEmployeeModel_Login(String login);
    void deleteByEmployeeModel_Login(String login);
}
