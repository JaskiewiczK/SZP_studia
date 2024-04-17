package SZP.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vacation_type")
public class VacationTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "name", nullable = false)
    private String name;

}