package hangman;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code StringUtilities} class contains small methods relating to 
 * {@code String} objects.
 *
 * @author Oliver Abdulrahim
 */
public final class StringUtilities {
    
    /**
     * Don't let anyone instantiate this class.
     */
    private StringUtilities() {
        throw new InstantiationError();
    }
    
    /**
     * Generates a pseudorandom {@code char} value from 
     * {@link Character#MIN_VALUE} ({@code 0}), inclusive, to 
     * {@link Character#MAX_VALUE} ({@code 65535}), inclusive.
     * 
     * @return A random {@code char}.
     */
    public static char randomChar() {
        return randomChar(Character.MIN_VALUE, Character.MAX_VALUE);
    }
    
    /**
     * Generates a pseudorandom {@code char} value from the given lower bound
     * (inclusive) to the given upper bound (inclusive).
     * 
     * @param lower The lower bound, inclusive.
     * @param upper The Upper bound, inclusive.
     * @return A random {@code char} value.
     */
    public static char randomChar(int lower, int upper) {
        return (char) ThreadLocalRandom.current().nextInt(lower, upper + 1);
    }

    /**
     * Generates a pseudorandom {@code String} object. May include {@code char}
     * values from {@code lower} to {@code upper} inclusive.
     * 
     * @param lower Lower bound, inclusive.
     * @param upper Upper bound, inclusive.
     * @param length The length of the {@code String} to generate.
     * @return A random {@code String} object with the given length.
     * @see #randomChar()
     */
    public static String random(char lower, char upper, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length : " + length + " < 0 !");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomChar(lower, upper));
        }
        return sb.toString();
    }

    /**
     * Generates an alphabetic {@code String} of the specified length. May 
     * contain characters between {@code 'a'}, inclusive, and {@code 'z'}, 
     * inclusive.
     * 
     * @param length The amount of characters in the {@code String} to generate.
     * @return A random, alphabetic {@code String} of the specified length.
     * @see #random(char, char, int)
     */
    public static String randomAlphaString(int length) {
        return StringUtilities.random('a', 'z', length);
    }
    
}

