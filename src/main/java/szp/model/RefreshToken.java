package szp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * This is the RefreshToken class that represents a refresh token in the system.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 * It is also annotated with @Entity, indicating that it is a JPA entity.
 * @Table(name = "refresh_token") is used to provide the details of the table that this entity will be mapped to.
 * @NoArgsConstructor, @AllArgsConstructor and @Builder are Lombok annotations to generate constructors and builder method.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshToken {
    /**
     * This is the id of the refresh token.
     * It is generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * This is the token string of the refresh token.
     */
    private String token;

    /**
     * This is the expiration date of the refresh token.
     */
    private Instant expireDate;

    /**
     * This is the employee associated with the refresh token.
     * It is a one-to-one relationship.
     */
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeModel employeeModel;
}