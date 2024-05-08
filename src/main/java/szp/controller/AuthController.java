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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import szp.model.AuthRequestDTO;
import szp.model.EmployeeModel;
import szp.model.RefreshToken;
import szp.model.Role;
import szp.repository.EmployeeRepository;
import szp.service.JwtService;
import szp.service.RefreshTokenService;

import java.util.Optional;
import java.util.logging.Logger;

import static szp.service.JwtService.TokenType.ACCESS_TOKEN;
import static szp.service.JwtService.TokenType.REFRESH_TOKEN;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger LOGGER = Logger.getLogger("AuthController");
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    @Value("${jwt.access-token.expiry}")
    private int accessTokenCookieExpiry;
    @Value("${jwt.refresh-token.expiry}")
    private int refreshTokenExpiry;

    @GetMapping("/")
    public String loginPage() {
        return "login-page";
    }

    @GetMapping("/login/success/redirect")
    public String getLoginRedirectUrl(Model model, HttpServletRequest request) {
        EmployeeModel user = extractEmployeeFrom(request);
        model.addAttribute("currentUser", user);
        if (request.isUserInRole(Role.HR.toString()))
            return "hr/hr";
        if (request.isUserInRole(Role.ADMIN.toString()))
            return "admin/admin";
        if (request.isUserInRole(Role.EMPLOYEE.toString()))
            return "employee/employee";
        throw new IllegalArgumentException();
    }

    @PostMapping(value = "/login")
    public String authenticateAndGetToken(Model model, AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()){
                refreshTokenService.deleteTokenIfExistsByUsername(authRequestDTO.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                String accessToken = jwtService.generateToken(authRequestDTO.getUsername());
                response.addCookie(createAccessTokenCookie(accessToken));
                response.addCookie(createRefreshTokenCookie(refreshToken.getToken()));
                LOGGER.info("User with username [%s] - authenticated".formatted(authRequestDTO.getUsername()));
                return "redirect:/auth/login/success/redirect";
            }
            model.addAttribute("errorMessage", "Incorrect username or password.");
            return "login-page";
        } catch (Exception e){
            model.addAttribute("errorMessage", "Incorrect username or password.");
            return "login-page";
        }
    }

    @GetMapping("/logout")
    @ResponseBody
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
    @ResponseBody
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

    private EmployeeModel extractEmployeeFrom(HttpServletRequest request) {
        String token = jwtService.getToken(request, ACCESS_TOKEN).orElseThrow();
        String username = jwtService.extractUsername(token);
        return employeeRepository.findByLogin(username).orElseThrow();
    }
}
