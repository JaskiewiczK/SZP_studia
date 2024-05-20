package szp.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import szp.model.VacationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<VacationModel, Integer> {
    List<VacationModel> findByEmployee_EmployeeId(Integer employeeId);
    List<VacationModel> findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(Date beginning, Date ending);

    @Query("from VacationModel vm where (vm.beginning between :beginning and :ending) or (vm.ending between :beginning and :ending)")
    List<VacationModel> findAllWhereVacationDateIncludesDates(@Param(value = "beginning") Date beginning, @Param(value = "ending") Date ending);
}
