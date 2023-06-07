package Player;

import java.awt.Color;

import Board.Position;
import Game.Game;
import Player.AI.AIMove;

public class AIPlayer extends Player {
    private AIMove moveGenerator;
    

    public AIPlayer(Color colour, String playerName, AIMove moveGenerator) {
        super(colour, playerName);
        this.moveGenerator = moveGenerator;
        this.isAI = true;
    }

    @Override
    public Position getMove(Game game) {
        return moveGenerator.getMove(game);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - " + moveGenerator.getClass().getSimpleName();
    }

}
