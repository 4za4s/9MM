package Display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLayeredPane;

import Board.Player;

/**n Display for how many pieces are left */
public class PieceCounter extends JLayeredPane {
    Color backgroundColor = Color.black;
    Color playerColor;
    Color emptyPieceColor = Color.lightGray;
    Color enemyColor;

    int backgroundWidth;
    int backgroundHeight;

    int pieceHeight;
    int pieceGap;



    Player player;

    public PieceCounter(Player player, Player enemy){
        this.player = player;
        playerColor = player.getColour();
        enemyColor = enemy.getColour();

    }


    @Override
    public void paintComponent(Graphics g){

        int piecesLeft =  player.getNumOfPiecesPlaced() - player.getNoOfPiecesLost();
        int piecesTaken = player.getNoOfPiecesLost();

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(100));

        //Background
        g2.setColor(backgroundColor);
        g2.fillRect(0,0,backgroundWidth, backgroundWidth * 6/10  + pieceHeight * 12 + pieceGap * 11); 

        //Draw player indicator
        g2.setColor(playerColor);
        g2.fillOval(backgroundWidth * 2/10, backgroundWidth * 2/10, backgroundWidth * 6/10, backgroundWidth * 6/10);


        //Display myPieces
        g2.setColor(playerColor.darker());
        for (int i = 9; i > piecesLeft; i--){
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * i,
                backgroundWidth * 8/10,
                pieceHeight
                );
        }



        //Display Missing pieces
        g2.setColor(emptyPieceColor);
        for (int i = player.maxPieces; i > 0; i--){
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * i,
                backgroundWidth * 8/10,
                pieceHeight
                );
        }

        // Display Taken pieces
        g2.setColor(enemyColor);
        for (int i = piecesTaken; i > 0; i--){
            System.out.println(piecesTaken);
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * i,
                backgroundWidth * 8/10,
                pieceHeight
                );
        }
        

    }


    public void resizeDisplay(int backgroundWidth, int backgroundHeight ) {
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;

        pieceHeight = backgroundWidth / 5;
        pieceGap = pieceHeight / 3;
        


    }

}

