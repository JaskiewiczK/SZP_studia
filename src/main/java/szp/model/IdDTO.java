package szp.model;

import lombok.Data;

/**
 * This is the IdDTO class that is used to transfer data related to identifiers.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class IdDTO {
    /**
     * This is the id that is being transferred.
     */
    private Integer id;
}