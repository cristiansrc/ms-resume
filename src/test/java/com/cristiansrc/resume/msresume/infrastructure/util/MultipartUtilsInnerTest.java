package com.cristiansrc.resume.msresume.infrastructure.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MultipartUtilsInnerTest {

    @Test
    void multipart_withNullFilename_behaviour() throws Exception {
        String original = "abc";
        String base64 = java.util.Base64.getEncoder().encodeToString(original.getBytes());
        MultipartFile m = MultipartUtils.base64ToMultipart(base64, null);

        assertNotNull(m);
        assertEquals("", m.getName());
        assertEquals("", m.getOriginalFilename());
        assertEquals("application/octet-stream", m.getContentType());
        assertFalse(m.isEmpty());
        assertArrayEquals(original.getBytes(), m.getBytes());
        assertEquals(original.length(), m.getSize());

        // transferTo writes content
        File tmp = File.createTempFile("mu-", ".tmp");
        try {
            m.transferTo(tmp);
            assertTrue(tmp.length() > 0);
        } finally {
            tmp.delete();
        }
    }

    @Test
    void innerClass_getInputStream_throwsWhenContentNull() throws Exception {
        // utilizar reflexión para instanciar la clase privada y pasar null fileContent
        Class<?>[] nested = MultipartUtils.class.getDeclaredClasses();
        Class<?> target = null;
        for (Class<?> c : nested) {
            if (c.getSimpleName().equals("Base64DecodedMultipartFile")) {
                target = c;
                break;
            }
        }
        assertNotNull(target);
        // localizar constructor (byte[], String, String)
        Constructor<?> ctor = target.getDeclaredConstructor(byte[].class, String.class, String.class);
        ctor.setAccessible(true);
        Object instance = ctor.newInstance((Object) null, null, null);

        // invocar métodos vía interfaz MultipartFile
        MultipartFile mf = (MultipartFile) instance;
        assertTrue(mf.isEmpty());
        assertThrows(IOException.class, mf::getInputStream);
    }
}

