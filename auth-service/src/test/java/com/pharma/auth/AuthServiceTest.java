package com.pharma.auth;

import com.pharma.auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.flyway.enabled=false",
    "jwt.secret=test-secret-key-must-be-32-characters-long"
})
class AuthServiceTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testTokenGenerationAndValidation() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");
        assertThat(token).isNotBlank();
        assertThat(jwtUtil.validateToken(token)).isTrue();
        assertThat(jwtUtil.getUsernameFromToken(token)).isEqualTo("testuser");
        assertThat(jwtUtil.getRoleFromToken(token)).isEqualTo("ADMIN");
    }
}
