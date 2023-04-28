package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Player;
import Board.Position;
import Display.Display;

public class Game {
    enum state {
        PLACING,
        SELECTING,
        MOVING,
        FLYING,
        TAKING
    }

    private Display display;
    private Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    private int turn;
    private state gameState;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     * @param display the display that will be used to display the game
     */
    public Game() {
        System.out.println("Making Game Objects");
        this.board = new Board();
        this.players.add(new Player(Color.blue, "Player 1"));
        this.players.add(new Player(Color.red, "Player 2"));
        this.turn = 0;

        gameState = state.PLACING;
    }

    public void buttonPressed(Position pos) {
        switch (gameState) {
            case PLACING:
                if (pos.getPiece() == null) {
                    int index = players.get(turn).getPiecesPlaced();
                    pos.setPiece(players.get(turn).getPieces().get(index));
                    players.get(turn).piecePlaced();

                    if (players.get(turn).getPiecesPlaced() == 9 && turn == 1) {
                        gameState = state.SELECTING;
                    }
                    
                    turn();
                }
                break;
            case SELECTING:
                break;
            case FLYING:
                break;
            default:
                break;
        }

        display.updateDisplay(board);
    }

    public void turn() {
        turn = (turn + 1) % 2;
    }

    public void setDisplay(Display display) {
        this.display = display;
        display.createDisplay(board);
    }
}
