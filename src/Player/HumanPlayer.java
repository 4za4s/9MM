package Player;

import Board.Position;
import Game.Game;

/**
 * A player whose moves are be selected by a human
 */
public class HumanPlayer extends Player {
    public HumanPlayer() {
        this.isAI = false;
    }


    @Override
    public Position getMove(Game game) {
        return null;
    }

}
