package Board;

import java.util.ArrayList;

import Game.GameState;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    ArrayList<Position> positions = new ArrayList<Position>();
    ArrayList<Position> possibleMoves;

    private int[][] positionLocations = { 
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
        //24 total positions on the board

        //Create positions
        for (int i = 24; i > 0; i--) {
            positions.add(new Position());
        }

        //For each posiion, set the position that is the closest left as its left neighbour]
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
        //Four exceptions (across the centre square)
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
    public void movePiece(Piece piece, Position newPosition) {
        //remove the piece from the current position
        piece.getPosition().setPiece(null);

        //place it on the new position
        piece.setPosition(newPosition);
    }

    /**
     * Calculates the possible moves for a piece
     * @param gameState the current game state
     * @param piece the piece to get the moves for
     * @return ArrayList of possible moves
     */
    public ArrayList<Position> getPossibleMoves(GameState gameState, Piece piece) {
        Position piecePos = piece.getPosition();

        switch (gameState) {
            // Currently pieces can move anywhere no matter the game state
            // case PLACING:
            //     return null;

            // case SELECTING:
            //     return null;

            // case MOVING:
            //     return null;

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



            case FLYING:

            
                return piecePos.getEmptyNeighbours();

                

              

            default:
                //all cases should be acounted for
                throw new IllegalArgumentException("Unknown gamestate was given: '" + gameState +  "'");
        }    
    }
    
    /**
     * Gets the positions on the board
     * @return ArrayList of positions
     */
    public ArrayList<Position> getPositions(){
        return positions;
    }
}
