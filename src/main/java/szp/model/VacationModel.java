package szp.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This is the VacationModel class that represents a vacation in the system.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 * It is also annotated with @Entity, indicating that it is a JPA entity.
 * @Table(name = "vacation") is used to provide the details of the table that this entity will be mapped to.
 */
@Data
@Entity
@Table(name = "vacation")
public class VacationModel {

    /**
     * This is the id of the vacation.
     * It is generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id ")
    private Integer vacationId;

    /**
     * This is the employee associated with the vacation.
     * It is a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeModel employee;

    /**
     * This is the beginning date of the vacation.
     */
    @Column(name = "beginning", nullable = false)
    private  java.sql.Date beginning;

    /**
     * This is the ending date of the vacation.
     */
    @Column(name = "ending", nullable = false)
    private  java.sql.Date  ending;

    /**
     * This is the type of the vacation.
     */
    @Column(name = "vacation_type", nullable = false)
    private String type;

}