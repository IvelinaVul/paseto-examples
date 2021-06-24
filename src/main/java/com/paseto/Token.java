package com.paseto;

import dev.paseto.jpaseto.Pasetos;

import javax.crypto.SecretKey;
import java.security.PrivateKey;

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
        return Pasetos.V1.PUBLIC.builder()
                .setPrivateKey(privateKey)
                .setSubject("Public token example")
                .claim("id", 12121212)
                .claim("username", "someAdminName")
                .claim("isAdmin", true)
                .setFooter("Footer")
                .compact();
    }
}

