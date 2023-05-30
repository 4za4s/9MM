package Player;

import java.awt.Color;

import Board.Board;
import Board.Position;

public class Human extends Player {
    public Human(Color colour, String playerName) {
        super(colour, playerName);
        this.isAI = false;
    }

    @Override
    public Position getMove(Board board) {
        return null;
    }
}
