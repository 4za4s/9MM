package Board;

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * Represent a position on the board, holds a single piece or nothing and has a list of neighbours
 */
public class Position extends JButton {
    private Piece piece;
    private Position northNeighbour;
    private Position southNeighbour;
    private Position eastNeighbour;
    private Position westNeighbour;

    int row;
    int column; 

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

    public void setNorthNeighbour(Position northNeighbour){
        this.northNeighbour = northNeighbour;
    }

    public void setSouthNeighbour(Position southNeighbour){
        this.southNeighbour = southNeighbour;
    }

    public void setEastNeighbour(Position eastNeighbour){
        this.eastNeighbour = eastNeighbour;
    }

    public void setWestNeighbour(Position westNeighbour){
        this.westNeighbour = westNeighbour;
    }

    public Position getNorthNeighbour(){
       return northNeighbour;
    }

    public Position getSouthNeighbour(){
        return southNeighbour;
    }

    public Position getEastNeighbour(){
        return eastNeighbour;
    }


    public Position getWestNeighbour(){
        return westNeighbour;
    }

    /**
     * Gets the neighbours of this position
     * @return list of Positions
     */
    public ArrayList<Position> getEmptyNeighbours(){
        ArrayList<Position> emptyNeighbours = new ArrayList<Position>();

        //Add the empty neighbours
        if (northNeighbour != null && northNeighbour.getPiece() == null){
            emptyNeighbours.add(northNeighbour);
        }
        if (southNeighbour != null && southNeighbour.getPiece() == null){
            emptyNeighbours.add(southNeighbour);
        }
        if (eastNeighbour != null && eastNeighbour.getPiece() == null){
            emptyNeighbours.add(eastNeighbour);
        }
        if (westNeighbour != null && westNeighbour.getPiece() == null){
            emptyNeighbours.add(westNeighbour);
        }
        return emptyNeighbours;
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

    public void setRowColumn(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return this.row;

    }

    public int getColumn(){
        return column;
    }
}
