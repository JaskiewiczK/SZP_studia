package szp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import szp.service.JwtService;

import java.io.IOException;
import java.util.Optional;

import static szp.service.JwtService.TokenType.ACCESS_TOKEN;

/**
 * This is the JwtAuthFilter class that handles the JWT authentication filter.
 * It extends OncePerRequestFilter, a filter base class that aims to guarantee a single execution per request dispatch.
 * It is annotated with @Component, meaning it's a Spring component and it's ready to be autowired as a bean.
 * @RequiredArgsConstructor is a Lombok annotation to generate a constructor with required fields (final fields and fields with @NonNull annotation).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    /**
     * Logger for logging information about the JwtAuthFilter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);

    /**
     * JwtService for handling JWT related operations.
     */
    private final JwtService jwtService;

    /**
     * UserDetailsService for loading user-specific data.
     */
    private final UserDetailsService userDetailsService;

    /**
     * This method is overridden from OncePerRequestFilter.
     * It handles the JWT authentication for each request.
     * @param request HttpServletRequest object to get request information for HTTP servlets.
     * @param response HttpServletResponse object to provide HTTP-specific functionality in sending a response.
     * @param filterChain FilterChain object to invoke the next filter in the chain.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected when the servlet handles the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtService.getToken(request, ACCESS_TOKEN);
        if(token.isEmpty()){
            filterChain.doFilter(request, response);
            LOGGER.debug("Token is empty.");
            return;
        }

        String username = jwtService.extractUsername(token.get());
        if (username != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token.get(), userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                LOGGER.debug("Token successfully validated.");
            }
        }
        filterChain.doFilter(request, response);
    }
}