package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "skill_type")
public class SkillTypeEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(name = "name_eng", nullable = false)
    @NotNull
    private String nameEng;

    @OneToMany(mappedBy = "skillType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SkillTypeRelationalEntity> skillTypeRelations = new ArrayList<>();

}
