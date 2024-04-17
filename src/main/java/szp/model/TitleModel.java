package szp.model;



import lombok.Data;

import jakarta.persistence.*;
@Data
@Entity
@Table(name = "title")
public class TitleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Integer titleId;

    @Column(name = "name", nullable = false)
    private String name;

}
