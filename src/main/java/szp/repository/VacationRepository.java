package szp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import szp.model.VacationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * This is the VacationRepository interface that provides methods for performing database operations on VacationModel entities.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * @Repository is a Spring annotation that indicates that the decorated class is a repository.
 * A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 */
@Repository
public interface VacationRepository extends JpaRepository<VacationModel, Integer> {

    /**
     * This method finds a list of VacationModel entities by the id of the associated employee.
     * @param employeeId The id of the employee.
     * @return A list of VacationModel entities.
     */
    List<VacationModel> findByEmployee_EmployeeId(Integer employeeId);

    /**
     * This method finds a list of VacationModel entities by the beginning and ending dates.
     * @param beginning The beginning date of the vacation.
     * @param ending The ending date of the vacation.
     * @return A list of VacationModel entities.
     */
    List<VacationModel> findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(Date beginning, Date ending);

    /**
     * This method finds a list of VacationModel entities where the vacation date includes the specified dates.
     * It uses a custom query defined with the @Query annotation.
     * @param beginning The beginning date.
     * @param ending The ending date.
     * @return A list of VacationModel entities.
     */
    @Query("from VacationModel vm where (vm.beginning between :beginning and :ending) or (vm.ending between :beginning and :ending)")
    List<VacationModel> findAllWhereVacationDateIncludesDates(@Param(value = "beginning") Date beginning, @Param(value = "ending") Date ending);
}