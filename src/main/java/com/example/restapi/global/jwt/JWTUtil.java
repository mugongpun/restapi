package com.example.restapi.global.jwt;

import com.example.restapi.global.ApiResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 토큰 생성 및 검증
 */
@Component
public class JWTUtil {
    private KeyStore keystore;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            keystore = KeyStore.getInstance("PKCS12");
            InputStream fis = getClass().getClassLoader()
                                        .getResourceAsStream("certificate.p12");
            keystore.load(fis, "restapi".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new CustomKeyStoreException("FAIL LOADING KEYSTORE", e);
        }
    }

    public String createToken(Map<String, Object> valueMap, int min) {
        try {
            privateKey = (PrivateKey) keystore.getKey("Mycert", "restapi".toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new CustomKeyStoreException("FAIL CREATE TOKEN", e);
        }
        return Jwts.builder()
                   .header()
                   .add("typ", "JWT")
                   .add("alg", "RS256")
                   .and()
                   .issuedAt(Date.from(ZonedDateTime.now()
                                                    .toInstant()))
                   .expiration(Date.from(ZonedDateTime.now()
                                                      .plusMinutes(min)
                                                      .toInstant()))
                   .claims(valueMap)
                   .signWith(privateKey, SignatureAlgorithm.RS256)
                   .compact();
    }

    public Map<String, Object> validateToken(String token) {
        try {
            PublicKey publicKey = keystore.getCertificate("Mycert").getPublicKey();

            return Jwts.parser()
                       .verifyWith(publicKey)
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();
        } catch (KeyStoreException e) {
            throw new CustomKeyStoreException(e.getMessage(), e);
        }
    }

    @ExceptionHandler(CustomKeyStoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleJWTError(CustomKeyStoreException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ApiResponse.failure(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }


    private class CustomKeyStoreException extends RuntimeException {
        public CustomKeyStoreException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
