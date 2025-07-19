package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILabelRepository extends JpaRepository<LabelEntity, Long> {
}
