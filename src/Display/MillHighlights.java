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

public class MillHighlights extends JLayeredPane{
   

    private Player player1;
    private Color player1Color; 

    private Color player2Color; 

    private int millHighlightsWidth;

    private Mills mills;


    public MillHighlights(Player player1, Player player2, Mills mills){
        this.player1 = player1;
        this.mills = mills;
        
        player1Color = new Color(
            player1.getColour().getRed(), 
            player1.getColour().getGreen(),
            player1.getColour().getBlue(),
            player1.getColour().getAlpha()*2/5
        );

        player2Color = new Color(
            player2.getColour().getRed(), 
            player2.getColour().getGreen(),
            player2.getColour().getBlue(),
            player2.getColour().getAlpha()*2/5
        );


    }


     /**
     * Draws mill highlights
     */
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

            if (millArray.get(i)[1].getPiece().getOwner() == player1){
                g2.setColor(player1Color);
            } else {
                g2.setColor(player2Color);
            }


            g2.setStroke(new BasicStroke(millHighlightsWidth)); 


            //Make intesecting lines
            g2.drawLine(millStartXpos + millHighlightsWidth / 2,
                        millStartYpos + millHighlightsWidth /2,
                        millEndXpos + millHighlightsWidth /2,
                        millEndYpos + millHighlightsWidth /2);
            

        }


    }


    public void resizeDisplay(int millHighlightsWidth){
        this.millHighlightsWidth = millHighlightsWidth;

    }

    
}

