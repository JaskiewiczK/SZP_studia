package szp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szp.model.VacationModel;
import szp.repository.VacationRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VacationServiceTest {
    @InjectMocks
    private VacationService vacationService;

    @Mock
    private VacationRepository vacationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVacationsInRangeIncludeAllMatching() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        VacationModel vacation1 = new VacationModel();
        vacation1.setVacationId(1);
        vacation1.setBeginning(Date.valueOf("2023-01-10"));
        vacation1.setEnding(Date.valueOf("2023-01-20"));
        vacation1.setType("Holiday");

        VacationModel vacation2 = new VacationModel();
        vacation2.setVacationId(2);
        vacation2.setBeginning(Date.valueOf("2023-05-10"));
        vacation2.setEnding(Date.valueOf("2023-05-20"));
        vacation2.setType("Holiday");

        List<VacationModel> vacations = Arrays.asList(vacation1, vacation2);

        when(vacationRepository.findAllWhereVacationDateIncludesDates(fromDate, toDate)).thenReturn(vacations);

        List<VacationModel> result = vacationService.getAllVacationsInRange(from, to, true);
        assertEquals(2, result.size());
        assertEquals("Holiday", result.get(0).getType());
        assertEquals("Holiday", result.get(1).getType());

        verify(vacationRepository, times(1)).findAllWhereVacationDateIncludesDates(fromDate, toDate);
    }

    @Test
    void testGetAllVacationsInRangeExcludeAllMatching() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        VacationModel vacation1 = new VacationModel();
        vacation1.setVacationId(1);
        vacation1.setBeginning(Date.valueOf("2023-01-10"));
        vacation1.setEnding(Date.valueOf("2023-01-20"));
        vacation1.setType("Holiday");

        VacationModel vacation2 = new VacationModel();
        vacation2.setVacationId(2);
        vacation2.setBeginning(Date.valueOf("2023-05-10"));
        vacation2.setEnding(Date.valueOf("2023-05-20"));
        vacation2.setType("Holiday");

        List<VacationModel> vacations = Arrays.asList(vacation1, vacation2);

        when(vacationRepository.findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(fromDate, toDate)).thenReturn(vacations);

        List<VacationModel> result = vacationService.getAllVacationsInRange(from, to, false);
        assertEquals(2, result.size());
        assertEquals("Holiday", result.get(0).getType());
        assertEquals("Holiday", result.get(1).getType());

        verify(vacationRepository, times(1)).findAllByBeginningIsGreaterThanEqualAndEndingLessThanEqual(fromDate, toDate);
    }

}