import java.awt.Dimension;
import java.awt.Toolkit;
// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;


import javax.swing.JFrame;

import Board.Board;
import Board.BoardManager;
import Display.Display;

/**
 * This is the driver class for 9MM game
 */
public class App{
    //Set frame settings
    static int frameWidth = 500;
    static int frameHeight = 550;

    public static void main(String[] args) {  

   
        JFrame frame = new JFrame("9 Mans Morris");
        getWindowSize();
        frame.setSize(frameWidth, frameHeight);

        // frame.setResizable(false); //prevent resizing 

        //Frame settings
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // //Create the board
        Board board = new Board();
        BoardManager boardManager = new BoardManager(board);
        board.setBoardManager(boardManager); //so it can report to its manager
        
        

        // Create and set up the content pane.
        Display displayPane = new Display(100,board, frame);
        board.setPanel(displayPane);
        board.setDesiplaysValidLocations(); //TODO: probably not the best way to have done this
        displayPane.createGameBoard();


        displayPane.setOpaque(false); //content panes must be opaque - TODO: maybe this should be true?
        frame.setContentPane(displayPane);

        


        // frame.setLayout(null);

        //Display the window
        frame.pack();
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setVisible(true);





        boardManager.startGame();



    }



    /* Set window size relative to display */
    private static void getWindowSize(){
        // getScreenSize() returns the size
        // of the screen in pixels
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // height will store the height of the screen
        frameHeight = (int)size.getHeight()*4/5;
        frameWidth = frameHeight*9/10; //10;
    } 
}


//TODO: creaete a 3d array to store the buttons