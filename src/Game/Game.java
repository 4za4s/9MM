package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Player;
import Board.Position;
import Display.GameDisplay;


/**
 * Main class that handles all the game logic and actions
 */
public class Game {
    private GameDisplay gameDisplay;
    private Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    private Player inTurnPlayer;
    private int turn = 0;
    private int turnCounter = 0;
    private GameState gameState;
    private Piece selectedPiece; //piece that has been selected to be moved

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
                        gameDisplay.removeHighlights();

                        changeTurn();
                        break;
                    }

                    changeTurn();
                    gameDisplay.displayPossibleMoveHighlights(
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
                updatePieceSelection(pos, GameState.MOVING);
                break;

            // A piece can be moved anywhere 
            case MOVING:
                //Make sure piece is moving to an empty neighbour
                if (selectedPiece.getPosition().getEmptyNeighbours().contains(pos)) {
                    board.movePiece(selectedPiece, pos);
                    changeTurn();
                    gameDisplay.removeHighlights();
                    gameState = GameState.SELECTING;

                }
                break;
             
            //An unknown gamestate was given
            default:
                throw new IllegalArgumentException("Unknown gamestate was given: '" + gameState +  "'");
                
        }

        //Always update the display after an action
        gameDisplay.updateDisplay(board);
    }

    /**
     * Changes the player who is in turn
     */
    public void changeTurn() {
        turn = (++turnCounter) % players.size();
        System.out.println("Turn = " + turnCounter);
        inTurnPlayer = players.get(turn);
    }

    /** 
     * Enusres that a valid piece is selected and displays the possible moves for that piece
     * @param pos the position of the piece clicked. Assumes that pos has a piece
     * @param state different states have different possible moves
    */
    public void updatePieceSelection(Position pos, GameState state) {      
        if (pos.getPiece().getOwner() == inTurnPlayer) {
            selectedPiece = pos.getPiece();
            gameState = state;
            gameDisplay.displayPossibleMoveHighlights(board.getPossibleMoves(gameState, selectedPiece),
                inTurnPlayer.getColour());
        } else {
            selectedPiece = null;
            gameDisplay.removeHighlights();
        }
    }

    /**
     * Sets the display for this game
     * @param display the display to be used
     */
    public void setGameDisplay(GameDisplay display) {
        this.gameDisplay = display;

        //Tells the display to display this game
        display.createDisplay(board);
        
        //Game starts with player 1 having some possible moves
        display.displayPossibleMoveHighlights(board.getPossibleMoves(gameState, inTurnPlayer.getPieces().get(0)),
            inTurnPlayer.getColour());
    }
}
