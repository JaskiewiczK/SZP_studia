package szp.model;

import lombok.Data;

@Data
public class ClientDTO {
    private String firstName;
    private String secondName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String bankAccount;
}
