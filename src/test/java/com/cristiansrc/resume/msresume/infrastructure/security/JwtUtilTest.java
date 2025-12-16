package com.cristiansrc.resume.msresume.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String USERNAME = "testUser";

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();
        setField(jwtUtil, "secret", Base64.getEncoder().encodeToString("a-very-long-and-secure-secret-key-for-testing".getBytes()));
        setField(jwtUtil, "expiration", 3600000L); // 1 hour
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void generateToken_createsValidToken() {
        String token = jwtUtil.generateToken(USERNAME);
        assertNotNull(token);
        assertEquals(USERNAME, jwtUtil.extractUsername(token));
    }

    @Test
    void validateToken_withValidTokenAndCorrectUser_returnsTrue() {
        String token = jwtUtil.generateToken(USERNAME);
        assertTrue(jwtUtil.validateToken(token, USERNAME));
    }

    @Test
    void validateToken_withValidTokenAndIncorrectUser_returnsFalse() {
        String token = jwtUtil.generateToken(USERNAME);
        assertFalse(jwtUtil.validateToken(token, "wrongUser"));
    }

    @Test
    void validateToken_withExpiredToken_returnsFalse() throws Exception {
        setField(jwtUtil, "expiration", -1000L); // Expired
        String token = jwtUtil.generateToken(USERNAME);
        assertFalse(jwtUtil.validateToken(token, USERNAME));
    }

    @Test
    void validateToken_withMalformedToken_returnsFalse() {
        assertFalse(jwtUtil.validateToken("a-malformed-token", USERNAME));
    }

    @Test
    void extractExpiration_fromValidToken_returnsFutureDate() {
        String token = jwtUtil.generateToken(USERNAME);
        Date expiration = jwtUtil.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void extractExpiration_fromExpiredToken_throwsExpiredJwtException() throws Exception {
        setField(jwtUtil, "expiration", -1000L); // Expired
        String token = jwtUtil.generateToken(USERNAME);
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractExpiration(token));
    }

    @Test
    void isTokenExpired_withValidToken_returnsFalse() throws Exception {
        String token = jwtUtil.generateToken(USERNAME);
        
        Method method = JwtUtil.class.getDeclaredMethod("isTokenExpired", String.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(jwtUtil, token);
        
        assertFalse(result);
    }

    @Test
    void isTokenExpired_withExpiredToken_throwsExpiredJwtException() throws Exception {
        setField(jwtUtil, "expiration", -1000L); // Expired
        String token = jwtUtil.generateToken(USERNAME);
        
        Method method = JwtUtil.class.getDeclaredMethod("isTokenExpired", String.class);
        method.setAccessible(true);
        
        try {
            method.invoke(jwtUtil, token);
            fail("Expected InvocationTargetException wrapping ExpiredJwtException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof ExpiredJwtException);
        }
    }
}
