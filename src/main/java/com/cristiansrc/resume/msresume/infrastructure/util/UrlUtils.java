package com.cristiansrc.resume.msresume.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.Optional;

public class UrlUtils {

    private UrlUtils() {
        // Utility class
    }

    /**
     * Obtiene la URL completa del endpoint actual y agrega el ID al final
     * @param id ID a agregar al final de la URL
     * @return URI completa con el ID
     */
    public static URI getCreatedResourceUri(Long id) {
        String currentUrl = getCurrentUrl();
        return URI.create(currentUrl + "/" + id);
    }

    /**
     * Obtiene la URL actual de la petición
     * @return URL actual sin query parameters
     */
    private static String getCurrentUrl() {
        HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .orElseThrow(() -> new IllegalStateException("No HttpServletRequest available"));

        String url = request.getRequestURL().toString();
        return url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
    }
}
