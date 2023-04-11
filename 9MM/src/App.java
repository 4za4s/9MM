import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;


import javax.swing.JFrame;

import Board.Board;
import Board.BoardManager;
import Board.Piece;
import Board.Player;
import Display.Display;


public class App{
    //Set frame settings
    static int frameWidth = 500;
    static int frameHeight = 550;

    public static void main(String[] args) {  

   



        Piece[][] pieceArray = new Piece[6][6];
        Player player1 = new Player(Color.white);

        Piece table[] = {new Piece(0, 1, player1)};


        // System.out.println(pieceArray);
        System.out.println(pieceArray[0][0]);
        System.out.println("Please run this in debug mode and see if you get an error = " + table[0].getColumn());


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


        displayPane.setOpaque(false); //content panes must be opaque
        frame.setContentPane(displayPane);

        board.setDesiplaysValidLocations(); //TODO: probably not the best way to have done this


        // frame.setLayout(null);

        //Display the window
        frame.pack();
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setVisible(true);



    }





        // boardManager.startGame();




        // // testing moving pieces
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
       
    // }



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