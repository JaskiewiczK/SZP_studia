package szp.repository;


import szp.model.VacationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<VacationModel, Integer> {
    List<VacationModel> findByEmployee_EmployeeId(Integer employeeId);
}
