package Player.AI;

import java.util.ArrayList;

import Board.Board;
import Board.Position;
import Game.Game;
import Game.GameState;
import Player.Player;

/**
 * Makes a random valid move
 */
public class RandomValidMove implements AIMove {

    @Override
    public Position getMove(Game game) {
        Board board = game.getBoard();
        GameState gameState = game.getGameState();
        Player inTurnPlayer = game.getInTurnPlayer();
        Player notInTurnPlayer = game.getNotInTurnPlayer();

        return randomPosition(board.getPossibleMoves(gameState, game.getSelectedPiece(), inTurnPlayer, notInTurnPlayer));
    }


    /**
     * Gets a random position
     * @param positions list of positions to choose from
     * @return random position form list given
     */
    public Position randomPosition(ArrayList<Position> positions) {
        double random = Math.random()- 0.0001;
        return positions.get((int) (random * positions.size()));
    }
}
