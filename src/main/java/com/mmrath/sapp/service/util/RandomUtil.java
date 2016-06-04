package com.mmrath.sapp.service.util;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generates an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return generateRandomUid();
    }

    /**
     * Generates a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return generateRandomUid();
    }

    public static String generateRandomUid(){
        String key = UUID.randomUUID().toString().replace("-","") + System.currentTimeMillis();
        return key + RandomStringUtils.randomAlphanumeric(5);
    }
}
