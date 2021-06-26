package com.paseto.legitSite;

import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.lang.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 * Real server
 *
 */
@Profile("legitSite")
@RestController()
public class DataController {

    private final SecretKey secretKey = Keys.secretKey();
    private final Map<String, UserData> registeredUsers;

    @Autowired
    public DataController() {
        this.registeredUsers = getRegisteredUsers();
    }

    private Map<String, UserData> getRegisteredUsers() {
        Map<String, UserData> users = new HashMap<>();
        users.put("pesho", new UserData("somePassThatIsOk1212!", "Peshos data"));
        users.put("someOtherUser", new UserData("someOtherUserOk1212!", "someOtherUser's data"));
        return users;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getUserData(@CookieValue(name="paseto") String token) {
        if (token == null) {
            return new ResponseEntity<>("No token", HttpStatus.BAD_REQUEST);
        }
        Paseto paseto = Parser.parseLocalToken(token, secretKey);
        String userId = paseto.getClaims().get("userId", String.class);
        UserData userData = registeredUsers.get(userId);
        return new ResponseEntity<>(userData.getData(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials, HttpServletResponse response) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (isRegisteredUser(username, password)) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", registeredUsers.get(username).getId());
            response.addCookie(new Cookie("paseto",Token.createLocalTokenLastingDays(secretKey, claims, 3)));
            return  new ResponseEntity<>("Successful Login", HttpStatus.OK);
        }
        return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
    }

    private boolean isRegisteredUser(String username, String password) {
        if (!registeredUsers.containsKey(username)) {
            return false;
        }
        UserData user = registeredUsers.get(username);
        return user.isValidPassword(password);
    }
}
