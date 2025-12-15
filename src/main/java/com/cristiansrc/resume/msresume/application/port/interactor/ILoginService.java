package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPost200Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPostRequest;

public interface ILoginService {
    LoginPost200Response loginPost(LoginPostRequest loginPostRequest);
}
