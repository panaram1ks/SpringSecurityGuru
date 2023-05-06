package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySALT";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
