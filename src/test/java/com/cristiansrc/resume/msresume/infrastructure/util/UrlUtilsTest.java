package com.cristiansrc.resume.msresume.infrastructure.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlUtilsTest {

    @Test
    void getCreatedResourceUri() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = UrlUtils.getCreatedResourceUri(1L);
        assertEquals("http://localhost/test/1", uri.toString());
    }

    @Test
    void getCreatedResourceUri_withQuery() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test");
        request.setQueryString("q=test");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = UrlUtils.getCreatedResourceUri(1L);
        assertEquals("http://localhost/test/1", uri.toString());
    }

    @Test
    void getCreatedResourceUri_noRequest() {
        RequestContextHolder.resetRequestAttributes();
        assertThrows(IllegalStateException.class, () -> UrlUtils.getCreatedResourceUri(1L));
    }
}
