import java.awt.Color;

/* represents a piece on the board */

public class Piece {

    Player owner;

    
    int prevRow; //allows to keep track of prev positions so display can know what to change when moved
    int prevColumn;

    int row;   
    int column;

    public Piece(int row, int column,Player owner){
        this.row = row;
        this.column = column;
        prevRow = row;
        prevColumn = column;
        this.owner = owner;

    }

    public void movePiece(int newRow, int newColumn){
        prevRow = row;
        prevColumn = column;

        row = newRow;
        column = newColumn;
    }

    public int getPrevRow(){
        return prevRow;
    }

    public int getPrevColumn(){
        return prevColumn;
    }
    
    public int getRow(){
        return row;
    }


    public int getColumn(){
        return column;
    }

    public Color getColour(){
        return owner.getColour();
    }

    public Player getOwner(){
        return owner;
    }
}
