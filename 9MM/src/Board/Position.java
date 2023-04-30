package Board;

import javax.swing.JButton;

/**
 * Represent a position on the board, holds a single piece or nothing and has a list of neighbours
 */
public class Position extends JButton {
    private Piece piece;
    private Position[] neighbours;

    /**
     * Class constructor, starting with a piece
     */
    public Position(Piece piece){
        this.piece = piece;
    }

    /**
     * Class constructor, empty position
     */
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
        if (piece != null && piece.getPosition() != this) {
            piece.setPosition(this);
        }
    }
}
