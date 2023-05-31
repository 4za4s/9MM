package Player.AI;

import Board.Position;
import Game.Game;

public interface AIMove {
    public Position getMove(Game game);
}
