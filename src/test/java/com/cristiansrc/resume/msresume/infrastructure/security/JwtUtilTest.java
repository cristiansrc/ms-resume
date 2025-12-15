package com.cristiansrc.resume.msresume.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Clave secreta de 256 bits codificada en Base64
        String secret = "dGhpcyBpcyBhIHZlcnkgc2VjdXJlIGFuZCBsb25nIHNlY3JldCBrZXkgZm9yIGp3dCB0ZXN0aW5n";
        org.springframework.test.util.ReflectionTestUtils.setField(jwtUtil, "secret", secret);
        org.springframework.test.util.ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L);
    }

    @Test
    void generateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
    }

    @Test
    void extractUsername() {
        String token = jwtUtil.generateToken("testuser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void validateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token, "testuser"));
    }
}
