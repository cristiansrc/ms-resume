package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AuditBasicEntityTest {

    static class TestEntity extends AuditBasicEntity {
    }

    @Test
    void testEntity() {
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));

        assertEquals(1L, entity.getId());
        assertNotNull(entity.getCreated());
        assertNotNull(entity.toString());
    }
}
