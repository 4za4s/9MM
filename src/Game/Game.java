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
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player inTurnPlayer;
    private int turn = 0;
    private int turnCounter = 0;
    private GameState gameState;
    private GameState previousGameState;
    private Piece selectedPiece; //piece that has been selected to be moved
    private int toTake = 0;


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

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Handles the game logic when a player clicks a position
     * @param pos the position that was clicked
     */
    public void buttonPressed(Position pos) {

        System.out.println(gameState);
        switch (gameState) { 

            //Place a piece
            case PLACING:
                previousGameState = gameState;
                //First phase of the game, players place their pieces
                int lastPieceIndex = inTurnPlayer.getNumOfPiecesPlaced();
                Piece piece = inTurnPlayer.getPieces().get(lastPieceIndex);
                if (pos.getPiece() == null) {
                    toTake = board.movePiece(piece, pos);
                    inTurnPlayer.piecePlaced();
                    if (toTake > 0){
                        gameState = GameState.TAKING;
                        break;
                    }

                    //If all pieces have been placed - and it is the last player to do so
                    if (lastPieceIndex == inTurnPlayer.maxPieces - 1 && inTurnPlayer == players.get(players.size() - 1)) {
                        gameState = GameState.SELECTING;
                        changeTurn();
                        break;
                    }

                    changeTurn();
                }
                break;

            // Select a piece to move
            case SELECTING:
                previousGameState = gameState;
                if (pos.getPiece() == null || pos.getPiece().getOwner() != inTurnPlayer) {
                    selectedPiece = null;
                } else {
                    selectedPiece = pos.getPiece();
                    if (inTurnPlayer.getNumOfPiecesPlaced() - inTurnPlayer.getNoOfPiecesLost() > 3){
                        gameState = GameState.MOVING;
                    } else {
                        gameState = GameState.FLYING;
                    }
                }
                break;

            // A piece can be moved anywhere 
            case FLYING:
            case MOVING:
                if (
                    (gameState == GameState.FLYING && pos.getPiece() == null)||
                    (gameState == GameState.MOVING && selectedPiece.getPosition().getEmptyNeighbours().contains(pos))
                    ) {
                    //Make sure piece is moving to an empty neighbour
                    toTake = board.movePiece(selectedPiece, pos);
                    if (toTake > 0){
                        gameState = GameState.TAKING;
                        selectedPiece = null;
                        break;
                    }
                    gameState = GameState.SELECTING;
                    changeTurn();
                    checkPossibleMoves(inTurnPlayer);
                    break;
                } else if (pos.getPiece() != null && selectedPiece != pos.getPiece() && pos.getPiece().getOwner() == inTurnPlayer) {
                    //If user selects a different piece belonging to him, change selection to that piece
                    selectedPiece = pos.getPiece();
                } else {
                    //Otherwise deselect piece selected
                    gameState = GameState.SELECTING;
                    selectedPiece = null;
                }
                break;
            
            // A piece can take another piece
            case TAKING:
                selectedPiece = pos.getPiece();
                //Make sure that the piece is an opponent's piece
                if(selectedPiece != null && selectedPiece.getOwner() != inTurnPlayer) { 

                    Player opponent = selectedPiece.getOwner();
                    System.out.println("Taking piece");
                    opponent.pieceLost();
                    board.movePiece(selectedPiece, null);
                    toTake--;
                    if (opponent.getPieces().size() - opponent.getNoOfPiecesLost() < 3){
                        gameDisplay.playerWins(inTurnPlayer);
                        gameState = GameState.POSTGAME;
                        break;
                    }
                    System.out.println(toTake);
                    if (toTake <= 0) {
                        gameState = previousGameState;
                        changeTurn();
                        checkPossibleMoves(inTurnPlayer);
                    }
                } 
                selectedPiece = null;
                break;
            //An unknown gamestate was given
            default:
                break; 
        }

        //Always update the display after an action
        gameDisplay.updateDisplay();
    }

    /**
     * Changes the player who is in turn
     */
    public void changeTurn() {
        turn = (++turnCounter) % players.size();
        System.out.println("Turn = " + turnCounter);
        inTurnPlayer = players.get(turn);
    }

    public boolean canTakePiece(Piece piece, Player opponent) {
        if (piece == null) {
            return false;
        }
        if (piece.getOwner() == inTurnPlayer) {
            return false;
        }
        if (piece.isInMill()) {
            for (Piece p : opponent.getPieces()) {
                if (!p.isInMill()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkPossibleMoves(Player player){
        if (gameState == GameState.PLACING) {
            return true;
        }

        for (Piece p : inTurnPlayer.getPieces()) {
            if (p.getPosition() != null && board.getPossibleMoves(GameState.MOVING, p, inTurnPlayer).size() != 0) {
                return true;
            }
        }
        gameState = GameState.POSTGAME;
        changeTurn();
        gameDisplay.playerWins(inTurnPlayer);
        return false;
    }

    public Player getInTurnPlayer() {
        return inTurnPlayer;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Sets the display for this game
     * @param gameDisplay the display to be used
     */
    public void setGameDisplay(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;
        gameDisplay.updateDisplay();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
