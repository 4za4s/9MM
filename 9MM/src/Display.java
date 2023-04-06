
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;




class Display extends JPanel {

    int boardPadding = 100; //padding to each side of the board 
    int effectiveSize; //how mich room is left  in game (frame minus padding)
    int gap; // distance between concentric squares
    int slotSize = 50; // height/width of a button
    

    
    JFrame frame; //the board window
    Board board; //the game board this is displaying
    ArrayList<GameButton> buttonArray = new ArrayList<GameButton>(); // array for storing the buttons

     
    Color defaultColour = Color.white;
   

    public Display(JFrame frame, int boardPadding, Board board){
        this.frame = frame;
        this.boardPadding = boardPadding;
        this.board = board;

   

        //work out some values for the spacing
        int minSize = Math.min(frame.getWidth(),frame.getHeight()); // Smallest of height and width

        effectiveSize = minSize - boardPadding * 2;
        gap = effectiveSize/6;


        //make button size depend on board size
        slotSize = minSize/20; //(Accessibility feature)


        setUpBoardSlots(); 
    }

    @Override
    //This is run by default
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;


        // Set stoke size
        g2.setStroke(new BasicStroke(slotSize / 4));

        //Draw rectangles
        g2.drawRect(boardPadding,boardPadding,gap*6,gap*6); // outer rectangle
        g2.drawRect(boardPadding + gap, boardPadding + gap, gap*4, gap*4); //middle rectangle
        g2.drawRect(boardPadding + gap*2, boardPadding + gap*2, gap*2, gap*2); //inner rectangle

        //Make intesecting lines
        g2.drawLine(boardPadding + gap*3, boardPadding, boardPadding + gap*3, boardPadding + gap*2 ); //top line
        g2.drawLine(boardPadding + gap*3, boardPadding + gap*4, boardPadding + gap*3, boardPadding + gap*6 ); //bottom line
        g2.drawLine(boardPadding, boardPadding + gap*3, boardPadding + gap*2, boardPadding + gap*3 ); //left line
        g2.drawLine(boardPadding + gap*4, boardPadding + gap*3, boardPadding + gap*6, boardPadding + gap*3 ); //right line


    }


    /* Creates the buttons to represent pieces/empty spaces */
    private void setUpBoardSlots(){


        int[][]buttonsLocations = new int[][]{{0,0},{0,3},{0,6}, //{row,column}
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
            int x = boardPadding - slotSize / 2 + gap * row;
            int y = boardPadding - slotSize / 2 + gap * column;

            //Make a corresponding button
            GameButton tempButtonVar = new GameButton(row,column);
            tempButtonVar.setBounds(x,y,slotSize,slotSize);
            tempButtonVar.setBackground(defaultColour);
            frame.add(tempButtonVar);

            //Make the button clickable
            new GameButtonClicked(tempButtonVar,board);


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


        // //Example of adding text
    // public void addShape() {
    //     // this.getGraphics();

        
    
    //     // Graphics2D g2 = (Graphics2D) g;

    //     // g2.setStroke(new BasicStroke(10));
    
    //     // g.drawString("qwe",20,20);
    // }


}