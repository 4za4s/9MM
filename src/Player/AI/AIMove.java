package Player.AI;

import Board.Position;
import Game.Game;

public interface AIMove {
    /**
     * Get the (alleged) best move 
     */
    public Position getMove(Game game);
}
