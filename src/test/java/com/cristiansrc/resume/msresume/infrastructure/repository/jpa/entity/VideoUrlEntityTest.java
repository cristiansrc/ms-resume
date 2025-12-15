package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VideoUrlEntityTest {

    @Test
    void testEntity() {
        VideoUrlEntity entity = new VideoUrlEntity();
        entity.setId(1L);
        entity.setName("Video");

        assertEquals(1L, entity.getId());
        assertEquals("Video", entity.getName());
        assertNotNull(entity.toString());
    }
}
