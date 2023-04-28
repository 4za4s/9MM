package Board;

import java.util.ArrayList;

import Game.Game.newGameState;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    ArrayList<Position> positions = new ArrayList<Position>();

    public Board(){
        System.out.println("Creating Positions");
        for (int i = 0; i < 24; i++) {
            positions.add(new Position());
        }
    }

    public void movePiece(Piece piece, Position newPosition) {
        piece.setPosition(newPosition);
    }

    public ArrayList<Position> getPossibleMoves(newGameState gameState, Piece piece) {
        switch (gameState) {
            case PLACING:
                ArrayList<Position> possibleMoves = new ArrayList<Position>();

                for (Position position : positions) {
                    if (position.getPiece() == null) {
                        possibleMoves.add(position);
                    }
                }

                return possibleMoves;

            case SELECTING:
                return null;

            case MOVING:
                return null;

            case FLYING:
                return null;

            default:
                return null;
        }
    }

    public ArrayList<Position> getPositions(){
        return positions;
    }
}
