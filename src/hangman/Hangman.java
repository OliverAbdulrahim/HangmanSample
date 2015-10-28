package hangman;

import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * The {@code Hangman} class contains the logic for a game of "Hangman." Game
 * operations, such as guessing substrings, are executed through methods
 * contained within this class.
 *
 * @author Oliver Abdulrahim
 * @see language.Dictionary
 * @see language.Word
 */
public final class Hangman {
    
    /**
     * Constant representing the "easy" difficulty setting.
     */
    public static final int EASY_DIFFICULTY = 0;
    
    /**
     * Constant representing the "medium" difficulty setting.
     */
    public static final int MEDIUM_DIFFICULTY = 1;
    
    /**
     * Constant representing the "hard" difficulty setting.
     */
    public static final int HARD_DIFFICULTY = 2;

    /**
     * The place-holding delimiter for the {@code String} that stores correct
     * guesses. Substrings that have not been guessed correctly are represented
     * by this {@code String} in the same index value within the
     * {@link #correctGuesses} object as in the {@link #currentWord} for this
     * instance.
     *
     * @see #correctGuesses
     */
    private static final String GUESS_DELIMITER = "_";
    
    /**
     * Stores words considered to be "easy."
     */
    private List<Word> easyWords;
    
    /**
     * Stores words considered to be "medium."
     */
    private List<Word> mediumWords;
    
    /**
     * Stores words considered to be "hard."
     */
    private List<Word> hardWords;
    
    /**
     * Stores the word that is being guessed.
     */
    private String currentWord;

    /**
     * Stores the characters have already been guessed. This {@code String}
     * stores all guesses, including those that are incorrect as well as those
     * that are correct.
     */
    private String previouslyGuessed;

    /**
     * Stores the characters have been guessed correctly, (i.e. they exist in 
     * {@link #currentWord}). In other words, this {@code String} stores the 
     * union between the current word and the characters that have already been
     * guessed.
     */
    private String correctGuesses;

    /**
     * Stores the amount of character guesses that are left in this game. This
     * value is concurrent with the correct image in the {@link #images} for 
     * this instance and may be used as an index value when retrieving values 
     * from the array for display purposes.
     */
    private int guessesLeft;

    /**
     * Stores the current image repository for use in a graphical interface.
     */
    private ImageIcon[] images;

    /**
     * Initializes a new game with medium difficulty 
     * ({@link #MEDIUM_DIFFICULTY}).
     */
    public Hangman() {
        // TODO - Image reading, read and categorize words from a file
        resetGame(MEDIUM_DIFFICULTY);
    }

    /**
     * Initializes a new game with the given difficulty. This method resets all
     * game-related attributes to their default state.
     *
     * @param difficulty The difficulty setting to use for this game.
     */
    public void resetGame(int difficulty) {
        // TODO - Difficulty settings, game variable reset
    }

// Getters and setters (yay, encapsulation)

    /**
     * Returns the current word for this game instance.
     *
     * @return The current word of this instance.
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Returns a {@code String} containing all the characters that have already
     * been guessed in this game.
     *
     * @return The characters that have already been guessed.
     */
    public String getPreviouslyGuessed() {
        return previouslyGuessed;
    }

    /**
     * Returns a {@code String} containing all the characters that have been
     * guessed correctly in this game instance.
     *
     * @return The characters that have already been guessed correctly.
     */
    public String getCorrectGuesses() {
        return correctGuesses;
    }
    
    /**
     * Returns the amount of incorrect guesses remaining for this game.
     *
     * @return The amount of incorrect guesses remaining for this game.
     */
    public int getGuessesLeft() {
        return guessesLeft;
    }

    /**
     * Returns the images of this game instance.
     *
     * @return The images of this instance.
     */
    public ImageIcon[] images() {
        return Arrays.copyOf(images, images.length);
    }
    
// Game-state methods    
    
    /**
     * Returns {@code true} if this game allows guesses at this point in time,
     * {@code false} otherwise.
     * 
     * @return {@code true} if this game allows guesses at this point in time,
     *         {@code false} otherwise.
     */
    public boolean canGuess() {
        return guessesLeft > 0;
    }
    
    /**
     * Returns the maximum amount of guesses allowed to this game instance.
     * 
     * @return The maximum amount of guesses for this game instance.
     */
    public int maxGuesses() {
        return images.length - 1;
    }
    
    /**
     * Tests the state of the game, returning {@code true} if the game has been
     * won, {@code false} otherwise.
     * 
     * <p> This method tests if the correct guesses are the same as the actual 
     * word and there are greater than zero guesses remaining.
     * 
     * @return {@code true} if the game has been won, {@code false} otherwise.
     */
    public boolean hasWon() {
        return correctGuesses.equals(currentWord) && guessesLeft > 0;
    }
    
// Gameplay methods   
    
    /**
     * The entry point for game guesses. Attempts to make the given character
     * guess, returning {@code true} if the guess was made, {@code false} 
     * otherwise.
     * 
     * <p> In order to correctly process the given {@code String}, this method 
     * takes a formulaic approach to either reject or accept the guess. Guesses 
     * are processed in the following way: 
     *   <ol> 
     *     <li> Sanitizes the guess to ensure that it is uniform in case and
     *          formatting with this instance's current word. 
     *     <li> Tests if the guess has already been made, returning
     *          {@code false} if it has.
     *     <li> Otherwise, adds the word to the set of already guessed 
     *          characters.
     *     <li> Tests if the guess is in the current word.
     *       <ul>
     *          <li> If the given character is in the current word, updates the
     *               set of already guessed characters and returns {@code true}.
     *          <li> Otherwise, decrements the amount of guesses remaining and
     *               returns {@code false}.
     *       </ul>
     *   </ol>
     * 
     * <p> In other words, this method will return {@code true} if and only if 
     * the guess satisfies all of the following conditions, {@code false} 
     * otherwise:
     *   <ul>
     *     <li> The guess is in the current word and by extension, the game has
     *          not already been won.
     *     <li> The guess is not in the set of already guessed characters.
     *   </ul>
     * 
     * @param guess The character to attempt to guess for.
     * @return {@code true} if the guess was correct, {@code false} otherwise.
     */
    public boolean makeGuess(String guess) {
        String g = Word.sanitizeString(guess);
        // TODO - Add logic for making guesses as delineated above.
        return false;
    }
    
    /**
     * Places the given guess in the set of correct guesses at any and all index
     * values that it occurs in the current word, all while maintaining these
     * index values between the two {@code String}s.
     * 
     * @param guess The {@code String} to add to the set of correct guesses 
     *        based on its index occurrence in the current word.
     */
    private void insertCorrectGuess(String guess) {
        // TODO - Insert correct guesses into the correctGuesses String to 
        // fulfill the contract given by this method's Javadoc
    }
    
}
