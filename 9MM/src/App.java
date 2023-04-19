import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import Board.Board;
import Board.BoardManager;
import Display.Display;

/**
 * This is the driver class for 9MM game
 */
public class App{
    //Set frame settings
    private static int frameWidth = 500; //width of 9MM gui
    private static int frameHeight = 550;  //height of 9MM gui

    public static void main(String[] args) {  

   
        JFrame frame = new JFrame("9 Mans Morris");


        getWindowSize();
        frame.setSize(frameWidth,frameHeight);

        // frame.setResizable(false); //prevent resizing 

        //Frame settings
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // //Create the board
        Board board = new Board();
        BoardManager boardManager = new BoardManager(board);
        board.setBoardManager(boardManager); //so it can report to its manager
        
        

        // Create and set up the content pane.
        Display displayPane = new Display(200,board, frame);
        board.setPanel(displayPane);
        board.setDesiplaysValidLocations(); //TODO: probably not the best way to have done this
        displayPane.createGameBoard();
        frame.setContentPane(displayPane);

        //Display the window
        frame.pack();
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setVisible(true);





        boardManager.startGame();



    }


    /**
     * Set window size of game relative to display
     */ //TODO: move this to a new class?
    private static void getWindowSize(){
        // getScreenSize() returns the size
        // of the screen in pixels
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // height will store the height of the screen
        frameHeight = (int)size.getHeight()*4/5;
        frameWidth = frameHeight*9/10; //10;
    } 
}
