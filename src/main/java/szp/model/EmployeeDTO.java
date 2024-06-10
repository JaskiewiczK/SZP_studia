package szp.model;

import lombok.Data;

/**
 * This is the EmployeeDTO class that is used to transfer data related to employees.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class EmployeeDTO {
    /**
     * This is the first name of the employee.
     */
    private String firstName;

    /**
     * This is the second name of the employee.
     */
    private String secondName;

    /**
     * This is the last name of the employee.
     */
    private String lastName;

    /**
     * This is the birth date of the employee.
     */
    private java.sql.Date  birthDate;

    /**
     * This is the pesel (Polish national identification number) of the employee.
     */
    private String pesel;

    /**
     * This is the login of the employee.
     */
    private String login;
}