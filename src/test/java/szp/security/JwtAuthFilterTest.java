package szp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import szp.service.JwtService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_TokenIsEmpty() throws ServletException, IOException {
        when(jwtService.getToken(any(HttpServletRequest.class), any(JwtService.TokenType.class)))
                .thenReturn(Optional.empty());

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).getToken(any(HttpServletRequest.class), any(JwtService.TokenType.class));
    }

    @Test
    public void testDoFilterInternal_WithInvalidToken() throws ServletException, IOException {
        String tokenValue = "invalidToken";
        Cookie cookie = new Cookie("accessToken", tokenValue);

        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(jwtService.getToken(request, JwtService.TokenType.ACCESS_TOKEN)).thenReturn(Optional.of(tokenValue));
        when(jwtService.extractUsername(tokenValue)).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, times(1)).getToken(request, JwtService.TokenType.ACCESS_TOKEN);
        verify(jwtService, times(1)).extractUsername(tokenValue);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithNoToken() throws ServletException, IOException {
        // Given
        when(jwtService.getToken(request, JwtService.TokenType.ACCESS_TOKEN)).thenReturn(Optional.empty());

        // When
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
}