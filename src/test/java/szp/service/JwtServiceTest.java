package szp.service;

import static org.junit.jupiter.api.Assertions.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class JwtServiceTest {
    private JwtService jwtService;
    private Key secretKey;


    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.SECRET = "mysecretkeymysecretkeymysecretkeymysecretkey"; // Example secret key
        jwtService.setAccessTokenExpiry(3600); // Example expiry time in seconds
        secretKey = Keys.hmacShaKeyFor(jwtService.SECRET.getBytes());

    }
    @Test
    void testExtractUsername() {
        String token = "example.jwt.token";
        String expectedUsername = "testuser";

        // Mocking the extractClaim method
        JwtService spyJwtService = Mockito.spy(jwtService);
        doReturn(expectedUsername).when(spyJwtService).extractClaim(eq(token), any(Function.class));

        String actualUsername = spyJwtService.extractUsername(token);
        assertEquals(expectedUsername, actualUsername);
    }
    @Test
    void testExtractExpiration() {
        String token = "example.jwt.token";
        Date expectedExpiration = new Date(System.currentTimeMillis() + 10000);

        // Mocking the extractClaim method
        JwtService spyJwtService = Mockito.spy(jwtService);
        doReturn(expectedExpiration).when(spyJwtService).extractClaim(eq(token), any(Function.class));

        Date actualExpiration = spyJwtService.extractExpiration(token);
        assertEquals(expectedExpiration, actualExpiration);
    }
    @Test
    void testIsTokenExpired() {
        String token = "example.jwt.token";
        Date expirationDate = new Date(System.currentTimeMillis() - 10000);

        // Mocking the extractExpiration method
        JwtService spyJwtService = Mockito.spy(jwtService);
        doReturn(expirationDate).when(spyJwtService).extractExpiration(token);

        assertTrue(spyJwtService.isTokenExpired(token));
    }

    @Test
    void testValidateToken() {
        String token = "example.jwt.token";
        String username = "testuser";
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn(username);

        // Mocking the extractUsername method
        JwtService spyJwtService = Mockito.spy(jwtService);
        doReturn(username).when(spyJwtService).extractUsername(token);
        doReturn(false).when(spyJwtService).isTokenExpired(token);

        assertTrue(spyJwtService.validateToken(token, userDetails));
    }

    //    @Test
//    void testExtractAllClaims() {
//        String token = Jwts.builder()
//                .setSubject("testuser")
//                .signWith(secretKey)
//                .compact();
//
//        Claims claims = jwtService.extractAllClaims(token);
//        assertNotNull(claims);
//        assertEquals("testuser", claims.getSubject());
//    }
//    @Test
//    void testExtractClaim() {
//        String token = Jwts.builder()
//                .setSubject("testuser")
//                .signWith(secretKey)
//                .compact();
//
//        String claim = jwtService.extractClaim(token, Claims::getSubject);
//        assertEquals("testuser", claim);
//    }
    @Test
    void testGetToken() {
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        String token = jwtService.createToken(claims, username);

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }
    @Test
    void testGetSignKey() {
        Key signKey = jwtService.getSignKey();
        assertNotNull(signKey);
        assertEquals(secretKey.getAlgorithm(), signKey.getAlgorithm());
        assertEquals(secretKey.getFormat(), signKey.getFormat());
    }
    @Test
    void testCreateToken() {
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        String token = jwtService.createToken(claims, username);

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }
    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }

}