import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Board.Board;
import Board.BoardManager;
import Display.Display;


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

        // Create and set up the content pane.
        JComponent displayPane = new Display(100,boardManager, frameWidth, frameHeight);
        displayPane.setOpaque(false); //content panes must be opaque
        frame.setContentPane(displayPane);

        
        // frame.setLayout(null);
    
        

        //Display the window
        frame.pack();
        frame.setVisible(true);

                // //Create board visual
        // Display panel = new Display(frame,100,boardManager);

        // board.setPanel(panel);




        // frame.setLayout(new FlowLayout());

        // frame.add(panel);




        // boardManager.startGame();




        // panel.displayAvailableLocation(1,1);


        //testing moving pieces
        // Piece piece = new Piece(0,0, new Player(Color.pink));

        // piece.movePiece(6,6);

        // board.updatePiece(piece);

        // piece.movePiece(0,6);

        // board.updatePiece(piece);





        // //Fun resizing
        // frame.addComponentListener(new ComponentAdapter() {
        //     public void componentResized(ComponentEvent e) {
        //         System.out.println("Size Changed");
                
        //         frame.remove(panel);
             
        //         frame.add(new DrawBoard(frame,100,board));
        //         frame.revalidate();
        //         frame.repaint();
        //     }
        // });


        // frame.repaint(); //try this if rendering looks weird
       
    }

    /* Set window size relative ot display */

    private static void getWindowSize(){
        // getScreenSize() returns the size
        // of the screen in pixels
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // height will store the height of the screen
        frameHeight = (int)size.getHeight()*4/5;
        frameWidth = frameHeight*9/10; //10;
    } 
}


