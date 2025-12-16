package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LabelEntityTest {

    @Test
    void testEntity() {
        LabelEntity entity = new LabelEntity();
        entity.setName("Label");

        assertEquals("Label", entity.getName());
        assertNotNull(entity.toString());
    }
}
