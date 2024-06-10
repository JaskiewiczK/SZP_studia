package szp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the AuthRequestDTO class that is used to transfer data related to authentication requests.
 * It is annotated with @Data, a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 * @AllArgsConstructor is a Lombok annotation to generate a constructor with all the properties in the class.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 * @Builder is a Lombok annotation to provide a builder pattern method.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

    /**
     * This is the username of the user trying to authenticate.
     */
    private String username;

    /**
     * This is the password of the user trying to authenticate.
     */
    private String password;
}