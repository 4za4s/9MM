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

    /**
     * Sets the neighbours of this position
     * Neighbours are determined during game creation for easy access during gameplay
     * @param neighbours list of neighbours
     */
    public void setNeighbours(Position[] neighbours){
        this.neighbours = neighbours;
    }

    /**
     * Gets the neighbours of this position
     * @return list of Positions
     */
    public Position[] getNeighbours(){
        return neighbours;
    }

    /**
     * Gets the piece on this position
     * @return Piece on this position, null if empty
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * Sets the piece on this position
     * @param piece piece to set, can be null to make the position empty
     */
    public void setPiece(Piece piece){
        this.piece = piece;
        //updates the piece to let it know it is now on this position
        if (piece != null && piece.getPosition() != this) {
            piece.setPosition(this);
        }
    }
}
