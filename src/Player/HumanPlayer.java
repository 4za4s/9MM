package Player;

import java.awt.Color;

import Board.Position;
import Game.Game;

public class HumanPlayer extends Player {
    public HumanPlayer(Color colour, String playerName) {
        super(colour, playerName);
        this.isAI = false;
    }


    @Override
    public Position getMove(Game game) {
        return null;
    }

}
