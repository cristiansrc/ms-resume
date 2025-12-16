package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    @Test
    void auditBasicEntity_onCreate_and_onUpdate() throws InterruptedException {
        AuditBasicEntity entity = new AuditBasicEntity() {};
        // aseguramos valores nulos antes de persist
        assertNull(entity.getCreated());
        assertNull(entity.getUpdated());
        assertNull(entity.getDeleted());

        entity.onCreate();
        assertNotNull(entity.getCreated());
        assertNotNull(entity.getUpdated());
        assertFalse(entity.getDeleted());

        Timestamp before = entity.getUpdated();
        Thread.sleep(10);
        entity.onUpdate();
        assertTrue(entity.getUpdated().after(before));
    }

    @Test
    void homeEntity_builder_and_setters() {
        ImageUrlEntity img = ImageUrlEntity.builder().name("n").nameEng("ne").nameFileAws("f").build();
        LabelEntity label = LabelEntity.builder().name("l").nameEng("le").build();

        HomeEntity home = HomeEntity.builder()
                .greeting("g")
                .buttonContactLabel("c")
                .buttonWorkLabel("w")
                .imageUrl(img)
                .build();

        assertEquals("g", home.getGreeting());
        assertEquals("c", home.getButtonContactLabel());
        assertEquals("w", home.getButtonWorkLabel());
        assertEquals(img, home.getImageUrl());

        home.setGreeting("g2");
        assertEquals("g2", home.getGreeting());

        home.setLabels(java.util.List.of(label));
        assertNotNull(home.getLabels());
        assertEquals(1, home.getLabels().size());
    }

    @Test
    void imageLabel_entities_builder() {
        ImageUrlEntity i = ImageUrlEntity.builder().name("n").nameEng("ne").nameFileAws("f").build();
        assertEquals("n", i.getName());

        LabelEntity l = LabelEntity.builder().name("ln").nameEng("le").build();
        assertEquals("ln", l.getName());
    }
}

