package Board;

import Display.Display;

import java.util.ArrayList;



/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {

    private Display display; //what displays the board
    private BoardManager boardManager; //what manages the board
    private Piece[][] pieceArray = new Piece[7][7];// array for storing the pieces
    

    private int[] lastPieceSelected = { 0, 0 }; // location of last piece selected (button clicked). row, column
    private int[] secondLastPieceSelected = { 0, 0 }; // These are for keeping track of what piece to move where

    private int[][] validLocations = new int[][] { { 0, 0 }, { 0, 3 }, { 0, 6 }, // so later can work out it a move is valid
            { 1, 1 }, { 1, 3 }, { 1, 5 }, // {row,column}
            { 2, 2 }, { 2, 3 }, { 2, 4 }, 
            { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 4 }, { 3, 5 }, { 3, 6 },
            { 4, 2 }, { 4, 3 }, { 4, 4 },
            { 5, 1 }, { 5, 3 }, { 5, 5 },
            { 6, 0 }, { 6, 3 }, { 6, 6 } };

    /**
     * Create a piece and adds it to the piece array
     * 
     * @param row    row of the new piece
     * @param column column of the new piece
     * @param owner  which player the piece belongs to
     */
    public void createPiece(int row, int column, Player owner) {
        Piece tempPieceVar = new Piece(row, column, owner);
        pieceArray[row][column] = tempPieceVar;
    }

    /**
     * Gets positions of all available locations where a piece can move to.
     * This is then passed to Display which handles the rendering
     * @param inTurnPlayer player who is currently in turn. So highlihgting can match his colour
     */
    public void displayAvailableLocations(Player inTurnPlayer) {

        // Get position {row,column} of all empty locations since they are all valid
        // moves for this sprint
        ArrayList<int[]> locationsArrayList = new ArrayList<int[]>();

        for (int[] location : validLocations) {
            if (pieceArray[location[1]][location[0]] == null) {
                locationsArrayList.add(location);

            }

        }
        display.displayAvailableLocation(locationsArrayList, inTurnPlayer);
    }



    /**
     * Called when a button is clicked, gives info about what the button was for to board manager
     * @param piece the piece that the button which was clicked represents
     */
    public void buttonClicked(Piece piece) {
        // Recordlast two buttons clicked
        secondLastPieceSelected[0] = lastPieceSelected[0];
        secondLastPieceSelected[1] = lastPieceSelected[1];

        lastPieceSelected[0] = piece.getRow();
        lastPieceSelected[1] = piece.getColumn();

        boardManager.buttonClicked(piece.getPhrase());

    }

    
    /**
     * Sets the valid locations in Display class TODO: redo this a better way
     */
    public void setDisplaysValidLocations() {
        display.setValidLocations(validLocations);
    }

    /* Note: panel is the display frame */
    public void setDisplay(Display panel) {
        this.display = panel;
    }

    /* the the decidated manager for this humble board to report to */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Moves a pieces. Does this by overriding the last selected position with the
     * second last selected position. Then
     * sets the second last selected position to null.
     */
    public void moveLatestPiece() {
        pieceArray[lastPieceSelected[0]][lastPieceSelected[1]] = pieceArray[secondLastPieceSelected[0]][secondLastPieceSelected[1]];
        pieceArray[secondLastPieceSelected[0]][secondLastPieceSelected[1]] = null;

    }

    /**
     * Rerenders the display of the board
     * 
     * @param selectablePlayer   the players whos pieces can be selected
     * @param piecePhase         the phrase to send to board manager if a piece is
     *                           selected (button pressed)
     * @param noPlayerString     the phrase to send to board manager if an empty
     *                           slot is selected (button pressed)
     * @param noPlayerSelectable if empty slots can be selected
     * @param turnText the text to display telling user what to do
     */
    public void updateBoard(Player selectablePlayer, String piecePhase, String noPlayerString,
            boolean noPlayerSelectable, String turnText) {
        display.updateDisplay(pieceArray, selectablePlayer, piecePhase, noPlayerString, noPlayerSelectable, turnText);
    }

    public int[] getLastPieceSelected(){
        return lastPieceSelected;
    }

}
