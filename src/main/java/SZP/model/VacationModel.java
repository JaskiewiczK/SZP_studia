package SZP.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vacation")
public class VacationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id ")
    private Integer vacationId;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private VacationTypeModel type;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeModel employee;

    @Column(name = "beginning", nullable = false)
    private  java.sql.Date beginning;

    @Column(name = "ending", nullable = false)
    private  java.sql.Date  ending;

}
