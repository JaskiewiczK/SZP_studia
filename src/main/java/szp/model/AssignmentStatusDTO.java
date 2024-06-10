package szp.model;

import lombok.Data;

/**
 * This is the AssignmentStatusDTO class that is used to transfer data related to assignment status.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class AssignmentStatusDTO {
    /**
     * This is the id of the assignment.
     */
    private Integer id;

    /**
     * This is the status of the assignment.
     */
    private String status;
}