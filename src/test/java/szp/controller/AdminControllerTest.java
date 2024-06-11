package szp.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import szp.SzpApplication;
import szp.security.WebSecurityConfig;
import szp.service.JwtService;
import szp.model.Role;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SzpApplication.class)
@AutoConfigureMockMvc
@Import({WebSecurityConfig.class, JwtService.class})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setup() {
        adminToken = generateToken("admin", "ROLE_ADMIN");
        userToken = generateToken("user", "ROLE_USER");
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode("357638792F423F4428472B4B6250655368566D597133743677397A2443264629357638792F423F4428472B4B6250655368566D597133743677397A2443264629");
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hour validity
        SecretKey key = getSignKey();

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Test
    public void testMechanicControllerWithAdminRole() throws Exception {
        mockMvc.perform(get("/admin/")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Role.ADMIN.name()));
    }

}
