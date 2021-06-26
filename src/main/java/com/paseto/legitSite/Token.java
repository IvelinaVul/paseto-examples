package com.paseto.legitSite;

import dev.paseto.jpaseto.PasetoV2LocalBuilder;
import dev.paseto.jpaseto.Pasetos;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Token {

    public static String createLocal(SecretKey secretKey) {
        return Pasetos.V2.LOCAL.builder()
                .setSharedSecret(secretKey)
                .setSubject("Local Token Example")
                .claim("id", 12121212)
                .claim("username", "someAdminName")
                .claim("isAdmin", true)
                .setFooter("Footer")
                .compact();
    }

    public static String createPublic(PrivateKey privateKey) {
        Instant threeDaysAfterToday = Instant.now().plus(3, ChronoUnit.DAYS);
        return Pasetos.V1.PUBLIC.builder()
                .setPrivateKey(privateKey)
                .setExpiration(threeDaysAfterToday)
                .setSubject("Public token example")
                .claim("id", 12121212)
                .claim("username", "someAdminName")
                .claim("isAdmin", true)
                .setFooter("Footer")
                .compact();
    }

    public static String createLocalTokenLastingDays(SecretKey secretKey, Map<String, String> claims, int days) {
        Instant threeDaysAfterToday = Instant.now().plus(days, ChronoUnit.DAYS);
        PasetoV2LocalBuilder builder = Pasetos.V2.LOCAL.builder();
        builder.setSharedSecret(secretKey);
        builder.setExpiration(threeDaysAfterToday);
        builder.setSubject("AuthToken");
        for (String claim : claims.keySet()) {
            builder.claim(claim, claims.get(claim));
        }
        return builder.compact();
    }
}

