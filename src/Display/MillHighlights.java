package Display;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


import javax.swing.JLayeredPane;

import Board.Mills;
import Board.Player;
import Board.Position;

/**
 * Highlights mills on the board
 */
public class MillHighlights extends JLayeredPane{
    private int millHighlightsWidth;
    private Mills mills;

    public MillHighlights(Mills mills){
        this.mills = mills;
    }

    @Override
    public void paintComponent(Graphics g ){

        Graphics2D g2 = (Graphics2D) g;

        ArrayList<Position[]> millArray = mills.getMills();

        int millStartXpos;
        int millStartYpos;

        int millEndXpos;
        int millEndYpos;

        //For every Mill in the mill array draw a line
        for (int i = 0; i < millArray.size(); i++ ){
            millStartXpos = millArray.get(i)[0].getX();

            millStartYpos = millArray.get(i)[0].getY();

            millEndXpos = millArray.get(i)[2].getX();
            millEndYpos = millArray.get(i)[2].getY();

            Player player = millArray.get(i)[0].getPiece().getOwner();
            Color colour = new Color(
                player.getColour().getRed(), 
                player.getColour().getGreen(),
                player.getColour().getBlue(),
                player.getColour().getAlpha()*2/5
            );
            g2.setColor(colour);

            g2.setStroke(new BasicStroke(millHighlightsWidth)); 


            //Make intesecting lines
            g2.drawLine(millStartXpos + millHighlightsWidth / 2,
                        millStartYpos + millHighlightsWidth /2,
                        millEndXpos + millHighlightsWidth /2,
                        millEndYpos + millHighlightsWidth /2);
        }
    }


    /**
     * Resizes the highlights
     * @param millHighlightsWidth what width to resize highlights to
     */
    public void resizeDisplay(int millHighlightsWidth){
        this.millHighlightsWidth = millHighlightsWidth;

    }

    
}

