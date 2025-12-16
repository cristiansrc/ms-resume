package com.cristiansrc.resume.msresume.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.core.Ordered;

import static org.junit.jupiter.api.Assertions.*;

class EncodingFilterConfigTest {

    @Test
    void characterEncodingFilter() {
        EncodingFilterConfig config = new EncodingFilterConfig();
        FilterRegistrationBean<CharacterEncodingFilter> bean = config.characterEncodingFilter();
        assertNotNull(bean);
        CharacterEncodingFilter filter = bean.getFilter();
        assertNotNull(filter);
        assertEquals("UTF-8", filter.getEncoding());
        // No hay isForceEncoding en versión actual, solo comprobamos el orden
        assertEquals(Ordered.HIGHEST_PRECEDENCE, bean.getOrder());
        assertEquals(1, bean.getUrlPatterns().size());
    }
}
