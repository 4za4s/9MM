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
        System.out.println("Creating Positions");

        //24 total positions on the board
        for (int i = 0; i < 24; i++) {
            positions.add(new Position());
        }
    }

    public void movePiece(Piece piece, Position newPosition) {
        piece.getPosition().setPiece(null);
        piece.setPosition(newPosition);
    }

    public ArrayList<Position> getPossibleMoves(GameState gameState, Piece piece) {
        switch (gameState) {
            // Currently pieces can move anywhere no matter the game state
            // case PLACING:
            //     return null;

            // case SELECTING:
            //     return null;

            // case MOVING:
            //     return null;

            case PLACING: //these two cases are the same (so no break statement)
            case FLYING:

                possibleMoves = new ArrayList<Position>();

                for (Position position : positions) {
                    if (position.getPiece() == null) {
                        possibleMoves.add(position);
                    }
                }

                return possibleMoves;

            default:
                throw new IllegalArgumentException("Unknown gamestate was given: '" + gameState +  "'");
        }


            
    }
    

    public ArrayList<Position> getPositions(){
        return positions;
    }
}
