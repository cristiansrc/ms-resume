package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BasicDataEntityTest {

    @Test
    void testEntity() {
        BasicDataEntity entity = new BasicDataEntity();
        entity.setId(1L);
        entity.setFirstName("Test");
        entity.setFirstSurname("User");
        entity.setEmail("test@test.com");
        entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));

        assertEquals(1L, entity.getId());
        assertEquals("Test", entity.getFirstName());
        assertEquals("User", entity.getFirstSurname());
        assertEquals("test@test.com", entity.getEmail());
        assertNotNull(entity.getCreated());
        assertNotNull(entity.getUpdated());
        assertNotNull(entity.toString());
        assertNotNull(entity.hashCode());
        assertEquals(entity, entity);
    }
}
