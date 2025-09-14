package com.dareTo.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Password {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
