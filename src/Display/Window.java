package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import Game.Game;

/**
 * The computer window that the game runs in (all new games will share this
 * window)
 */
public class Window extends JFrame {
    private Display currentDisplay; // what is currently being display - eg a game
    private Window window = this; // this
    private final int MINSIZE = 200; // minimum size the board can display as
    private final int MAXSIZE = 2000; // minimum size the board can display as
    private Dimension size; // how big this window is
    private final Color BACKGROUNDCOLOR = new Color(244, 224, 190); // Background colour of this window

    /**
     * Class constructor
     */
    public Window() {
        super("Nine Man's Morris");
        // Frame settings
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window
        getContentPane().setBackground(BACKGROUNDCOLOR);
        setWindowSize();
        pack();
        setSize(size);
        setVisible(true);
        setLayout(null); // try setLayout(new GridLayout()); if bored
        setMinimumSize(new Dimension(MINSIZE, MINSIZE));
        setMaximumSize(new Dimension(MAXSIZE, MAXSIZE));

        displayMenu();

        resizing();
    }

    /**
     * Listerner to check for resizing. Enables
     * resizing the window to resize its contents
     */
    private void resizing() {

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                System.out.println("Size Changing");

                size = window.getContentPane().getSize();
                currentDisplay.resizeDisplay(size);

                revalidate();
                repaint();
            }
        });
    }

    /**
     * Display a game on the window
     */
    public void displayGame() {
        displayGame(new Game());
    }

    /**
     * Displays a game. Only the game will be displayed
     * @param game gaem to display
     */
    public void displayGame(Game game) {
        if (currentDisplay != null) {
            remove(currentDisplay);
        }

        GameDisplay gameDisplay = new GameDisplay(getHeight(), getWidth(), game, this);
        game.setGameDisplay(gameDisplay);
        currentDisplay = gameDisplay;
        size = window.getContentPane().getSize();
        add(gameDisplay);
        repaint();
        currentDisplay.resizeDisplay(size);
        game.startGame();
    }

    /**
     * Display the main menu on the window
     */
    public void displayMenu() {
        if (currentDisplay != null) {
            remove(currentDisplay);
        }
        getContentPane().removeAll();
        getContentPane().setBackground(BACKGROUNDCOLOR);
        MenuDisplay menuDisplay = new MenuDisplay(getHeight(), getWidth(), this);
        currentDisplay = menuDisplay;
        size = window.getContentPane().getSize();
        add(menuDisplay);
        repaint();
        currentDisplay.resizeDisplay(size);
    }

    /**
     * Set window size of game relative to display. Height and Width calculated
     * relative
     * to the screen so the game is roughly the same size on most screens
     */
    private void setWindowSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight = Math.max(MINSIZE, (int) size.getHeight() * 5 / 6);
        int frameWidth = Math.max(MINSIZE, frameHeight * 13 / 14);
        this.setLocation((int) size.getWidth() / 2 - frameWidth / 2, (int) size.getHeight() / 2 - frameHeight / 2);

        this.size = new Dimension(frameWidth, frameHeight);
    }
}
