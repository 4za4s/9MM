package Player;

import java.awt.Color;

import Board.Board;
import Board.Piece;
import Board.Position;
import Game.GameState;

public class HumanPlayer extends Player {
    public HumanPlayer(Color colour, String playerName) {
        super(colour, playerName);
        this.isAI = false;
    }


    @Override
    public Position getMove(Board board, GameState gameState, Player inTurnPlayer, Player notInTurnPlayer) {
        return null;
    }

}
