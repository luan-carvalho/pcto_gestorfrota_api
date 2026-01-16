package br.edu.ifto.gestorfrotaapi.authentication.util;

import java.security.SecureRandom;

public class TokenGenerator {

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);

    }

}
