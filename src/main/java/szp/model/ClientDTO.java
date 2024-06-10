package szp.model;

import lombok.Data;

/**
 * This is the ClientDTO class that is used to transfer data related to clients.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 */
@Data
public class ClientDTO {
    /**
     * This is the first name of the client.
     */
    private String firstName;

    /**
     * This is the second name of the client.
     */
    private String secondName;

    /**
     * This is the last name of the client.
     */
    private String lastName;

    /**
     * This is the email of the client.
     */
    private String email;

    /**
     * This is the phone number of the client.
     */
    private String phoneNumber;

    /**
     * This is the bank account of the client.
     */
    private String bankAccount;
}