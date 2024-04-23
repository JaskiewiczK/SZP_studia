package szp.repository;

import szp.model.AssignmentStateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentStateRepository extends JpaRepository<AssignmentStateModel, Integer> {
}
