package SZP.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "workstation")
public class WorkstationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workstation_id", nullable = false)
    private Integer workstationId;

    @Column(name = "name", nullable = false)
    private String name;

}
