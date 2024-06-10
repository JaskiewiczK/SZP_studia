package szp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * This is the WorkstationModel class that represents a workstation in the system.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 * It is also annotated with @Entity, indicating that it is a JPA entity.
 * @Table(name = "workstation") is used to provide the details of the table that this entity will be mapped to.
 */
@Data
@Entity
@Table(name = "workstation")
public class WorkstationModel {

    /**
     * This is the id of the workstation.
     * It is generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workstation_id")
    private Integer id;

    /**
     * This is the name of the workstation.
     */
    @Column(name = "workstation_name", nullable = false)
    private String name;

    /**
     * This is the list of assignments associated with the workstation.
     * It is a one-to-many relationship.
     */
    @OneToMany(mappedBy = "workstation", cascade = CascadeType.ALL)
    private List<AssignmentModel> assignments;

}