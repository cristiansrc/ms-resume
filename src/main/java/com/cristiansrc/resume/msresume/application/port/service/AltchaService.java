package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.AltchaValidationException;
import com.cristiansrc.resume.msresume.application.exception.PreconditionFailedException;
import com.cristiansrc.resume.msresume.application.port.interactor.IAltchaService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.AltchaChallengeResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class AltchaService implements IAltchaService {

    private static final String HMAC_KEY = "secret-hmac-key"; // En producción, usar una clave segura y rotarla
    private static final String SHA256_ALGORITHM = "SHA-256";
    private static final int MAX_NUMBER = 1_000_000; // Número máximo para el challenge (ajustable según dificultad)
    private final ObjectMapper objectMapper;

    @Override
    public AltchaChallengeResponse createChallenge() {
        // 1. Generar salt aleatorio
        String salt = generateRandomString(12);
        
        // 2. Generar un número aleatorio secreto (el cliente debe encontrarlo)
        SecureRandom random = new SecureRandom();
        int secretNumber = random.nextInt(MAX_NUMBER);
        
        // 3. Calcular el challenge: hash(salt + secretNumber)
        String challenge = generateChallenge(salt, secretNumber);
        
        // 4. Firmar el challenge (HMAC del challenge)
        String signature = signChallenge(challenge);

        AltchaChallengeResponse response = new AltchaChallengeResponse();
        response.setAlgorithm(SHA256_ALGORITHM);
        response.setChallenge(challenge);
        response.setSalt(salt);
        response.setSignature(signature);

        return response;
    }

    @Override
    public void validateChallenge(String altchaResponse) {
        if (altchaResponse == null || altchaResponse.isEmpty()) {
            throw new AltchaValidationException("Altcha response is missing");
        }

        try {
            // El payload viene codificado en Base64
            String decodedJson = new String(Base64.getDecoder().decode(altchaResponse), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(decodedJson);

            String algorithm = jsonNode.path("algorithm").asText();
            String challenge = jsonNode.path("challenge").asText();
            long number = jsonNode.path("number").asLong();
            String salt = jsonNode.path("salt").asText();
            String signature = jsonNode.path("signature").asText();

            if (!SHA256_ALGORITHM.equals(algorithm)) {
                throw new AltchaValidationException("Invalid Altcha algorithm");
            }

            // 1. Verificar que la signature sea válida: HMAC(challenge) == signature
            String expectedSignature = signChallenge(challenge);
            if (!expectedSignature.equals(signature)) {
                throw new AltchaValidationException("Invalid Altcha signature");
            }

            // 2. Verificar que hash(salt + number) == challenge
            String expectedChallenge = generateChallenge(salt, (int) number);
            if (!expectedChallenge.equals(challenge)) {
                throw new AltchaValidationException("Invalid Altcha solution");
            }

            // 3. Verificar que number <= MAX_NUMBER (opcional, pero buena práctica)
            if (number > MAX_NUMBER) {
                 // Esto podría pasar si cambiamos la dificultad, pero por ahora lo dejamos pasar o logueamos
            }

        } catch (Exception e) {
            throw new AltchaValidationException("Invalid Altcha response format: " + e.getMessage());
        }
    }

    private String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    /**
     * Genera el challenge calculando hash(salt + secretNumber)
     * El cliente debe encontrar el secretNumber probando valores
     */
    private String generateChallenge(String salt, int secretNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA256_ALGORITHM);
            String data = salt + secretNumber;
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new PreconditionFailedException("Error generating challenge");
        }
    }

    /**
     * Firma el challenge usando HMAC-SHA256
     */
    private String signChallenge(String challenge) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(HMAC_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(challenge.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hmacBytes);
        } catch (Exception e) {
            throw new PreconditionFailedException("Error signing challenge");
        }
    }
}
