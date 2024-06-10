package szp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This is the WebSecurityConfig class that handles the security configuration of the application.
 * It is annotated with @EnableWebSecurity and @Configuration, indicating that it is a configuration class for Spring Security.
 * @RequiredArgsConstructor is a Lombok annotation to generate a constructor with required fields (final fields and fields with @NonNull annotation).
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    /**
     * This is the service for loading user-specific data.
     */
    private final UserDetailsService userDetailsService;

    /**
     * This is the filter for JWT authentication.
     */
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * This method provides the authentication provider for the application.
     * @return DaoAuthenticationProvider object that handles the authentication.
     */
    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * This method provides the security filter chain for the application.
     * @param http HttpSecurity object to build the security configuration.
     * @return SecurityFilterChain object that contains the security filters.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(login -> login.loginPage("/auth/"))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/styles/**", "/images/**").permitAll()
                                .requestMatchers("/auth/login", "/auth/refresh-token", "/auth/").permitAll()
                                .requestMatchers("/auth/logout", "/auth/login/success/redirect").authenticated()
                                .requestMatchers("/employee/**").hasAnyRole("EMPLOYEE", "ADMIN", "HR")
                                .requestMatchers("/hr/**").hasRole("HR")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                        )
                .sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider());
        return http.build();
    }

    /**
     * This method provides the password encoder for the application.
     * @return PasswordEncoder object that handles the password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method provides the authentication manager for the application.
     * @param config AuthenticationConfiguration object to get the authentication manager.
     * @return AuthenticationManager object that handles the authentication.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}