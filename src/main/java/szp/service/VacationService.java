package szp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import szp.model.VacationModel;
import szp.repository.VacationRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * This is the VacationService class that handles the business logic related to vacations.
 * It is annotated with @Service, meaning it's a Spring service component and it's ready to be autowired as a bean.
 * @AllArgsConstructor is a Lombok annotation to generate a constructor with all the properties in the class.
 */
@Service
@AllArgsConstructor
public class VacationService {
    /**
     * This is the repository for VacationModel entities.
     */
    private final VacationRepository vacationRepository;

    /**
     * This method retrieves all VacationModel entities in a given date range.
     * If includeAllMatchning is true, it retrieves all vacations where the vacation date includes the specified dates.
     * If includeAllMatchning is false, it retrieves all vacations where the beginning is greater than or equal to the start date and the ending is less than or equal to the end date.
     * @param from LocalDate representing the start date of the range.
     * @param to LocalDate representing the end date of the range.
     * @param includeAllMatchning boolean indicating whether to include all matching or not.
     * @return A list of VacationModel entities.
     */
    public List<VacationModel> getAllVacationsInRange(LocalDate from, LocalDate to, boolean includeAllMatchning) {
        if (includeAllMatchning) {
            return vacationRepository.findAllWhereVacationDateIncludesDates(Date.valueOf(from), Date.valueOf(to));
        } else {
            return vacationRepository.findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(Date.valueOf(from), Date.valueOf(to));
        }
    }
}