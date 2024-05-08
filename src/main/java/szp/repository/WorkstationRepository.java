package szp.repository;

import szp.model.WorkstationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkstationRepository extends JpaRepository<WorkstationModel, Integer> {


}