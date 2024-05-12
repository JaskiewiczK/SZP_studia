package szp.model;

import lombok.Data;

@Data
public class AssignmentDTO {
    private Integer employeeId;
    private Integer customerId;

    private Integer workstationId;

    private String description;
    private Integer cost;
}
