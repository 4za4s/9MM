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
    Player enemy;

    public PieceCounter(Player player, Player enemy){
        this.player = player;
        this.enemy = enemy;
        playerColor = player.getColour();
        enemyColor = enemy.getColour();

    }

    /**
     * Finds the colour that the slot at position i in the piece counter should be
     * @param i
     * @return
     */
    private Color getSlotColor(int i){
        //Player's piece
        if (i > player.getNumOfPiecesPlaced()) {
            return playerColor.darker();
    
        //Empty slot
        } else if (i > enemy.getNoOfPiecesLost()){
            return emptyPieceColor;
        
        //Enemy Piece
        } else {
            return enemyColor.darker();
        }

    }


    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(100));

        //Background
        g2.setColor(backgroundColor);
        g2.fillRect(0,0,backgroundWidth, backgroundWidth + pieceHeight * player.maxPieces + pieceGap * (player.maxPieces + 1)); 

        //Draw player indicator
        g2.setColor(playerColor);
        g2.fillOval(backgroundWidth * 2/10, backgroundWidth * 2/10, backgroundWidth * 6/10, backgroundWidth * 6/10);


        //Display pieces
        for (int i = player.maxPieces; i > 0; i--){
            g2.setColor(getSlotColor(i));
            g2.fillRect(
                backgroundWidth * 1/10,
                backgroundWidth + (pieceGap + pieceHeight) * (i -1),
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

