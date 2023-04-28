import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Player;
import Display.Display;

public class Game {
    private Display display;
    private Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    private int turn;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     * @param display the display that will be used to display the game
     */
    public Game(Display display) {
        System.out.println("Making Game Objects");
        this.display = display;
        this.board = new Board();
        this.players.add(new Player(Color.blue, "Player 1"));
        this.players.add(new Player(Color.red, "Player 2"));
        this.turn = 0;

        board.getPositions().get(0).setPiece(new Piece(players.get(0)));
        board.getPositions().get(8).setPiece(new Piece(players.get(1)));
        display.createDisplay(board);
    }

    public void nextTurn() {
        turn = (turn + 1) % 2;
    }
}
