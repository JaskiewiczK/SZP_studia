package szp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This is the JwtService class that handles the JWT related operations.
 * It is annotated with @Service and @Component, meaning it's a Spring service component and it's ready to be autowired as a bean.
 */
@Service
@Component
public class JwtService {
    /**
     * This method sets the expiry time for the access token.
     * @param accessTokenExpiry The expiry time for the access token.
     */
    public void setAccessTokenExpiry(int accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    /**
     * This is the TokenType enumeration that represents the type of the token.
     * It can be one of the following: ACCESS_TOKEN, REFRESH_TOKEN.
     */
    public enum TokenType {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken");
        private String header;
        TokenType(String header) {
            this.header = header;
        }

        /**
         * This method returns the header of the token type.
         * @return String representing the header of the token type.
         */
        public String getHeader() {
            return header;
        }
    }

    @Value("${jwt.access-token.expiry}")
    private int accessTokenExpiry;
    @Value("${jwt.secret-key}")
    public String SECRET;

    /**
     * This method extracts the username from the token.
     * @param token The JWT token.
     * @return String representing the username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method extracts the expiration date from the token.
     * @param token The JWT token.
     * @return Date representing the expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * This method extracts a claim from the token.
     * @param token The JWT token.
     * @param claimsResolver Function to resolve the claim.
     * @return The resolved claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This method extracts all claims from the token.
     * @param token The JWT token.
     * @return Claims object containing all the claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * This method checks if the token is expired.
     * @param token The JWT token.
     * @return boolean indicating whether the token is expired or not.
     */
    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * This method validates the token.
     * @param token The JWT token.
     * @param userDetails UserDetails object containing user-specific data.
     * @return boolean indicating whether the token is valid or not.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * This method generates a token for a username.
     * @param username The username for which the token is to be generated.
     * @return String representing the generated token.
     */
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * This method creates a token with the given claims and username.
     * @param claims Map containing the claims.
     * @param username The username for which the token is to be created.
     * @return String representing the created token.
     */
    String createToken(Map<String, Object> claims, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiry * 1000L);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(getSignKey()).compact();
    }

    /**
     * This method gets the signing key.
     * @return SecretKey object representing the signing key.
     */
    SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * This method gets the token from the request.
     * @param request HttpServletRequest object to get request information for HTTP servlets.
     * @param tokenType TokenType object representing the type of the token.
     * @return Optional that may contain the token if found.
     */
    public Optional<String> getToken(HttpServletRequest request, TokenType tokenType) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(request.getCookies()).map(Arrays::stream).orElse(Stream.empty())
                .filter(cookie -> cookie.getName().equals(tokenType.getHeader()))
                .map(Cookie::getValue).findFirst();
    }
}