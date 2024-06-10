package szp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import szp.model.EmployeeModel;
import szp.repository.EmployeeRepository;

/**
 * This is the UserDetailsService class that handles the loading of user-specific data.
 * It implements UserDetailsService from Spring Security to provide a core user service.
 * It is annotated with @Service, meaning it's a Spring service component and it's ready to be autowired as a bean.
 * @RequiredArgsConstructor is a Lombok annotation to generate a constructor with required fields (final fields and fields with @NonNull annotation).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    /**
     * This is the repository for EmployeeModel entities.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * This method loads the user-specific data by username.
     * It overrides the loadUserByUsername method from UserDetailsService interface.
     * @param login The username of the user.
     * @return UserDetails object that contains user-specific data.
     * @throws UsernameNotFoundException if the username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        EmployeeModel employee = employeeRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return User.withUsername(employee.getLogin()).password(employee.getPassword()).roles(employee.getRole().name()).build();
    }
}