package Display;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;


/**
 *  Creates a drawing of the background. This drawing has to be added to a panel to be viewed 
 */
class Background extends JLayeredPane {

    private int boardPadding; //how much padding to add to each side of the board
    private int gap; //how much space to put between the rings of the board
    private int slotSize; //how big each piece is one the board

    /**
     * Class constructor
     */
    public Background( int boardPadding, int gap, int slotSize) {
        this.boardPadding = boardPadding;
        this.gap = gap;
        this.slotSize = slotSize;
    }

    /**
     * Draws the background, called when the panel is drawn
     * Part of the JLayeredPane class
     */
    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;


        //Add image background
        // Image img;
        // try {
        //     String url = "/home/linden/Documents/Uni/2023/Sem1/FIT3077/9MansMorris/project/src/Display/planks_birch.png";
        //     img = ImageIO.read(new File(url));
        //     g2.drawImage(img, 0,0,1300,1300, this);

        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace(); 
        // }

       


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