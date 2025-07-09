package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "blog")
public class BlogEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    @NotNull
    private String cleanUrlTitle;

    @Column(nullable = false)
    @NotNull
    private String descriptionShort;

    @Column(nullable = false)
    @NotNull
    private String description;
}
