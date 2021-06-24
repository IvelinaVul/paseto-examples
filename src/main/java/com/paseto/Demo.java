package com.paseto;

import dev.paseto.jpaseto.Claims;
import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.Version;
import dev.paseto.jpaseto.lang.Keys;

import javax.crypto.SecretKey;
import java.security.KeyPair;

public class Demo {

    private void usePublicToken() {
        KeyPair keyPair = Keys.keyPairFor(Version.V1);
        String publicToken = Token.createPublic(keyPair.getPrivate());
        Paseto paseto = Parser.parsePublicToken(publicToken, keyPair.getPublic());
        displayToken(publicToken, paseto);
    }

    private void useLocalToken() {
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
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.useLocalToken();
        demo.usePublicToken();
        demo.usePublicToken();
    }

}
