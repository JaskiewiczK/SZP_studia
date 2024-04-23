package szp.repository;

import szp.model.VacationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationTypeRepository extends JpaRepository<VacationTypeModel, Integer> {
}
