package szp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assignment_state")
public class AssignmentStateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "state", nullable = false)
    private String state;

}
