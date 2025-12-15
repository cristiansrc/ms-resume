package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlogEntityTest {

    @Test
    void testEntity() {
        BlogEntity entity = new BlogEntity();
        entity.setId(1L);
        entity.setTitle("Title");

        assertEquals(1L, entity.getId());
        assertEquals("Title", entity.getTitle());
        assertNotNull(entity.toString());
    }
}
