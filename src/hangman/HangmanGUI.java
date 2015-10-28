package hangman;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * The {@code HangmanGUI} class contains a user interface with a playable 
 * version of "Hangman."
 *
 * @author Oliver Abdulrahim
 */
public final class HangmanGUI
    extends JFrame
{
    
    /**
     * Recursively returns all components within a given container, including
     * any children that are also {@code Container}s.
     *
     * @param container The component whose components to retrieve.
     * @return All components within a given container.
     */
    public static List<Component> getAllComponents(Container container) {
        Component[] components = container.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component c : components) {
            compList.add(c);
            if (c instanceof Container) {
                compList.addAll(getAllComponents((Container) c));
            }
        }
        return compList;
    }

    /**
     * Displays a {@code JOptionPane} confirmation dialog using the given
     * arguments.
     *
     * @param message The message to display on the pane.
     * @param title The title of the pane.
     * @return The outcome of the user input.
     */
    private static int showConfirmPane(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title,
                JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Displays a {@code JOptionPane} information window using the given
     * arguments.
     *
     * @param message The message to display on the pane.
     * @param title The title of the pane.
     */
    private static void showMessagePane(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a {@code JOptionPane} information window using the given
     * arguments.
     *
     * @param message The message to display on the pane.
     * @param title The title of the pane.
     */
    private static void showErrorPane(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Default behavior for invalid input is to emit a system beep.
     */
    private static void invalidInputReceived() {
        Toolkit.getDefaultToolkit().beep();
    }
    
    /**
     * The main method for this package. Creates and displays a
     * {@code HangmanGUI} form.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        // Sets the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HangmanGUI.class.getName())
                    .log(Level.SEVERE,
                            "Error with look and feel settings. "
                          + "Check if they are installed correctly",
                            ex);
        }
        SwingUtilities.invokeLater(() -> {
            // Add any additional code to be executed by the main method within
            // this block.
            HangmanGUI gui = new HangmanGUI();
            gui.setVisible(true);
        });
    }

// Game variables
    
    /**
     * Stores the game manager for this user interface.
     */
    private Hangman game;
    
    /**
     * Stores the amount of games that this user interface has played.
     */
    private int gamesPlayed;
    
    /**
     * Stores the amount of games that this user interface has won.
     */
    private int gamesWon;
    
// GUI variables    
    
    /**
     * Acts as a container for the label that displays the image state of the 
     * game.
     * 
     * @see #imageLabel Child of this {@code Container}.
     */
    private JPanel imagePanel;
    
    /**
     * Displays the image that displays the state of the game.
     * 
     * @see #imagePanel Parent of this {@code Component}.
     */
    private JLabel imageLabel;
    
    /**
     * Acts as a container for the label that displays the word that is being
     * guessed.
     * 
     * @see #currentWordLabel Child of this {@code Container}.
     */
    private JPanel currentWordPanel;
    
    /**
     * Stores, in formatted text, the word that is being guessed.
     * 
     * @see #currentWordPanel Parent of this {@code Component}.
     */
    private JLabel currentWordLabel;
    
    /**
     * Contains the buttons and text fields that display statistics about 
     * current and past games.
     * 
     * @see #guessedLabel Child of this {@code Container}.
     * @see #guessedField Child of this {@code Container}.
     * @see #guessesLeftLabel Child of this {@code Container}.
     * @see #guessesLeftField Child of this {@code Container}.
     * @see #winRateLabel Child of this {@code Container}.
     * @see #winRateField Child of this {@code Container}.
     * @see #hintButton Child of this {@code Container}.
     * @see #newWordButton Child of this {@code Container}.
     * @see #giveUpButton Child of this {@code Container}.
     */
    private JPanel gameOperationsPanel;
    
    /**
     * Stores, in formatted text, the set of already guessed characters.
     * 
     * @see #gameOperationsPanel Parent of this {@code Component}.
     */
    private JLabel guessedLabel;
    
    /**
     * Stores the number of incorrect guesses remaining before the end of the
     * game.
     * 
     * @see #gameOperationsPanel Parent of this {@code Component}.
     */
    private JLabel guessesLeftLabel;
    
    /**
     * Stores the win rate of the game, or in other words, the amount of games
     * won ({@link #gamesWon}) divided by the amount of games played 
     * ({@link #gamesPlayed}).
     * 
     * @see #gameOperationsPanel Parent of this {@code Component}.
     */
    private JLabel winRateLabel;

    /**
     * Resets the current word to a new, random one when pressed, constituting a
     * loss if and only if a guess has already been made.
     * 
     * @see #gameOperationsPanel Parent of this {@code Component}.
     */
    private JButton newWordButton;
    
    /**
     * Shows the current word when pressed, constituting a loss.
     * 
     * @see #gameOperationsPanel Parent of this {@code Component}.
     */
    private JButton giveUpButton;
    
    /**
     * Contains the game options menu.
     * 
     * @see #easyRadioButton Child of this {@code Container}.
     * @see #mediumRadioButton Child of this {@code Container}.
     * @see #hardRadioButton Child of this {@code Container}.
     */
    private JPanel optionsPanel;
    
    /**
     * Provides for a grouping of the easy ({@link #easyRadioButton}), medium 
     * ({@link #mediumRadioButton}), and hard ({@link #hardRadioButton}) radio
     * buttons so that only one may be selected at a given time.
     */
    private ButtonGroup difficultyButtonGroup;
    
    /**
     * Selection of this button indicates that "easy" difficulty is desired.
     * 
     * @see #optionsPanel Parent of this {@code Component}.
     */
    private JRadioButton easyRadioButton;
    
    /**
     * Selection of this button indicates that "medium" difficulty is desired.
     * 
     * @see #optionsPanel Parent of this {@code Component}.
     */
    private JRadioButton mediumRadioButton;
    
    /**
     * Selection of this button indicates that "hard" difficulty is desired.
     * 
     * @see #optionsPanel Parent of this {@code Component}.
     */
    private JRadioButton hardRadioButton;
    
    /**
     * Stores a soft-keyboard of buttons with alphabetic character text.
     * 
     * @see #KEYBOARD Provides for the text of the buttons attached to this 
     *      {@code JPanel}.
     */
    private JPanel keyboardPanel;
    
    /**
     * Stores the text of the buttons attached to the keyboard {@code JPanel}.
     *
     * @see #keyboardPanel The container of buttons with the text of this
     *      {@code String}.
     */
    private static final String KEYBOARD = "QWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * Creates new, default {@code Hangman_GUI} form.
     */
    public HangmanGUI() {
        super("Hangman");
        initComponents();
    }

    /**
     * Called from within the constructor to initialize the form.
     */
    private void initComponents() {
        imagePanel = new JPanel();
        imageLabel = new JLabel();
        currentWordPanel = new JPanel();
        currentWordLabel = new JLabel();
        gameOperationsPanel = new JPanel();
        guessedLabel = new JLabel();
        guessesLeftLabel = new JLabel();
        winRateLabel = new JLabel();
        newWordButton = new JButton();
        giveUpButton = new JButton();
        keyboardPanel = new JPanel();
        
    // keyboardPanel setup
        
        keyboardPanel.setBorder(BorderFactory.createTitledBorder("Keyboard"));
        keyboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints;

        // Rows and columns should be zero-offset
        final int rows = 2, columns = 9;
        final int padding = 35;
        final Insets leadingInset = new Insets(0, -padding, 0, 0);

        // The rows of the keyboard have different amount of "keys" and are
        // center-padded. Add this padding to every row after the first. Also 
        // add y to x for every row additional offset.
        int keyboardIndex = 0;
        for (int y = 0; y <= rows; y++) {
            for (int x = y; x <= columns && keyboardIndex < KEYBOARD.length(); x++) {
                constraints = new GridBagConstraints();
                constraints.gridx = x;
                constraints.gridy = y;
                if (y > 0) {
                    constraints.insets = leadingInset;
                }
                String text = KEYBOARD.substring(keyboardIndex, ++keyboardIndex);
                keyboardPanel.add(buildButton(text), constraints);
            }
        }
        
        /* Hint:
         * Layout for gameOperationsPanel could look something like the 
         * following:
         *        ╭─────────────────────╮
         *        │┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈│
         *        │┈┈ ■■■■■■■■■■■■■■■ ┈┈│
         *        │┈┈ ■■■■■■■■■■■■■■■ ┈┈│
         *        │┈┈ ■■■■■■■■■■■■■■■ ┈┈│
         *        │┈┈ ■■■■■■■■■■■■■■■ ┈┈│
         *        │┈┈ ■■■■■■■■■■■■■■■ ┈┈│
         *        │┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈│
         *        ╰─────────────────────╯
         *   Character/Item     Representation
         *     - solid line     panel edges
         *     - ┈              padding
         *     - ■              component
         * 
         * I would use a BorderLayout, but you can use a GridBagLayout if you
         * want more control over the result.
         */
        
        // TODO - Add your component setup code here.
        
        // Don't forget to add the panels you set up to this object.
        add(keyboardPanel);
        
        // Some housekeeping to make everything visible.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        pack();
        
        resetGame();
    }
    
    /**
     * Builder for keyboard buttons. The buttons returned by this method are
     * disabled, use the "Consolas" font (12pt), have an {@code ActionListener} 
     * for parsing guesses, and have the given text.
     *
     * @param text The text of the button.
     * @return A button with the given text.
     */
    private JButton buildButton(String text) {
        final Font consolas = new Font("Consolas", 0, 12);
        JButton button = new JButton(text);
        button.setFont(consolas);
        button.setEnabled(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                parseGuess(evt);
            }
        });
        return button;
    }

    /**
     * Serves as a common method for parsing and handling the value of an input
     * event.
     *
     * <p> This method assumes that the given {@code ActionEvent} originated
     * from an {@code AbstractButton} instance contained within this object. It
     * attempts to make a guess based on the text contained by the button source
     * of the event.
     *
     * @param evt The input event at which the guess occurred.
     * @see #makeMove(String) The next method in the chain of events that occur
     *      when a guess is received by the GUI.
     */
    private void parseGuess(ActionEvent evt) {
        AbstractButton button = (AbstractButton) evt.getSource();
        String guess = button.getText();
        makeMove(guess);
        button.setEnabled(false);
    }
    
    /**
     * Resets the game to its default state.
     */
    private void resetGame() {
        game = new Hangman();
        gamesPlayed = 0;
        gamesWon = 0;
        setStateOfAll(true);
    }
    
    /**
     * Enables or disables all components contained within this object.
     *
     * @param state The state to set every {@code Component} contained within 
     *        this object to.
     */
    private void setStateOfAll(boolean state) {
        List<Component> components = getAllComponents(this);
        for (Component c : components) {
            c.setEnabled(state);
        }
    }
    
    /**
     * Finds the {@code JButton} in the {@code keyboardPanel} that represents
     * the given character parameter and disables it.
     *
     * @param guess The {@code String} guess representing the button to be
     *              disabled.
     */
    private void disableButton(String guess) {
        for (int i = 0; i < keyboardPanel.getComponentCount(); i++) {
            AbstractButton button = (AbstractButton) keyboardPanel.getComponent(i);
            // TODO - Add your disabling code here
        }
    }

    /**
     * Attempts to make a move on the game board. This method updates the game
     * board appropriately depending on the validity of the guess.
     *
     * @param guess The character to attempt to guess.
     */
    private void makeMove(String guess) {
        // TODO - Add logic that delegates the given move to this instance's
        // Hangman game object
    }

    /**
     * Returns the currently selected difficulty.
     *
     * @return The currently selected difficulty.
     */
    private int getUserSelectedDifficulty() {
        // Hint:
        // easyRadioButton.isSelected();
        return Hangman.HARD_DIFFICULTY;
    }

    /**
     * Updates the current label which displays the current word to accurately
     * reflect the state of the game.
     */
    private void updateCurrentLabel() {
        // TODO - Add code that updates the JLabel that displays the word that 
        // is being guessed for
    }

    /**
     * Updates the image to reflect the current state of the game.
     */
    private void updateImages() {
        // TODO - Add code that updates the images based on the amount of 
        // incorrect guesses left.
    }

    /**
     * Updates the statistics display, including the set of already guessed 
     * characters, the amount of guesses left, and the win rate.
     */
    private void updateStatistics() {
        // TODO - Add code that updates the components contained within the 
        // gameOperationsPanel
    }

    /**
     * Checks if the game is in a win or loss state.
     * 
     * @see #gameEnded() Called by this method if the game has ended.
     */
    private void checkGameState() {
        // TODO - Add code that checks if the game has been won or lost
    }

    /**
     * Ensures the GUI is kept properly updated at the end of a game. Input is
     * blocked until the game is reset.
     */
    private void gameEnded() {
        // TODO - Add code that disables everything in the GUI at the end of the
        // game
    }
    
}
