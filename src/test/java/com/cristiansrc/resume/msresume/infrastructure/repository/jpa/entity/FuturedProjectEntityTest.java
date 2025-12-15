package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuturedProjectEntityTest {

    @Test
    void testEntity() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        entity.setId(1L);
        entity.setName("FuturedProject");

        assertEquals(1L, entity.getId());
        assertEquals("FuturedProject", entity.getName());
        assertNotNull(entity.toString());
    }
}
