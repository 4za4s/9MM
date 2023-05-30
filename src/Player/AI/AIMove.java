package Player.AI;

import Board.Board;
import Board.Position;

public interface AIMove {
    public Position getMove(Board board);
}
