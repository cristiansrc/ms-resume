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
@Table(name = "video_url")
public class VideoUrlEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(name = "name_eng", nullable = false)
    @NotNull
    private String nameEng;

    @Column(nullable = false)
    @NotNull
    private String url;
}
