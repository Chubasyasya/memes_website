package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    public static String generateSalt(){
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        StringBuilder saltString = new StringBuilder();
        for (byte b : salt) {
            saltString.append(String.format("%02x", b));
        }
        return saltString.toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            String saltedPassword = password + salt;

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedPassword = md.digest(saltedPassword.getBytes());

            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
