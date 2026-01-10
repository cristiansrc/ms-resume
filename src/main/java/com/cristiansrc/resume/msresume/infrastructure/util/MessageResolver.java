package com.cristiansrc.resume.msresume.infrastructure.util;

import com.cristiansrc.resume.msresume.application.exception.InvalidCredentialsException;
import com.cristiansrc.resume.msresume.application.exception.PreconditionFailedException;
import com.cristiansrc.resume.msresume.application.exception.RenderCvServiceException;
import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageResolver {
    private final MessageSource messageSource;

    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public ResourceNotFoundException notFound(String key, Object... args) {
        String msg = getMessage(key, args);
        return new ResourceNotFoundException(msg);
    }

    public InvalidCredentialsException invalidCredentials(String key, Object... args) {
        String msg = getMessage(key, args);
        return new InvalidCredentialsException(msg);
    }

    public PreconditionFailedException preconditionFailed(String key, Object... args) {
        String msg = getMessage(key, args);
        return new PreconditionFailedException(msg);
    }

    public RenderCvServiceException renderCvService(String key, Object... args) {
        String msg = getMessage(key, args);
        return new RenderCvServiceException(msg);
    }
}
