package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
public abstract class AuditBasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    private Timestamp updated;

    @Column(nullable = false)
    @Setter
    private Boolean deleted;

    @PrePersist
    protected void onCreate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.created = currentTimestamp;
        this.updated = currentTimestamp;

        if(this.deleted == null) {
            this.deleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Timestamp(System.currentTimeMillis());
    }
}
