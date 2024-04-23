package szp.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import szp.model.AuthRequestDTO;
import szp.model.RefreshToken;
import szp.service.JwtService;
import szp.service.RefreshTokenService;

import java.util.Optional;

import static szp.service.JwtService.TokenType.ACCESS_TOKEN;
import static szp.service.JwtService.TokenType.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${jwt.access-token.expiry}")
    private int accessTokenCookieExpiry;
    @Value("${jwt.refresh-token.expiry}")
    private int refreshTokenExpiry;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if(authentication.isAuthenticated()){
                refreshTokenService.deleteTokenIfExistsByUsername(authRequestDTO.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                String accessToken = jwtService.generateToken(authRequestDTO.getUsername());
                response.addCookie(createAccessTokenCookie(accessToken));
                response.addCookie(createRefreshTokenCookie(refreshToken.getToken()));
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(401).build();
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> token = jwtService.getToken(request, ACCESS_TOKEN);
        if (token.isPresent()) {
            String username = jwtService.extractUsername(token.get());
            refreshTokenService.deleteTokenIfExistsByUsername(username);
        }
        Cookie accessToken = createAccessTokenCookie("");
        Cookie refreshToken = createRefreshTokenCookie("");
        accessToken.setMaxAge(0);
        refreshToken.setMaxAge(0);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = jwtService.getToken(request, REFRESH_TOKEN).orElseThrow(EntityNotFoundException::new);
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmployeeModel)
                .map(empoleeModel -> {
                    String accessToken = jwtService.generateToken(empoleeModel.getLogin());
                    Cookie accessTokenCookie = createAccessTokenCookie(accessToken);
                    response.addCookie(accessTokenCookie);
                    return ResponseEntity.status(200).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB!"));
    }

    private Cookie createAccessTokenCookie(String accessToken) {
        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN.getHeader(), accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(accessTokenCookieExpiry);
        return accessTokenCookie;
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie accessTokenCookie = new Cookie(REFRESH_TOKEN.getHeader(), refreshToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/auth/refresh-token");
        accessTokenCookie.setMaxAge(refreshTokenExpiry);
        return accessTokenCookie;
    }
}
