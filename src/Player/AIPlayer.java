package Player;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Position;
import Player.AI.AIMove;

public class AIPlayer extends Player {
    private AIMove moveGenerator;

    public AIPlayer(Color colour, String playerName, AIMove moveGenerator) {
        super(colour, playerName);
        this.moveGenerator = moveGenerator;
    }

    @Override
    public Position getMove(Board board) {
        return moveGenerator.getMove(board);
    }
}
