package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBasicDataRepository extends JpaRepository<BasicDataEntity, Long> {
}
