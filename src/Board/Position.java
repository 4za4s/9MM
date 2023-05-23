package Board;

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * RA position on the board, holds a single piece or nothing and has a list of its neighbours
 */
public class Position extends JButton {
    private Piece piece; //the piece it holds
    private Position northNeighbour; //north neighbour
    private Position southNeighbour; //south neighbour
    private Position eastNeighbour; //east neighbour
    private Position westNeighbour; //west neightbour

    private int row; //row of the piece (on the board)
    private int column; //column of the piece (on the board)

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
     * Sets the north neighbour of the position 
     * @param northNeighbour the postition to set as north neighbour
     */
    public void setNorthNeighbour(Position northNeighbour){
        this.northNeighbour = northNeighbour;
    }

    /**
     * Sets the south neighbour of the position 
     * @param southNeighbour the postition to set as south neighbour
     */
    public void setSouthNeighbour(Position southNeighbour){
        this.southNeighbour = southNeighbour;
    }

    /**
     * Sets the east neighbour of the position 
     * @param eastNeighbour the postition to set as east neighbour
     */
    public void setEastNeighbour(Position eastNeighbour){
        this.eastNeighbour = eastNeighbour;
    }

    /**
     * Sets the west neighbour of the position 
     * @param westNeighbour the postition to set as west neighbour
     */
    public void setWestNeighbour(Position westNeighbour){
        this.westNeighbour = westNeighbour;
    }

    /**
     * Gets north neighbour of the piece
     * @return the north neighbour
     */
    public Position getNorthNeighbour(){
       return northNeighbour;
    }

    /**
     * Gets south neighbour of the piece
     * @return the south neighbour
     */
    public Position getSouthNeighbour(){
        return southNeighbour;
    }



    /**
     * Gets east neighbour of the piece
     * @return the east neighbour
     */
    public Position getEastNeighbour(){
        return eastNeighbour;
    }



    /**
     * Gets west neighbour of the piece
     * @return the west neighbour
     */
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
    }

    /**
     * sets the row and column variables
     * @param row row to set
     * @param column column to set
     */
    public void setRowColumn(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row
     * @return row
     */
    public int getRow(){
        return this.row;

    }

    /**
     * Gets the column
     * @return column
     */
    public int getColumn(){
        return column;
    }
}
