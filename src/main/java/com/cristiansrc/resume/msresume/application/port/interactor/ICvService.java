package com.cristiansrc.resume.msresume.application.port.interactor;

import org.springframework.core.io.Resource;

public interface ICvService {
    Resource generateCurriculum(String language);
}
