package Display;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLayeredPane;


/**
 *  Creates a drawing of the background. This drawing has to be added to a panel to be viewed 
 */
class Background extends JLayeredPane {
    private int gap; //how much space to put between the rings of the board
    private int slotSize; //how big each piece is one the board

    /**
     * Draws the background, called when the panel is drawn
     * Part of the JLayeredPane class
     */
    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;


        // // Add image background
        // Image img;
        // try {
        //     String url = "src/Assets/planks_birch.png";
        //     img = ImageIO.read(new File(url));
        //     g2.drawImage(img, 0,0,1300,1300, this);

        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace(); 
        // }

        // Set stoke size
        g2.setStroke(new BasicStroke(slotSize / 2)); //Double width because half gets cut off by the frame

        //Draw rectangles
        g2.drawRect(0,0,gap*6,gap*6); // outer rectangle

        g2.setStroke(new BasicStroke(slotSize / 4));
        g2.drawRect( gap, gap, gap*4, gap*4); //middle rectangle
        g2.drawRect(gap*2, gap*2, gap*2, gap*2); //inner rectangle

        //Make intesecting lines
        g2.drawLine(gap*3, 0, gap*3, gap*2 ); //top line
        g2.drawLine(gap*3, gap*4, gap*3, gap*6 ); //bottom line
        g2.drawLine(0, gap*3, gap*2, gap*3 ); //left line
        g2.drawLine(gap*4, gap*3, gap*6, gap*3 ); //right line  
    }


    public void resizeDisplay(int gap, int slotSize) {
        this.gap = gap;
        this.slotSize = slotSize;


    }
}