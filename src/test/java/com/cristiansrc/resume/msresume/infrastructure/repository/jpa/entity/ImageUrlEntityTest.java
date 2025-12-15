package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageUrlEntityTest {

    @Test
    void testEntity() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setId(1L);
        entity.setName("Image");

        assertEquals(1L, entity.getId());
        assertEquals("Image", entity.getName());
        assertNotNull(entity.toString());
    }
}
