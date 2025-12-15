package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LabelEntityTest {

    @Test
    void testEntity() {
        LabelEntity entity = new LabelEntity();
        entity.setId(1L);
        entity.setName("Label");

        assertEquals(1L, entity.getId());
        assertEquals("Label", entity.getName());
        assertNotNull(entity.toString());
    }
}
