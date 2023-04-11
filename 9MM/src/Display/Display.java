package Display;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import Board.Board;
import Board.GameButton;
import Board.Piece;
import Board.Player;


public class Display extends JPanel{

    int boardPadding; //padding to each side of the board 
    int effectiveSize; //how mich room is left  in game (frame minus padding)
    int gap; // distance between concentric squares
    
    Frame frame;

    int frameWidth;
    int frameHeight;
    int slotSize = 50; // height/width of a button
    Dimension size; //so all board parts are created the same size
    int[][] validLocations;


    private ButtonDisplay layeredPaneSlots;
    private SelectionHighlights layeredPaneHighlights;
    private Background layeredPaneBackground;
    
    Board board; //the game board this is displaying
    ArrayList<GameButton> buttonArray = new ArrayList<GameButton>(); // array for storing the buttons
    Piece[][] pieceArray = new Piece[7][7];

    Color defaultColour = Color.white;
   

    public Display(int boardPadding, Board board, Frame frame){
        this.frame = frame;
        this.boardPadding = boardPadding;
        this.board = board;
        this.frameWidth = frame.getWidth();
        this.frameHeight = frame.getHeight();

  

        
    }

    /* creates the game board. Not done at initialiseation because variables need to be set later */
    public void createGameBoard(){

  
        //Work out values for the spacing
        
        int minSize = Math.min(frameWidth, frameHeight);

        effectiveSize = minSize - boardPadding * 2;
        gap = effectiveSize/6;


        //make button size depend on board size
        slotSize = minSize/20; //(Accessibility feature)


        //Create board
        setLayout(null); //try setLayout(new GridLayout()); if bored

  
    
        //Create and set up the background layered pane.
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);

        //Add layer to the frame
   
        add(layeredPaneBackground);

        
        
        String piecePhases[] = {"unused"};
        Player[] unusedPlayer = {new Player(defaultColour)};
        updatePieces(pieceArray,unusedPlayer,piecePhases, "unusedString",false);

        

        layeredPaneBackground.setSize(size);

    }

    public void updatePieces(Piece[][] pieceArray, Player[] selectablePlayers,String piecePhases[], String  noPlayerString, boolean noPlayerSelectable){
        //Get the new locatiosn of all of the pieces
        this.pieceArray = pieceArray;

   

        layeredPaneSlots = new ButtonDisplay(boardPadding, gap, slotSize, pieceArray, validLocations, piecePhases, selectablePlayers, noPlayerString , noPlayerSelectable, this);
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight)); //TODO: update above input

        //Remove everything from the frame
        this.removeAll();
        //Add all of the new stuff back to the frame
        add(layeredPaneBackground); //TODO: not optimal to use removeAll because then I need to add this back every time. Was unable to just remove the buttons because too dumb
        add(layeredPaneSlots);

        size = layeredPaneSlots.getPreferredSize();

        layeredPaneSlots.setSize(size);

    
    }




    /* Highlight an available location the selected piece can move 
     * Using buttons because it is easy.
     * 
     * TODO: redo whole rendering system?
    */

    public void displayAvailableLocation(ArrayList<int[]> availableLocations){ //eventually ArrayList<int[]> availableLocations -> as an input


        layeredPaneHighlights = new SelectionHighlights(boardPadding, gap, slotSize, availableLocations);

        add(layeredPaneHighlights);

        


        layeredPaneHighlights.setSize(size);

        // repaint();
        // revalidate();


        }

        /* When a button on the display is clicked */
        public void buttonClicked(GameButton gameButton){
            board.buttonClicked(gameButton.getPiece());

        }

        // // frame.setLayout(new FlowLayout());
        // GameButton tempButtonVar = new GameButton(row,column);
        // tempButtonVar.setBounds(0,0,10,10);
        // tempButtonVar.setBackground(Color.pink);
        // frame.add(tempButtonVar);

        // frame.repaint();
        // // Set stoke size
        // g2.setStroke(new BasicStroke(slotSize / 4));

        // //Draw rectangles
        // g2.drawRect(boardPadding,boardPadding,gap*6,gap*6); // outer rectangle

        public void setValidLocations(int[][] validLocations){
            this.validLocations = validLocations;
        }

 }




        // //Example of adding text
    // public void addShape() {
    //     // this.getGraphics();

        
    
    //     // Graphics2D g2 = (Graphics2D) g;

    //     // g2.setStroke(new BasicStroke(10));
    
    //     // g.drawString("qwe",20,20);
    // }

