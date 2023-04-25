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

    public static void main(String[] args) {  

        BoardManager boardManager = new BoardManager();
        
        boardManager.startGame();



    }

}


