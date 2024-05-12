package szp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "assignment")
public class AssignmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer assignmentId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeModel employee;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientModel client;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "assign_date", nullable = false)
    private  java.sql.Date  assignDate;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @OneToOne
    @JoinColumn(name = "workstation_id")
    private WorkstationModel workstation;


}
