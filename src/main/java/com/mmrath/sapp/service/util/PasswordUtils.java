package com.mmrath.sapp.service.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * Created by Murali
 */
public abstract class PasswordUtils {

    private static final String ALGORITHM = "SHA-256";

    private PasswordUtils() {

    }

    public static final String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        return encodedSalt;
    }

    public static final String encodePassword(String password, String salt) {
        MessageDigest messageDigest = getMessageDigest();
        String saltedPass = password + "{" + salt.toString() + "}";

        byte[] digest = new byte[0];
        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedSaltedPassword = Base64.getEncoder().encodeToString(digest);
        return encodedSaltedPassword;
    }

    public static final boolean isPasswordValid(String encodedPassword, String password,
            String salt) {

        String passwordSaltedEncoded = encodePassword(password, salt);
        return encodedPassword.equals(passwordSaltedEncoded);
    }

    /**
     * Get a MessageDigest instance for the given algorithm. Throws an IllegalArgumentException if
     * <i>algorithm</i> is unknown
     *
     * @return MessageDigest instance
     * @throws IllegalArgumentException if NoSuchAlgorithmException is thrown
     */
    private static final MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + ALGORITHM + "]");
        }
    }

}

