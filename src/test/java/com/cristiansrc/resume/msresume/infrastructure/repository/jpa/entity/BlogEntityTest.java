package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlogEntityTest {

    @Test
    void testInstantiation() {
        BlogEntity entity = new BlogEntity();
        assertNotNull(entity);
    }
}
