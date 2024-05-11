package szp.model;

import lombok.Data;

@Data
public class VacationRequestDTO {
    private  java.sql.Date beginning;

    private  java.sql.Date ending;

    private String type;
}
