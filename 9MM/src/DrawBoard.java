import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
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

    /* gets the length of a sqare for a layer */
    private int getSquareLen(int layer){
        return effectiveSize - gap*(layer*2);
    }

    /* gets the offset of a sqare for a layer */
    private int getSquareOffset(int layer){
        return effectiveSize - gap*(layer*2);
    }

    
    // //Example of adding text
    // public void addShape() {
    //     // this.getGraphics();

        
    
    //     // Graphics2D g2 = (Graphics2D) g;

    //     // g2.setStroke(new BasicStroke(10));
    
    //     // g.drawString("qwe",20,20);
    // }



    /* palces  a lot of different buttons. There is not much consistance is the location method. Should be fixed later */
    //^ Infact I think the function can be replaced with a fancy while loop
    private void setUpBoardSlots(){

        int[]buttonsLocations = {};
        




        int slotSizeOffset = slotSize / 2; //to account for slot being plcaes not centered on the point

        //var names b[row][column]
        //0th left
        GameButton b00 = new GameButton(0,0);//creating instance of JButton  
        b00.setBounds(boardPadding - slotSizeOffset, boardPadding - slotSizeOffset,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b00);

        //0th row middle
        GameButton b03= new GameButton(0,3);//creating instance of JButton  
        b03.setBounds(boardPadding + getRectValues(0)[1] / 2 - slotSizeOffset, boardPadding - slotSizeOffset ,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b03);

        //0th row right
        GameButton b06 = new GameButton(0,6);//creating instance of JButton  
        b06.setBounds(boardPadding + getRectValues(0)[1] - slotSize / 2,boardPadding - slotSize / 2,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b06);

        //1st row left
        GameButton b11 = new GameButton(1,1);//creating instance of JButton  
        b11.setBounds(boardPadding - slotSize / 2 + gap ,boardPadding - slotSize / 2 + gap,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b11);


        //make the rest of hte buttons GameButton later

        //1st row mid
        GameButton b13 = new GameButton(1,3);//creating instance of JButton  
        b13.setBounds(boardPadding - slotSize / 2 + getRectValues(0)[1]/2 ,boardPadding - slotSize / 2 + gap,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b13);

        //1st row right
        GameButton b15 = new GameButton(1,5);//creating instance of JButton  
        b15.setBounds(boardPadding - slotSize / 2 - gap + getRectValues(0)[1],boardPadding - slotSize / 2 + gap,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b15);

        // 2nd row 
        GameButton b22 = new GameButton(2,2);//creating instance of JButton  
        b22.setBounds(boardPadding + getSquareOffset(2) - slotSizeOffset,boardPadding + getSquareOffset(2) - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b22);

        //2nd row mid
        GameButton b23 = new GameButton(2,3);//creating instance of JButton  
        b23.setBounds(boardPadding + getSquareOffset(2) + getSquareLen(2)/2 - slotSizeOffset, boardPadding + getSquareOffset(2) - slotSizeOffset,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b23);

        //2nd row right
        GameButton b24 = new GameButton(2,4);//creating instance of JButton  
        b24.setBounds(boardPadding + getSquareOffset(2) + getSquareLen(2) - slotSizeOffset, boardPadding + getSquareOffset(2) - slotSizeOffset,slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b24);

        //3rd row left
        GameButton b30 = new GameButton(3,0);//creating instance of JButton  
        b30.setBounds(boardPadding -  slotSizeOffset, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b30);

        //3rd row left
        GameButton b31 = new GameButton(3,1);//creating instance of JButton  
        b31.setBounds(boardPadding -  slotSizeOffset + gap, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b31);

        //3rd row right
        GameButton b32 = new GameButton(3,2);//creating instance of JButton  
        b32.setBounds(boardPadding -  slotSizeOffset + gap*2, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b32);

        //3rd row right -> left
        GameButton b34 = new GameButton(3,4);//creating instance of JButton  
        b34.setBounds(boardPadding -  slotSizeOffset + gap*4, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b34);

        //3rd row right -> mid
        GameButton b35 = new GameButton(3,5);//creating instance of JButton  
        b35.setBounds(boardPadding -  slotSizeOffset + gap*5, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b35);


        //3rd row right -> right
        GameButton b36 = new GameButton(3,6);//creating instance of JButton  
        b36.setBounds(boardPadding -  slotSizeOffset + gap*6, boardPadding + getSquareLen(0)/2 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b36);

        //4th row left
        GameButton b42 = new GameButton(4,2);//creating instance of JButton  
        b42.setBounds(boardPadding -  slotSizeOffset + gap * 2, boardPadding + gap * 4 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b42);

        //4th row mid
        GameButton b43 = new GameButton(4,3);//creating instance of JButton  
        b43.setBounds(boardPadding -  slotSizeOffset + gap * 3, boardPadding + gap * 4 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b43);
         
    
        //4th row right
        GameButton b44 = new GameButton(4,4);//creating instance of JButton  
        b44.setBounds(boardPadding -  slotSizeOffset + gap * 4, boardPadding + gap * 4 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b44);

        //5th row left
        GameButton b51 = new GameButton(5,1);//creating instance of JButton  
        b51.setBounds(boardPadding -  slotSizeOffset + gap * 1, boardPadding + gap * 5 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b51);

        //5th row mid
        GameButton b53 = new GameButton(5,3);//creating instance of JButton  
        b53.setBounds(boardPadding -  slotSizeOffset + gap * 3, boardPadding + gap * 5 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b53);

        //5th row right
        GameButton b55 = new GameButton(5,5);//creating instance of JButton  
        b55.setBounds(boardPadding -  slotSizeOffset + gap * 5, boardPadding + gap * 5 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b55);

        //6th row left
        GameButton b60 = new GameButton(6,0);//creating instance of JButton  
        b60.setBounds(boardPadding -  slotSizeOffset + gap * 0, boardPadding + gap * 6 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b60);

        //6th row mid
        GameButton b63 = new GameButton(6,3);//creating instance of JButton  
        b63.setBounds(boardPadding -  slotSizeOffset + gap * 3, boardPadding + gap * 6 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b63);

        //6th row right
        GameButton b66 = new GameButton(6,6);//creating instance of JButton  
        b66.setBounds(boardPadding -  slotSizeOffset + gap * 6, boardPadding + gap * 6 - slotSizeOffset, slotSize, slotSize);//x axis, y axis, width, height 
        frame.add(b66);
          
        //Make clicking the buttons notify the board
        GameButtonClicked a = new GameButtonClicked(b00,board);
        GameButtonClicked b = new GameButtonClicked(b03,board);
        GameButtonClicked c = new GameButtonClicked(b06,board);
        GameButtonClicked d = new GameButtonClicked(b11,board);
        GameButtonClicked e = new GameButtonClicked(b13,board);
        GameButtonClicked f = new GameButtonClicked(b15,board);
        //TODO: rest later
        


        




    }


}