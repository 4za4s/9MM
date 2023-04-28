package Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Class to represent a position on the board, holds a single piece or nothing and has a list of neighbours
 */
public class Position extends JButton implements ActionListener {
    private Piece piece;
    private Position[] neighbours;

    //constructor if a piece should already exist here
    public Position(int[] pos, Piece piece){
        this.piece = piece;
    }

    //constructor if no piece is here
    public Position() {
        this.piece = null;
    }

    public void setNeighbours(Position[] neighbours){
        this.neighbours = neighbours;
    }

    public Position[] getNeighbours(){
        return neighbours;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked");
    }
}
