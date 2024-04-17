package SZP.repository;


import SZP.model.VacationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationInterface extends JpaRepository<VacationModel, Integer> {
}
