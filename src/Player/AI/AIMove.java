package Player.AI;

import Board.Position;
import Game.Game;


/**
 * Means a class has an AI to dictate its moves
 */
public interface AIMove {
    /**
     * Get the (alleged) best move 
     */
    public Position getMove(Game game);
}
