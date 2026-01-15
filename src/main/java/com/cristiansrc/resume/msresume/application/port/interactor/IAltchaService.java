package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.AltchaChallengeResponse;

public interface IAltchaService {
    AltchaChallengeResponse createChallenge();
    void validateChallenge(String altchaResponse);
}
