package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Player;
import Board.Position;
import Display.Display;


/**
 * Main class that handles all the game logic and actions
 */
public class Game {

    // Enum for the different game states, for implementing different rules at each stage of the game
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
    private Player inTurnPlayer;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     */
    public Game() {
        this.board = new Board();
        this.players.add(new Player(Color.blue, "Player 1"));
        this.players.add(new Player(Color.red, "Player 2"));

        inTurnPlayer = players.get(turn);
        gameState = newGameState.PLACING;
    }

    /**
     * Handles the game logic when a player clicks a position
     * @param pos the position that was clicked
     */
    public void buttonPressed(Position pos) {
        switch (gameState) {
            case PLACING:
                //First phase of the game, players place their pieces
                int index = inTurnPlayer.getPiecesPlaced();
                
                if (pos.getPiece() == null) {
                    pos.setPiece(inTurnPlayer.getPieces().get(index));
                    inTurnPlayer.piecePlaced();

                    if (inTurnPlayer.getPiecesPlaced() == inTurnPlayer.getPieces().size() && turn == 1) {
                        gameState = newGameState.SELECTING;
                        display.removeHighlights();

                        turn();
                        break;
                    }
                    
                    turn();
                    display.displayPossibleMoves(board.getPossibleMoves(gameState, inTurnPlayer.getPieces().get(index)), inTurnPlayer.getColour());
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
        inTurnPlayer = players.get(turn);
    }

    public void selectPiece(Position pos, newGameState state) {
        if (pos.getPiece().getOwner() == inTurnPlayer) {
            selectedPiece = pos.getPiece();
            gameState = state;
            display.displayPossibleMoves(board.getPossibleMoves(gameState, selectedPiece), inTurnPlayer.getColour());
        } else {
            selectedPiece = null;
            display.removeHighlights();
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
        display.createDisplay(board);
        display.displayPossibleMoves(board.getPossibleMoves(gameState, inTurnPlayer.getPieces().get(0)), inTurnPlayer.getColour());
    }
}
