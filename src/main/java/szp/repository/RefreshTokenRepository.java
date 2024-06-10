package szp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import szp.model.RefreshToken;

import java.util.Optional;

/**
 * This is the RefreshTokenRepository interface that provides methods for performing database operations on RefreshToken entities.
 * It extends CrudRepository which provides CRUD related methods like save(), findOne(), findAll(), count(), delete() etc.
 * @Repository is a Spring annotation that indicates that the decorated class is a repository.
 * A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    /**
     * This method finds a RefreshToken entity by its token.
     * @param token The token of the refresh token.
     * @return An Optional that may contain the RefreshToken entity if found.
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * This method checks if a RefreshToken entity exists by the login of the associated EmployeeModel.
     * @param login The login of the employee.
     * @return A boolean indicating whether a RefreshToken entity exists or not.
     */
    boolean existsByEmployeeModel_Login(String login);

    /**
     * This method deletes a RefreshToken entity by the login of the associated EmployeeModel.
     * @param login The login of the employee.
     */
    void deleteByEmployeeModel_Login(String login);
}