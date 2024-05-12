package szp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private java.sql.Date  birthDate;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<AssignmentModel> assignments;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<VacationModel> vacations;

}
