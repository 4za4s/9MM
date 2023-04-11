package Display;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLayeredPane;


/* Creates a draweing of the background. This drawing has to be added to a panel to be viewed */
class Background extends JLayeredPane { //prev JPanel

    int boardPadding;
    int gap;
    int slotSize;

    public Background( int boardPadding, int gap, int slotSize) {

        this.boardPadding = boardPadding;
        this.gap = gap;
        this.slotSize = slotSize;

    }

    @Override
    //This is run by default
    public void paintComponent(Graphics g){

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












}