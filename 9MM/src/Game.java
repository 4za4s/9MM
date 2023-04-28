import java.awt.Color;

import Board.Board;
import Board.Player;
import Display.Display;

public class Game {
    private Display display;
    private Board board;
    private Player[] players;
    private int turn;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     * @param display the display that will be used to display the game
     */
    public Game(Display display) {
        this.display = display;
        this.board = new Board();
        this.players = new Player[] {new Player(Color.red, "Player 1"), new Player(Color.blue, "Player 2")};
        this.turn = 0;
    }

    public void nextTurn() {
        turn = (turn + 1) % 2;
    }
}
