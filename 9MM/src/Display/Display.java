package Display;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import Board.BoardManager;
import Board.GameButton;
import Board.GameButtonClicked;
import Board.Piece;


public class Display extends JPanel{

    int boardPadding; //padding to each side of the board 
    int effectiveSize; //how mich room is left  in game (frame minus padding)
    int gap; // distance between concentric squares
    
    Frame frame;

    int frameWidth;
    int frameHeight;
    int slotSize = 50; // height/width of a button
    Dimension size; //so all board parts are created the same size


    private JLayeredPane layeredPaneSlots;
    private SelectionHighlights layeredPaneHighlights;
    private Background layeredPaneBackground;
    
    BoardManager manager; //the game board this is displaying
    ArrayList<GameButton> buttonArray = new ArrayList<GameButton>(); // array for storing the buttons

     
    Color defaultColour = Color.white;
   

    public Display(int boardPadding, BoardManager manager, Frame frame){
     

        this.frame = frame;
        this.boardPadding = boardPadding;
        this.manager = manager;
        this.frameWidth = frame.getWidth();
        this.frameHeight = frame.getHeight();

        //Work out values for the spacing
        
        int minSize = Math.min(frameWidth, frameHeight);

        effectiveSize = minSize - boardPadding * 2;
        gap = effectiveSize/6;


        //make button size depend on board size
        slotSize = minSize/20; //(Accessibility feature)


        //Create board
        setLayout(null); //try setLayout(new GridLayout()); if board


        //Create and set up the slots layered pane.
        layeredPaneSlots = new JLayeredPane();
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight)); //TODO: remove this later somehow
        setUpLayeredPaneSlots();
    
        //Create and set up the background layered pane.
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);

        //Add both layers to the frame
        add(layeredPaneSlots);
        add(layeredPaneBackground);

        size = layeredPaneSlots.getPreferredSize();
        
        layeredPaneSlots.setSize(size);
        layeredPaneBackground.setSize(size);

    }



    /* Creates the buttons to represent pieces/empty spaces */
    private void setUpLayeredPaneSlots(){


        int[][]buttonsLocations =   new int[][]{{0,0},{0,3},{0,6}, //{row,column}
                                                {1,1},{1,3},{1,5},
                                                {2,2},{2,3},{2,4},
                                                {3,0},{3,1},{3,2},{3,4},{3,5},{3,6},
                                                {4,2},{4,3},{4,4},
                                                {5,1},{5,3},{5,5},
                                                {6,0},{6,3},{6,6}

                            };

        // Loop through each button location and create a button there
        for (int i = 0; i < buttonsLocations.length; i++){
            //get row/column of button to be creates
            int row = buttonsLocations[i][0];
            int column = buttonsLocations[i][1];

            //Work out what the row/column correstponds to in terms of x/y
            int y = boardPadding - slotSize / 2 + gap * row; //y is row not column
            int x = boardPadding - slotSize / 2 + gap * column;

            //Make a corresponding button
            GameButton tempButtonVar = new GameButton(row,column);
            tempButtonVar.setBounds(x,y,slotSize,slotSize);
            tempButtonVar.setBackground(defaultColour);
            layeredPaneSlots.add(tempButtonVar); //what to add, layer

            //Make the button clickable
            new GameButtonClicked(tempButtonVar,manager);


            buttonArray.add(tempButtonVar);


        }

    }


    public void updatePiece(Piece piece){
        //sets old location to empty
        setLocation(piece.getPrevRow(), piece.getPrevColumn(), defaultColour);

        //override new location
        setLocation(piece.getRow(), piece.getColumn(), piece.getColour());
    }

    /* Sets a button to match the colour of a player (or empty colour) */
    private void setLocation(int row, int column, Color colour){
        //find button at location
        GameButton button = findButton(row,column);

        //set its colour
        button.setBackground(colour);
    }

    private GameButton findButton(int row, int column) {
        for (int i=0; i< buttonArray.size();i++){
            if (buttonArray.get(i).getRowPos() == row && buttonArray.get(i).getColumnPos() == column){
                return buttonArray.get(i);
            }

        }

        return null;
       // throw new Exception("Button not found at row:" + row + ", column:" + column);

    }

    /* Prevents user from selecting buttons in location that aren't on this is.
     * Locations on this list are NOT made selectable
     */
    public void deselectOtherButtons(ArrayList<int[]> boardLocations){

        boolean locFound = false;

        int[] buttonLocation = {0, 0};
        

        //Find locations not on the given list
        for (GameButton button : buttonArray){
            locFound = false;

            //Check if a button matches any position
            for(int[] boardLocation: boardLocations ){

                buttonLocation[0] = button.getRowPos();
                buttonLocation[1] = button.getColumnPos() ;

                if (Arrays.equals(buttonLocation,boardLocation)){

                    locFound = true;
                }
            }

            //Deselect button
            if (locFound == false){
                button.setEnabled(false);

            }
        }
    }

    /* Highlite an available location the selected piece can move 
     * Using buttons because it is easy.
     * 
     * TODO: redo whole rendering system?
    */

    public void displayAvailableLocation(ArrayList<int[]> availableLocations){ //eventually ArrayList<int[]> availableLocations -> as an input

        // //hardcoding this for testing
        // ArrayList<int[]>availableLocations = new ArrayList<int[]>();

        // int[] pos1 = {0,0};
        // int[] pos2 = {0,3};
        // int[] pos3 = {0,6};

        // availableLocations.add(pos1);
        // availableLocations.add(pos2);
        // availableLocations.add(pos3);

        //remove old highlightes
        if (layeredPaneHighlights != null){
            remove(layeredPaneHighlights); 

        }

        layeredPaneHighlights = new SelectionHighlights(boardPadding, gap, slotSize, availableLocations);

        add(layeredPaneHighlights);

        


        layeredPaneHighlights.setSize(size);

        // repaint();
        // revalidate();


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



 }




        // //Example of adding text
    // public void addShape() {
    //     // this.getGraphics();

        
    
    //     // Graphics2D g2 = (Graphics2D) g;

    //     // g2.setStroke(new BasicStroke(10));
    
    //     // g.drawString("qwe",20,20);
    // }

