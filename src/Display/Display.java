package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import Game.Game;
import Game.GameState;
import Menu.MainMenu;

public class Display extends JFrame{
    Game game;
    MainMenu mainMenu;
    GameState mode = GameState.PREGAME;
    Display display = this;
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
        setMinimumSize(new Dimension(600, 600));
        setMaximumSize(new Dimension(2000, 2000));


        resizing();
    }

    /* Enables resizing */
    private void resizing(){

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                System.out.println("Size Changing");
                getContentPane().removeAll();
                
                

                switch(mode){
                    case PREGAME:
                        MenuDisplay menuDisplay = new MenuDisplay(mainMenu, display);
                        menuDisplay.setPreferredSize(new Dimension(getHeight(), getWidth()));
                        // add(menuDisplay);
                        break;

                    case INGAME:
                        GameDisplay gameDisplay = new GameDisplay(game, display);
                        game.setGameDisplay(gameDisplay);
                        break;

                    case POSTGAME:
                        System.out.println("TODO!!!!!!");

                    default:
                        throw new IllegalArgumentException("Unknown mode was given: '" + mode +  "'");

                }

                revalidate();
                repaint();
            }
        });

         
        

    }

    public void displayGame(Game game){
        getContentPane().removeAll();
        this.game = game;
        mode = GameState.INGAME;
        GameDisplay gameDisplay = new GameDisplay(game, this);
        game.setGameDisplay(gameDisplay);
        repaint();
    }

    public void displayMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu; 
        getContentPane().removeAll();
        MenuDisplay menuDisplay = new MenuDisplay(mainMenu, this);
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
