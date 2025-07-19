package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHomeRepository extends JpaRepository<HomeEntity, Long> {
}
