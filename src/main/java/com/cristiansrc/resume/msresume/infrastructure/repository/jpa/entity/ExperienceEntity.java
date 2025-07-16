package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "experience")
public class ExperienceEntity extends AuditBasicEntity {

    @Column(name = "year_start", nullable = false)
    @NotNull
    private String yearStart;

    @Column(name = "year_end", nullable = false)
    @NotNull
    private String yearEnd;

    @Column(nullable = false)
    @NotNull
    private String company;

    @Column(nullable = false)
    @NotNull
    private String description;
}
