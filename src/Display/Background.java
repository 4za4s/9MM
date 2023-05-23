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
    private int lineWidth; //width of lines

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
        g2.setStroke(new BasicStroke(lineWidth));
    
        //Draw rectangles
        g2.drawRect(lineWidth / 2,lineWidth / 2,gap*6,gap*6); // outer rectangle
        g2.drawRect(gap + lineWidth / 2, gap + lineWidth / 2, gap*4, gap*4); //middle rectangle
        g2.drawRect(gap*2 + lineWidth / 2, gap*2 + lineWidth / 2, gap*2, gap*2); //inner rectangle

        //Make intesecting lines
        g2.drawLine(gap*3 + lineWidth / 2, 0 + lineWidth / 2, gap*3 + lineWidth / 2, gap*2 +lineWidth / 2); //top line
        g2.drawLine(gap*3 + lineWidth / 2, gap*4 + lineWidth / 2, gap*3 + lineWidth / 2, gap*6 + lineWidth / 2); //bottom line
        g2.drawLine(lineWidth / 2, gap*3 + lineWidth / 2, gap*2 + lineWidth / 2, gap*3 + lineWidth / 2 ); //left line
        g2.drawLine(gap*4 + lineWidth / 2 , gap*3 + lineWidth / 2, gap*6 + lineWidth / 2, gap*3 + lineWidth / 2 ); //right line  
    }


    /**
     * Resizes the display to match given inputs
     * @param gap distance between concentric squares
     * @param lineWidth how thick to make lines
     */
    public void resizeDisplay(int gap, int lineWidth) {
        this.gap = gap;
        this.lineWidth = lineWidth;


    }
}