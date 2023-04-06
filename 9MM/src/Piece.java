import java.awt.Color;

/* represents a piece on the board */

public class Piece {


    int prevRow = -1; //allows to keep track of prev positions so display can know what to change when moved
    int prevColumn = -1;

    int row;   
    int column;
    Color colour = Color.red; //TODO: make this dependant on a player somehow

    public Piece(int row, int column){
        this.row = row;
        this.column = column;

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
        return colour;
    }
}
