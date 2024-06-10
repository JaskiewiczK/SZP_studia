package szp.model;

import lombok.Data;

/**
 * This is the WorkstationDTO class that is used to transfer data related to workstations.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class WorkstationDTO {
    /**
     * This is the name of the workstation.
     */
    private String name;
}