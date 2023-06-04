package Board;

import java.util.ArrayList;

import Game.GameState;
import Player.Player;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    private ArrayList<Position> positions = new ArrayList<Position>(); //list of all positions on the board
    private ArrayList<Position> possibleMoves; // list possible moves that can currently be made
    private Mills mills; // mills on the board

    private int[][] positionLocations = {  //locations of positions on the board
        { 0, 0 }, { 0, 3 }, { 0, 6 },   // {row,column}
        { 1, 1 }, { 1, 3 }, { 1, 5 }, 
        { 2, 2 }, { 2, 3 }, { 2, 4 }, 
        { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 4 }, { 3, 5 }, { 3, 6 },
        { 4, 2 }, { 4, 3 }, { 4, 4 },
        { 5, 1 }, { 5, 3 }, { 5, 5 },
        { 6, 0 }, { 6, 3 }, { 6, 6 } 
    };

    

    /**
     * Class constructor
     */
    public Board(){
        this.mills = new Mills();

        //24 total positions on the board
        //Create positions
        for (int i = 24; i > 0; i--) {
            positions.add(new Position());
        }

        //For each posiion, set the position that is the closest in x direction as its x neighbour
        int westMinDist; 
        int eastMinDist; 
        int northMinDist;
        int southMinDist;
        for (int changingPos = 0; changingPos < positions.size(); changingPos++){
            westMinDist = 100000; //Not specific, just has to be very large
            eastMinDist = 100000;
            northMinDist = 100000;
            southMinDist = 100000;

            for (int otherPos = 0; otherPos < positions.size(); otherPos++){

               
                //Left
                if (positionLocations[changingPos][0] == positionLocations[otherPos][0] && //Same row
                    positionLocations[changingPos][1] > positionLocations[otherPos][1] && //Left
                    positionLocations[changingPos][1] - positionLocations[otherPos][1] < westMinDist //Least left
                ){
                    westMinDist = positionLocations[changingPos][1] - positionLocations[otherPos][1];
                    
                    positions.get(changingPos).setWestNeighbour(positions.get(otherPos));
                }

                //Right
                if (positionLocations[changingPos][0] == positionLocations[otherPos][0] && //Same row
                positionLocations[changingPos][1] < positionLocations[otherPos][1] && //Right
                positionLocations[otherPos][1] - positionLocations[changingPos][1] < eastMinDist //Least right
                ){
                    eastMinDist = positionLocations[otherPos][1] - positionLocations[changingPos][1];
                    
                    positions.get(changingPos).setEastNeighbour(positions.get(otherPos));
                }

                //Up
                if (positionLocations[changingPos][1] == positionLocations[otherPos][1] && //Same Column
                positionLocations[changingPos][0] > positionLocations[otherPos][0] && //Up
                positionLocations[changingPos][0] - positionLocations[otherPos][0] < northMinDist //Least Up
                ){
                    northMinDist = positionLocations[changingPos][0] - positionLocations[otherPos][0];
                    
                    positions.get(changingPos).setNorthNeighbour(positions.get(otherPos));
                }

                //Down
                if (positionLocations[changingPos][1] == positionLocations[otherPos][1] && //Same Column
                positionLocations[changingPos][0] < positionLocations[otherPos][0] && //Down
                positionLocations[otherPos][0] - positionLocations[changingPos][0] < southMinDist //Least Down
                ){
                    southMinDist = positionLocations[otherPos][0] - positionLocations[changingPos][0];
                    
                    positions.get(changingPos).setSouthNeighbour(positions.get(otherPos));
                }
            }
        }
        //Four exceptions for neighbour (across the centre square)
        positions.get(7).setSouthNeighbour(null);
        positions.get(11).setEastNeighbour(null);
        positions.get(12).setWestNeighbour(null);
        positions.get(16).setNorthNeighbour(null);
    }

    /**
     * Moves a piece from one position to another
     * @param piece the piece to move
     * @param newPosition the position to move the piece to
     */
    public int movePiece(Piece piece, Position newPosition) {
        //remove the piece from the current position
        if (piece != null) {
            mills.removeMill(piece.getPosition());
            piece.setPosition(newPosition);
        }
        if (newPosition != null) {
            newPosition.setPiece(piece);
        }

        return mills.createMill(piece);
    }


     /**
      * Calculates the possible moves for a piece
      * @param gameState the current game state
      * @param piece the piece to get the moves for
      * @param inTurnPlayer player that is in turn
      * @param notInTurnPlayer player that is not in turn
      * @return ArrayList of possible moves
      */
    public ArrayList<Position> getPossibleMoves(GameState gameState, Piece piece, Player inTurnPlayer, Player notInTurnPlayer) {

        switch (gameState) {

            //Placing and flying have same output
            case FLYING:
            case PLACING: 
                //start with empty list always
                possibleMoves = new ArrayList<Position>();

                //fill with possible moves
                for (Position position : positions) {
                    if (position.getPiece() == null) {
                        possibleMoves.add(position);
                    }
                }
                return possibleMoves;

            case SELECTING:
                ArrayList<Position> possibleMoves = new ArrayList<Position>();
                
                for (Piece playerPiece : inTurnPlayer.getPieces()){
                    if (playerPiece.getPosition() != null){
                        if (inTurnPlayer.getNumOfPiecesPlaced() - inTurnPlayer.getNoOfPiecesLost() < 4){
                            possibleMoves.add(playerPiece.getPosition());
                        } else if (getPossibleMoves(GameState.MOVING, playerPiece, inTurnPlayer, notInTurnPlayer).size() > 0){
                            possibleMoves.add(playerPiece.getPosition());
                        }
                    }
                   
                }
                return possibleMoves;

            case MOVING:
                if (piece == null){
                    return new ArrayList<Position>();
                }
                return piece.getPosition().getEmptyNeighbours();

            case TAKING:

                possibleMoves = new ArrayList<Position>();

                //first work out if player to take has pieces in a mill
                if ( mills.playerHasPieceNotInMill(notInTurnPlayer)){

                    for (Piece playerPiece : notInTurnPlayer.getPieces()) {
                        if (playerPiece.getPosition() != null && 
                            !mills.isInMill(playerPiece)) {
                            possibleMoves.add(playerPiece.getPosition() );
                        }
                    }
                    

                } else { //Allow any piece to be taken
                    for (Position position : positions) {
                        if (position.getPiece() != null && 
                        position.getPiece().getOwner() != inTurnPlayer) {
                            possibleMoves.add(position);
                        }
                    }

                }


            return possibleMoves;
 

            default:
                return new ArrayList<Position>();
        }    
    }

     /**
      * Works out if a piece can be taken
      * @param gameState current game state
      * @param piece piece to check
      * @param inTurnPlayer player currently in turn
      * @param notInTurnPlayer player currently not in turn
      * @return if the peice can be taken
      */
    public boolean canTakePiece(GameState gameState, Piece piece, Player inTurnPlayer, Player notInTurnPlayer){
        ArrayList<Position> possibleMoves = getPossibleMoves( gameState,  piece,  inTurnPlayer, notInTurnPlayer);

        if  (piece == null){
                return false;
        }

        return possibleMoves.contains(piece.getPosition());
    }
    
    /**
     * Gets the positions on the board
     * @return ArrayList of positions
     */
    public ArrayList<Position> getPositions(){
        return positions;
    }

    /**
     * Gets related mills class
     * @return mills class
     */
    public Mills getMills(){
        return mills;
    }
}

