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
    private Player notInTurnPlayer;
    private int turn = 0;
    private int turnCounter = 0;
    private GameState gameState;
    private Piece selectedPiece; //piece that has been selected to be moved
    private int toTake = 0;


    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     */
    public Game() {
        this.board = new Board();

        this.players.add(new Player(Color.blue, "Player Blue"));
        this.players.add(new Player(Color.red, "Player Red"));
        inTurnPlayer = players.get(turn);
        notInTurnPlayer = players.get(turn + 1);

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

        System.out.println("=================");
        System.out.println(gameState);
        System.out.println(inTurnPlayer.getName() + " pieces lost: " + inTurnPlayer.getNoOfPiecesLost());
        // System.out.println("Number of pieces left =  " + (opponent.getPieces().size() - opponent.getNoOfPiecesLost()));
        switch (gameState) { 

            //Place a piece
            case PLACING:
                //First phase of the game, players place their pieces
                int lastPieceIndex = inTurnPlayer.getNumOfPiecesPlaced();
                Piece piece = inTurnPlayer.getPieces().get(lastPieceIndex);

                // if (board.isAPossibleMove(gameState, pos.getPiece(), inTurnPlayer)){ //TODO: later refine code to use this
                if (pos.getPiece() == null) {
                    toTake = board.movePiece(piece, pos);
                    inTurnPlayer.piecePlaced();
                    if (toTake > 0){
                        gameState = GameState.TAKING;
                        break;
                    }

                    //If all pieces have been placed - and it is the last player to do so
                    if (inTurnPlayer.getNumOfPiecesPlaced() == inTurnPlayer.maxPieces && inTurnPlayer == players.get(players.size() - 1)) {
                        gameState = GameState.SELECTING;
                        changeTurn();
                        break;
                    }

                    changeTurn();
                }
                break;

            // Select a piece to move
            case SELECTING:
                if (pos.getPiece() == null || pos.getPiece().getOwner() != inTurnPlayer) {
                    selectedPiece = null;
                } else {
                    selectedPiece = pos.getPiece();
                    if (inTurnPlayer.getNumOfPiecesPlaced() - inTurnPlayer.getNoOfPiecesLost() > 3){ //TODO: I think this no work: inTurnPlayer.getNumOfPiecesPlaced() - inTurnPlayer.getNoOfPiecesLost() > 3
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
                    checkForPossibleMoves(inTurnPlayer);
                    break;
                     //If user selects a different piece belonging to him, change selection to that piece
                } else if (pos.getPiece() != null && selectedPiece != pos.getPiece() && pos.getPiece().getOwner() == inTurnPlayer) {
                   
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

                if (board.isAPossibleMove(gameState, selectedPiece, inTurnPlayer, notInTurnPlayer)){ 
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

                    //No more pieces to take this turn
                    if (toTake <= 0) {

                        //Work out correct new gamestate
                        changeTurn();

                            if  (inTurnPlayer.getNumOfPiecesPlaced() < inTurnPlayer.maxPieces){
                            gameState = GameState.PLACING; 

                        } else {
                            gameState = GameState.SELECTING;
                        }

                        //Win conditions
                        checkForPossibleMoves(inTurnPlayer);

                        if (inTurnPlayer.getNoOfPiecesLost() == inTurnPlayer.maxPieces - 2){
                            gameState = GameState.POSTGAME;
                            changeTurn();
                            gameDisplay.playerWins(inTurnPlayer);
                        }
                            
                    }
                } 
                selectedPiece = null;
                break;



            //An unknown gamestate was given
            default:
            //TODO: give an error message here
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
        notInTurnPlayer = inTurnPlayer;
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

    public boolean checkForPossibleMoves(Player player){
        if (gameState == GameState.PLACING) {
            return true;
        }

        for (Piece p : inTurnPlayer.getPieces()) {
            if (p.getPosition() != null && board.getPossibleMoves(GameState.MOVING, p, notInTurnPlayer, null).size() != 0) { //TODO: put inTurnPlayer in proper spot
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

    public Player getNotInTurnPlayer() {
        return notInTurnPlayer;
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
