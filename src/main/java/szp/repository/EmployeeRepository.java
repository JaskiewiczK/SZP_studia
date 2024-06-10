package szp.repository;

import szp.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is the EmployeeRepository interface that provides methods for performing database operations on EmployeeModel entities.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * @Repository is a Spring annotation that indicates that the decorated class is a repository.
 * A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    /**
     * This method finds an EmployeeModel entity by its login.
     * @param login The login of the employee.
     * @return An Optional that may contain the EmployeeModel entity if found.
     */
    Optional<EmployeeModel> findByLogin(String login);
}