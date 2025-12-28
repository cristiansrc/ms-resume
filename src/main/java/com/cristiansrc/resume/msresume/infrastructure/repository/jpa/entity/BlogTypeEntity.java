package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "blog_type")
public class BlogTypeEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(name = "name_eng", nullable = false)
    @NotNull
    private String nameEng;

}
