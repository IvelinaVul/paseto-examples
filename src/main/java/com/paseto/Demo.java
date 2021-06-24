package com.paseto;

import dev.paseto.jpaseto.Claims;
import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.Version;
import dev.paseto.jpaseto.lang.Keys;

import javax.crypto.SecretKey;
import java.security.KeyPair;

public class Demo {

    public void createAndUsePublicTokenTwice() {
        KeyPair keyPair = Keys.keyPairFor(Version.V1);
        String publicToken = createPublicToken(keyPair);
        usePublicToken(publicToken, keyPair);
        usePublicToken(publicToken, keyPair);
    }

    private String createPublicToken(KeyPair keyPair) {
        return Token.createPublic(keyPair.getPrivate());
    }

    private void usePublicToken(String publicToken, KeyPair keyPair) {
        Paseto paseto = Parser.parsePublicToken(publicToken, keyPair.getPublic());
        displayToken(publicToken, paseto);
    }

    private void createAndUseLocalToken() {
        SecretKey secretKey = Keys.secretKey();
        String localToken = Token.createLocal(secretKey);
        Paseto paseto = Parser.parseLocalToken(localToken, secretKey);
        displayToken(localToken, paseto);

    }

    private void displayToken(String token, Paseto paseto) {
        System.out.println();
        System.out.println(paseto.getPurpose());
        System.out.println("Token:");
        System.out.println(token);
        Claims claims = paseto.getClaims();
        System.out.println(claims.getSubject());
        System.out.println("Claims:");
        System.out.print("id: ");
        System.out.println(claims.get("id"));
        System.out.print("username: ");
        System.out.println(claims.get("username"));
        System.out.print("isAdmin: ");
        System.out.println(claims.get("isAdmin"));
        System.out.println("Footer:");
        System.out.println(paseto.getFooter().value());
        System.out.println(claims.getExpiration());
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.createAndUseLocalToken();
        demo.createAndUsePublicTokenTwice();
    }

}
