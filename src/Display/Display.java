package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Game.Game;
import Menu.MainMenu;

public class Display extends JFrame{
    private final int minSize = 700; //minimum size the board can display as 
    private Dimension size;
    private final Color backgroundColor = new Color(244,224,190);
    /**
     * Class constructor
     */
    public Display(){
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
        
    }

    public void displayGame(Game game){
        getContentPane().removeAll();
        GameDisplay gameDisplay = new GameDisplay(game, this);
        game.setGameDisplay(gameDisplay);
        repaint();
    }

    public void displayMenu(MainMenu menu){
        getContentPane().removeAll();
        MenuDisplay menuDisplay = new MenuDisplay(menu, this);
        menuDisplay.setPreferredSize(new Dimension(getHeight(), getWidth()));
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
