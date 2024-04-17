package szp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import szp.model.EmployeeModel;
import szp.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        EmployeeModel employee = employeeRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return User.withUsername(employee.getLogin()).password(employee.getPassword()).roles(employee.getRole().name()).build();
    }
}
