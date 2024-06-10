package szp.repository;

import szp.model.WorkstationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is the WorkstationRepository interface that provides methods for performing database operations on WorkstationModel entities.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * @Repository is a Spring annotation that indicates that the decorated class is a repository.
 * A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 */
@Repository
public interface WorkstationRepository extends JpaRepository<WorkstationModel, Integer> {
}