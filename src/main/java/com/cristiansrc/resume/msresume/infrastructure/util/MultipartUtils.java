package com.cristiansrc.resume.msresume.infrastructure.util;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * Utilidades para trabajar con archivos en memoria.
 */
public final class MultipartUtils {

    private MultipartUtils() {
        // Evita instanciación
    }

    /**
     * Convierte una cadena Base64 en un MultipartFile.
     *
     * @param base64           Cadena en formato Base64. Puede venir con o sin el prefijo
     *                         "data:<mime>;base64," (por ejemplo, data:image/png;base64,...).
     * @param originalFilename Nombre de archivo que quieras asignar (p. ej. "foto.png").
     * @return MultipartFile listo para pasar a cualquier servicio Spring.
     */
    public static MultipartFile base64ToMultipart(String base64, String originalFilename) {
        if (base64 == null || base64.isBlank()) {
            throw new IllegalArgumentException("La cadena Base64 está vacía");
        }

        String[] parts = base64.split(",");
        String base64Data;                // solo la parte de datos
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        // ¿Trae prefijo “data:...”?
        if (parts.length == 2) {
            String header = parts[0];     // "data:image/png;base64"
            base64Data = parts[1];
            int idxColon = header.indexOf(':');
            int idxSemi  = header.indexOf(';');
            if (idxColon > -1 && idxSemi > -1) {
                contentType = header.substring(idxColon + 1, idxSemi);   // "image/png"
            }
        } else {
            base64Data = base64;          // viene “pelado”
        }

        byte[] fileContent = Base64.getDecoder().decode(base64Data);

        return new Base64DecodedMultipartFile(fileContent, originalFilename, contentType);
    }

    // Implementación propia de MultipartFile para evitar MockMultipartFile
    private static class Base64DecodedMultipartFile implements MultipartFile {
        private final byte[] fileContent;
        private final String fileName;
        private final String contentType;

        public Base64DecodedMultipartFile(byte[] fileContent, String fileName, String contentType) {
            this.fileContent = fileContent;
            this.fileName = fileName;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return fileName == null ? "" : fileName;
        }

        @Override
        public String getOriginalFilename() {
            return fileName == null ? "" : fileName;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return fileContent == null || fileContent.length == 0;
        }

        @Override
        public long getSize() {
            return fileContent.length;
        }

        @Override
        public byte[] getBytes() {
            return fileContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (fileContent == null) {
                throw new IOException("El contenido del archivo es nulo");
            }
            return new ByteArrayInputStream(fileContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                fos.write(fileContent);
            }
        }
    }
}
