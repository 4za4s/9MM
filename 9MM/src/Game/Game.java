package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Piece;
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
    private Piece selectedPiece;

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

                    if (players.get(turn).getPiecesPlaced() == players.get(turn).getPieces().size() && turn == 1) {
                        gameState = newGameState.SELECTING;
                        display.removeHighlights();

                        turn();
                        break;
                    }
                    
                    turn();
                    display.displayPossibleMoves(board.getPossibleMoves(gameState, players.get(turn).getPieces().get(index)), players.get(turn).getColour());
                }
                break;
            case SELECTING:
                selectedPiece = null;
                if (pos.getPiece() == null) {
                    break;
                }
                selectPiece(pos, newGameState.FLYING);
                break;
                
            case FLYING:
                if (pos.getPiece() == null) {
                    board.movePiece(selectedPiece, pos);
                    turn();
                    display.removeHighlights();
                    gameState = newGameState.SELECTING;
                } else {
                    selectPiece(pos, newGameState.FLYING);
                }
                break;
            default:
                break;
        }

        display.updateDisplay(board);
    }

    public void turn() {
        turn = (turn + 1) % 2;
    }

    public void selectPiece(Position pos, newGameState state) {
        if (pos.getPiece().getOwner() == players.get(turn)) {
            selectedPiece = pos.getPiece();
            gameState = state;
            display.displayPossibleMoves(board.getPossibleMoves(gameState, selectedPiece), players.get(turn).getColour());
        } else {
            selectedPiece = null;
            display.removeHighlights();
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
        display.createDisplay(board);
        display.displayPossibleMoves(board.getPossibleMoves(gameState, players.get(turn).getPieces().get(0)), players.get(turn).getColour());
    }
}
