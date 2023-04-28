package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Player;
import Board.Position;
import Display.Display;

public class Game {
    public enum newGameState {
        PLACING,
        SELECTING,
        MOVING,
        FLYING,
        TAKING
    }

    private Display display;
    private Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    private int turn = 0;
    private newGameState gameState;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     * @param display the display that will be used to display the game
     */
    public Game() {
        System.out.println("Making Game Objects");
        this.board = new Board();
        this.players.add(new Player(Color.blue, "Player 1"));
        this.players.add(new Player(Color.red, "Player 2"));


        gameState = newGameState.PLACING;
    }

    public void buttonPressed(Position pos) {
        switch (gameState) {
            case PLACING:
                int index = players.get(turn).getPiecesPlaced();
                
                if (pos.getPiece() == null) {
                    pos.setPiece(players.get(turn).getPieces().get(index));
                    players.get(turn).piecePlaced();

                    if (players.get(turn).getPiecesPlaced() == 9 && turn == 1) {
                        gameState = newGameState.SELECTING;
                        display.displayPossibleMoves(new ArrayList<Position>(), players.get(0).getColour());

                        turn();
                        break;
                    }
                    
                    turn();
                    display.displayPossibleMoves(board.getPossibleMoves(gameState, players.get(turn).getPieces().get(index)), players.get(turn).getColour());
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
        display.displayPossibleMoves(board.getPossibleMoves(gameState, players.get(turn).getPieces().get(0)), players.get(turn).getColour());
    }
}
