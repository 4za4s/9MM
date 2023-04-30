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

    /**
     * Class constructor
     */
    public Board(){
        //24 total positions on the board
        for (int i = 0; i < 24; i++) {
            positions.add(new Position());
        }
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
        switch (gameState) {
            // Currently pieces can move anywhere no matter the game state
            // case PLACING:
            //     return null;

            // case SELECTING:
            //     return null;

            // case MOVING:
            //     return null;

            case PLACING: //these two cases have the same possible moves but represent different stages of the game
            case FLYING:
                //start with empty list always
                possibleMoves = new ArrayList<Position>();

                //fill with possible moves
                for (Position position : positions) {
                    if (position.getPiece() == null) {
                        possibleMoves.add(position);
                    }
                }

                return possibleMoves;

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
