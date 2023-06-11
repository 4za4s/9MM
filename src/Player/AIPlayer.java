package Player;

import java.awt.Color;

import Board.Position;
import Display.NamedColour;
import Game.Game;
import Player.AI.AIMove;


/**
 * A player which is not human
 */
public class AIPlayer extends Player {
    private AIMove moveGenerator;
    

    /**
     * Constructor
     */
    public AIPlayer(AIMove moveGenerator) {
        super();
        this.moveGenerator = moveGenerator;
        this.isAI = true;
    }

    /**
     * Constructor
     */
    public AIPlayer(NamedColour colour, AIMove moveGenerator) {
        super(colour);
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
