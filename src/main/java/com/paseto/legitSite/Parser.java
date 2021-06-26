package com.paseto.legitSite;

import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.Pasetos;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class Parser {

    public static Paseto parseLocalToken(String token, SecretKey secretKey) {
        return Pasetos
                .parserBuilder()
                .setSharedSecret(secretKey)
                .build()
                .parse(token);
    }
    public static Paseto parsePublicToken(String token, PublicKey publicKey) {
        return Pasetos
                .parserBuilder()
                .setPublicKey(publicKey)
                .build()
                .parse(token);
    }

}
