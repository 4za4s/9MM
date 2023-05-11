package Display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLayeredPane;

import Board.Player;

/**n Display for how many pieces are left */
public class PieceCounter extends JLayeredPane implements ResizableDisplay{
    Color backgroundColor = Color.black;
    Color playerColor;
    Color emptyPieceColor = Color.lightGray;


    float scale = 1;

    int backgroundWidth = 75;
    int backgroundHeight = 275;

    int pieceHeight = 15;
    int pieceGap = 5;

    int piecesLeft = 3;

    Player player;

    public PieceCounter(Player player){
        this.player = player;
        playerColor = player.getColour();

    }


    @Override
    public void paintComponent(Graphics g){

        piecesLeft =  player.getNumOfPiecesPlaced() - player.getNoOfPiecesLost();

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(100));

        //Background
        g2.setColor(backgroundColor);
        g2.fillRect(0,0,backgroundWidth,backgroundHeight); 

        //Draw player indicator
        g2.setColor(playerColor);
        g2.fillOval(backgroundWidth * 2/10, backgroundWidth * 2/10, backgroundWidth * 6/10, backgroundWidth * 6/10);


        //Display myPieces
        g2.setColor(playerColor.darker());
        for (int i = 9; i > 9 - piecesLeft; i--){
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * i,
                backgroundWidth * 8/10,
                pieceHeight
                );

        }

        //Display Missing pieces
        g2.setColor(emptyPieceColor);
        for (int i = 9 - piecesLeft; i > 0; i--){
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * i,
                backgroundWidth * 8/10,
                pieceHeight
                );

        }
        

       
    }


    @Override
    public void resizeDisplay(Dimension size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resizeDisplay'");
    }
    
}

