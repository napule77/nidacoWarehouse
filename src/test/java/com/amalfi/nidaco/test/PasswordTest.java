package com.amalfi.nidaco.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    @Test
    void verificaPasswordAdmin() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordChiara = "admin123";

        String hashNelDatabase =
                "$2a$10$1Z.x7HOe.HTngnsB0fqQUemoJvXBooziFHw3ZvF2d.DLPBJqfBfPm";

        boolean risultato =
                encoder.matches(passwordChiara, hashNelDatabase);

        System.out.println("Password corretta? " + risultato);
    }
}