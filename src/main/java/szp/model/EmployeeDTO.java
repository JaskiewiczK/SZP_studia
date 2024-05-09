package szp.model;


import lombok.Data;

@Data
public class EmployeeDTO {
    private String firstName;

    private String secondName;

    private String lastName;

    private java.sql.Date  birthDate;

    private String pesel;

    private String login;
}
