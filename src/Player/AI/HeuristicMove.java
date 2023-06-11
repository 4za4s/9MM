package Player.AI;

import Board.Piece;
import Board.Position;
import Game.Game;
import Game.GameState;
import Player.Player;


/**
 * Checks for specific situation on the board, uses this info to make moves. Plays 2 move ahead
 */
public class HeuristicMove implements AIMove {
    private Game game;
    /**
     * Checks for a few specific scenarios, otherwise does a random move
     */
    @Override
    public Position getMove(Game game) {
        this.game = game;
        Position pos;

        if (game.getGameState() == GameState.PLACING ||
                game.getGameState() == GameState.MOVING ||
                game.getGameState() == GameState.FLYING) {

            // Try to get three in a row directly
            pos = checkPlayerForPossibleNewMill(game.getInTurnPlayer(), game.getNotInTurnPlayer());

            if (pos != null) {
                return pos;
            }

            // If can't get three in a row try to prevent opponent from getting 3 in a row
            pos = checkPlayerForPossibleNewMill(game.getNotInTurnPlayer(), game.getInTurnPlayer());

            if (pos != null) {
                return pos;
            }

        }
        // Try select a piece that can move to a three in a row

        pos = checkPlayerForMillNextTurn(game.getInTurnPlayer(), game.getNotInTurnPlayer());
        if (pos != null) {
            return pos;
        }

        // If no good moves found make a random move
        return new RandomValidMove().getMove(game);
    }

    /**
     * Checks for a potential 3 in a row move for a player
     * 
     * @param playerToCheckFor player to check for a three in a row for
     * @param otherPlayer      other player
     * @return a three completer location if fond, otherwise null
     */
    private Position checkPlayerForPossibleNewMill(Player playerToCheckFor, Player otherPlayer) {

        Piece piece = game.getSelectedPiece();
        // Check for each position
        for (Position position : game.getBoard().getPossibleMoves(game.getGameState(), piece, playerToCheckFor,
                otherPlayer)) {

            if (hasTwoOutOfThree(position, playerToCheckFor, 'X')) {
                return position;
            }
        }
        return null;
    }

    /**
     * Checks for a move that will lead to a 3 in a row one move later
     */
    private Position checkPlayerForMillNextTurn(Player playerToCheckFor, Player otherPlayer) {
        Piece piece = game.getSelectedPiece();
        for (Position position : game.getBoard().getPossibleMoves(game.getGameState(), piece, playerToCheckFor,
                otherPlayer)) {

            // Check each direction, make sure the position returned is not
            // counting the piece given in its 2/3 count

            // North
            try {
                if (hasTwoOutOfThree(position.getNorthNeighbour(), playerToCheckFor, 'S')){
                    return position;
                }

            } catch (Exception NullPointerException) {
            }

            // South
            try {
                if (hasTwoOutOfThree(position.getSouthNeighbour(), playerToCheckFor, 'N')) {
                    return position;
                }

            } catch (Exception NullPointerException) {
            }
            // East
            try {
                if (hasTwoOutOfThree(position.getEastNeighbour(), playerToCheckFor, 'W')) {
                    return position;
                }

            } catch (Exception NullPointerException) {
            }

            // West
            try {
                if (hasTwoOutOfThree(position.getWestNeighbour(), playerToCheckFor, 'E')) {
                    return position;
                }

            } catch (Exception NullPointerException) {
            }

        }

        return null;
    }

    /**
     * Checks if a given position was filled, if it would create a mill
     * Looks for pattern: _xx and x_x (in any orientation), where x is two of same player's piece and _ is
     * empty slot
     * 
     * @param directionToIgnore don't check for a mill in this direction way. X = check
     *                          all . N = don't check if position given was found
     *                          north of a two in a row, S = was found south.
     *                          E = was found east. W = was found west. V = in
     *                          middle vertically
     *                          H = was in middle horizontally
     * @return if position was filled, if it would create a mill
     */
    private boolean hasTwoOutOfThree(Position position, Player playerToCheckFor, char directionToIgnore) {

        // Ensure position is empty
        if (position.getPiece() != null) {
            return false;
        }

        // Pos south of two in a row
        if (directionToIgnore != 'N') {
            try {
                if (position.getNorthNeighbour().getPiece().getOwner() == playerToCheckFor &&
                        position.getNorthNeighbour().getNorthNeighbour().getPiece().getOwner() == playerToCheckFor) {
                    return true;
                }
            } catch (Exception NullPointerException)  {} 

        
        } 

        // Pos north of two in a row
        if (directionToIgnore != 'S'){
            try {
                if (position.getSouthNeighbour().getPiece().getOwner() == playerToCheckFor &&
                        position.getSouthNeighbour().getSouthNeighbour().getPiece().getOwner() == playerToCheckFor) {
                    return true;
                }
            } catch (Exception NullPointerException) {}


           
        }
        // Pos to west of two in a row
        if (directionToIgnore != 'E'){
                
                try {
                    if (position.getEastNeighbour().getPiece().getOwner() == playerToCheckFor &&
                            position.getEastNeighbour().getEastNeighbour().getPiece().getOwner() == playerToCheckFor) {
                        return true;
                    }
                } catch (Exception NullPointerException) {}

        }

        // Pos to east of two in a row
        if (directionToIgnore != 'W'){
            try {
                if (position.getWestNeighbour().getPiece().getOwner() == playerToCheckFor &&
                        position.getWestNeighbour().getWestNeighbour().getPiece().getOwner() == playerToCheckFor) {
                    return true;
                }
            } catch (Exception NullPointerException) { }


    
        }

        // Pos in middle of horizontal two in a row
        if (directionToIgnore != 'H'){
            try {
                if (position.getWestNeighbour().getPiece().getOwner() == playerToCheckFor &&
                        position.getEastNeighbour().getPiece().getOwner() == playerToCheckFor) {
                    return true;
                }
            } catch (Exception NullPointerException) { }

        
        }
        // Pos in middle of vertical two in a row
        if (directionToIgnore != 'V'){
            try {
                if (position.getNorthNeighbour().getPiece().getOwner() == playerToCheckFor &&
                        position.getSouthNeighbour().getPiece().getOwner() == playerToCheckFor) {
                    return true;
                }
            } catch (Exception NullPointerException) { } 
        } 

        return false;
    }

}
