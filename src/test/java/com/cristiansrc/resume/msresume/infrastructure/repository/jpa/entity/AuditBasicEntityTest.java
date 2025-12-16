package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuditBasicEntityTest {

    static class TestEntity extends AuditBasicEntity {
    }

    @Test
    void testEntity() {
        TestEntity entity = new TestEntity();

        assertNotNull(entity.toString());
    }
}
