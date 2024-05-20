package szp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import szp.model.VacationModel;
import szp.repository.VacationRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VacationService {
    private final VacationRepository vacationRepository;

    public List<VacationModel> getAllVacationsInRange(LocalDate from, LocalDate to, boolean includeAllMatchning) {
        if (includeAllMatchning) {
            return vacationRepository.findAllWhereVacationDateIncludesDates(Date.valueOf(from), Date.valueOf(to));
        } else {
            return vacationRepository.findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(Date.valueOf(from), Date.valueOf(to));
        }
    }
}
