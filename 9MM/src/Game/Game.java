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
    private Display display;
    private Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    private Player inTurnPlayer;
    private int turn = 0;
    private int turnCounter = 0;
    private GameState gameState;
    private Piece selectedPiece;

    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     */
    public Game() {
        this.board = new Board();
        this.players.add(new Player(Color.blue, "Player 1"));
        this.players.add(new Player(Color.red, "Player 2"));
        inTurnPlayer = players.get(turn);

        //Place pieces
        gameState = GameState.PLACING;
    }

    /**
     * Handles the game logic when a player clicks a position
     * @param pos the position that was clicked
     */
    public void buttonPressed(Position pos) {
        switch (gameState) { 

            //Place a piece
            case PLACING:
                //First phase of the game, players place their pieces
                int lastPieceIndex = inTurnPlayer.getNumOfPiecesPlaced();

                if (pos.getPiece() == null) {
                    pos.setPiece(inTurnPlayer.getPieces().get(lastPieceIndex));
                    inTurnPlayer.piecePlaced();

                    //If all pieces have been placed - and it is the last player to do so
                    if (lastPieceIndex == inTurnPlayer.maxPieces - 1 && inTurnPlayer == players.get(players.size() - 1)) {
                        gameState = GameState.SELECTING;
                        display.removeHighlights();

                        changeTurn();
                        break;
                    }

                    changeTurn();
                    display.displayPossibleMoves(
                            board.getPossibleMoves(gameState, inTurnPlayer.getPieces().get(lastPieceIndex)),
                            inTurnPlayer.getColour());
                }
                break;

            // Select a piece to move
            case SELECTING:
                selectedPiece = null;
                if (pos.getPiece() == null) {
                    break;
                }
                updatePieceSelection(pos, GameState.FLYING);
                break;

            // A piece can be moved anywhere 
            case FLYING:
                if (pos.getPiece() == null) {
                    board.movePiece(selectedPiece, pos);
                    changeTurn();
                    display.removeHighlights();
                    gameState = GameState.SELECTING;
                } else {
                    updatePieceSelection(pos, GameState.FLYING);
                }
                break;
             
            //An unknown gamestate was given
            default:
                throw new IllegalArgumentException("Unknown gamestate was given: '" + gameState +  "'");
                
        }

        //Always update the display after an action
        display.updateDisplay(board);
    }

    /**
     * Changes the player who is in turn
     */
    public void changeTurn() {
        turn = (++turnCounter) % players.size();
        inTurnPlayer = players.get(turn);
    }

    /** 
     * Enusres that a valid piece is selected and displays the possible moves for that piece
     * @param pos the position of the piece clicked
     * @param state different states have different possible moves
    */
    public void updatePieceSelection(Position pos, GameState state) {
        if (pos.getPiece().getOwner() == inTurnPlayer) {
            selectedPiece = pos.getPiece();
            gameState = state;
            display.displayPossibleMoves(board.getPossibleMoves(gameState, selectedPiece),
                inTurnPlayer.getColour());
        } else {
            selectedPiece = null;
            display.removeHighlights();
        }
    }

    /**
     * Sets the display for this game
     * @param display the display to be used
     */
    public void setDisplay(Display display) {
        this.display = display;

        //Tells the display to display this game
        display.createDisplay(board);
        
        //Game starts with player 1 having some possible moves
        display.displayPossibleMoves(board.getPossibleMoves(gameState, inTurnPlayer.getPieces().get(0)),
            inTurnPlayer.getColour());
    }
}