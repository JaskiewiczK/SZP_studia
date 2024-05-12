package szp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "workstation")
public class WorkstationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workstation_id")
    private Integer id;

    @Column(name = "workstation_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "workstation", cascade = CascadeType.ALL)
    private List<AssignmentModel> assignments;

}