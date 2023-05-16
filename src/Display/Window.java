package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import Game.Game;
import Game.GameState;

public class Window extends JFrame{
    GameState mode = GameState.PREGAME;
    AbstractDisplay currentDisplay;
    Window window = this;
    private final int minSize = 700; //minimum size the board can display as 
    private final int maxSize = 2000; //minimum size the board can display as 
    private Dimension size;
    private final Color backgroundColor = new Color(244,224,190);

    /**
     * Class constructor
     */
    public Window(){
        super("9 Mans Morris");
        //Frame settings
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window
        getContentPane().setBackground(backgroundColor);
        setWindowSize();
        pack();
        setSize(size);
        setVisible(true);
        setLayout(null); //try setLayout(new GridLayout()); if bored
        setMinimumSize(new Dimension(minSize, minSize));
        setMaximumSize(new Dimension(maxSize, maxSize));
        
        displayMenu();

        resizing();
    }

    /* Enables resizing */
    private void resizing(){

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                System.out.println("Size Changing");

                Dimension size = window.getContentPane().getSize();
                currentDisplay.resizeDisplay();

                revalidate();
                repaint();
            }
        });
    }

    public void displayGame(){
        getContentPane().removeAll();
        Game game = new Game();
        GameDisplay gameDisplay = new GameDisplay(getHeight(), getWidth(), game, this);
        game.setGameDisplay(gameDisplay);
        currentDisplay = gameDisplay;
        add(gameDisplay);
        repaint();
    }

    public void displayMenu(){
        getContentPane().removeAll();
        MenuDisplay menuDisplay = new MenuDisplay(getHeight(), getWidth(), this);
        currentDisplay = menuDisplay;
        add(menuDisplay);
        repaint();
    }

    /**
     * Set window size of game relative to display
     */
    private void setWindowSize(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Height and Width calculated relative to the screen so the 
        // game is roughly the same size on most screens
        int frameHeight = Math.max(minSize,(int)size.getHeight()*5/6);
        int frameWidth = Math.max(minSize,frameHeight*13/14);

        this.size = new Dimension(frameWidth, frameHeight);
    }
}
