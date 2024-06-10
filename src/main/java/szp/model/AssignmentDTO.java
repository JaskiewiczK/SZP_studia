package szp.model;

import lombok.Data;

/**
 * This is the AssignmentDTO class that is used to transfer data related to assignments.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class AssignmentDTO {
    /**
     * This is the id of the employee associated with the assignment.
     */
    private Integer employeeId;

    /**
     * This is the id of the customer associated with the assignment.
     */
    private Integer customerId;

    /**
     * This is the id of the workstation associated with the assignment.
     */
    private Integer workstationId;

    /**
     * This is the description of the assignment.
     */
    private String description;

    /**
     * This is the cost of the assignment.
     */
    private Integer cost;
}