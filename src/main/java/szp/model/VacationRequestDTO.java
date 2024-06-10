package szp.model;

import lombok.Data;

/**
 * This is the VacationRequestDTO class that is used to transfer data related to vacation requests.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class VacationRequestDTO {
    /**
     * This is the beginning date of the vacation request.
     */
    private  java.sql.Date beginning;

    /**
     * This is the ending date of the vacation request.
     */
    private  java.sql.Date ending;

    /**
     * This is the type of the vacation request.
     */
    private String type;
}