package Player.AI;

import Board.Board;
import Board.Piece;
import Board.Position;
import Game.GameState;
import Player.Player;

public interface AIMove {
    public Position getMove(Board board, GameState gameState, Player inTurnPlayer, Player notInTurnPlayer);
}
