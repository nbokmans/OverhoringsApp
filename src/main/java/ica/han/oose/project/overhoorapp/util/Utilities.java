package ica.han.oose.project.overhoorapp.util;

import java.util.Random;

/**
 * Some random utilities that may be useful somewhere.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 17-5-2015
 */
public class Utilities {

    /**
     * Stores allowed characters.
     */
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Stores an instance of a Random object.
     */
    private static final Random RANDOM = new Random();

    /**
     * Generates a random string with a predefined length.
     *
     * @param length The length of the string.
     * @return A random string with a predefined length.
     */
    public String generateRandomString(final int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALLOWED_CHARACTERS.charAt(RANDOM.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }
}
