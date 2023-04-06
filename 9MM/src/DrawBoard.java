import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;




class DrawBoard extends JPanel {

    int boardPadding = 100; //padding to each side of the board 
    int effectiveSize; //how mich room is left  in game (frame minus padding)
    int gap; // distance between concentric squares
    int slotSize = 50; // height/width of a button
    

    
    JFrame frame; //the board window
    Board board; //the game board this is displaying
   

    public DrawBoard(JFrame frame, int boardPadding, Board board){
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


    // JFrame frame;

    @Override
    //This is run by default
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;


        // Set stoke size
        g2.setStroke(new BasicStroke(10));

    
        
        //Draw game squares

        //main info

        //vars for effiecnt calculation of square sizes
        int[] sizeValues;
        int thisOffset;
        int thisEffectiveSize;

        //Make rectangles

        //outer rectangle
        sizeValues = getRectValues(0);
        thisOffset =  sizeValues[0];
        thisEffectiveSize = sizeValues[1];
        g2.drawRect(thisOffset,thisOffset,thisEffectiveSize,thisEffectiveSize);

        //middle rectangle
        sizeValues = getRectValues(1);
        thisOffset =  sizeValues[0];
        thisEffectiveSize = sizeValues[1];
        g2.drawRect(thisOffset,thisOffset,thisEffectiveSize,thisEffectiveSize);

        //inner rectangle
        sizeValues = getRectValues(2);
        thisOffset =  sizeValues[0];
        thisEffectiveSize = sizeValues[1];
        g2.drawRect(thisOffset,thisOffset,thisEffectiveSize,thisEffectiveSize);



        //Make verticle lines
        int lineLen = getRectValues(0)[1] / 3;
    


        int topOffset = getRectValues(0)[0]; //top offset
        int sideOffset = getRectValues(0)[0] + getRectValues(0)[1] / 2 ; // side offset
        
        //Top line
        g2.drawLine(sideOffset, topOffset, sideOffset,topOffset+ lineLen);

        //Bottom line
        //+3 because it pokes out otherwise
        g2.drawLine(sideOffset, topOffset + lineLen * 2 + 3, sideOffset,topOffset+ lineLen * 3); 
 

        //Left line
        g2.drawLine(topOffset + lineLen * 2 + 3,sideOffset,topOffset+ lineLen * 3, sideOffset); 


        //Right line
        g2.drawLine(topOffset,sideOffset,topOffset+ lineLen , sideOffset);

    }

    // function for calculating square locations. Later 0 is top, 1 middle, 2 bottom
    //returns offset for square, length of square
    private int[] getRectValues(int layer){
        int[] array = {boardPadding + gap*(layer), effectiveSize - gap*(layer*2)};
        return array;
        

    }

    
    // //Example of adding text
    // public void addShape() {
    //     // this.getGraphics();

        
    
    //     // Graphics2D g2 = (Graphics2D) g;

    //     // g2.setStroke(new BasicStroke(10));
    
    //     // g.drawString("qwe",20,20);
    // }



    /* palces  a lot of different buttons. Thgitere is not much consistance is the location method. Should be fixed later */
    //^ Infact I think the function can be replaced with a fancy while loop
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

            //Add the button to the board
            frame.add(tempButtonVar);

            //Make the button clickable
            new GameButtonClicked(tempButtonVar,board);

        }



        




    }


}