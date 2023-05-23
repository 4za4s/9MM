package Game;

import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Player;
import Board.Position;
import Display.GameDisplay;


/**
 * Handles all the game logic and actions
 */
public class Game {
    private GameDisplay gameDisplay; //display for the game
    private Board board; //board the game is moving pieces around on
    private ArrayList<Player> players = new ArrayList<Player>(); //players of the game 
    private Player inTurnPlayer; //player whos turn it is to make an action
    private Player notInTurnPlayer; //player who is not in turn to make an action
    private int turnIndex = 0; //index in list of player whose turn it is
    private int turnCounter = 0; //counter for nubmer of turns passes
    private GameState gameState; //what state the game is in
    private Piece selectedPiece; //piece that has been selected to be moved
    private int toTake = 0; //how many pieces are left to take. In 1 turn a player can take up to 2 pieces


    /**
     * Creates a new game, can be extended later to include different player types (AI, human, etc.)
     */
    public Game() {
        this.board = new Board();

        this.players.add(new Player(Color.blue, "Player Blue"));
        this.players.add(new Player(Color.red, "Player Red"));
        inTurnPlayer = players.get(turnIndex);
        notInTurnPlayer = players.get(turnIndex + 1);

        //Place pieces
        gameState = GameState.PLACING;
    }

    /**
     * Get the board the game is base on
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get current game state
     * @return current game state
     */
    public GameState getGameState() {
        return gameState;
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
                Piece piece = inTurnPlayer.getPieces().get(lastPieceIndex);

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

                if (board.canTakePiece(gameState, selectedPiece, inTurnPlayer, notInTurnPlayer)){ 
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
                            gameDisplay.playerWins(notInTurnPlayer);
                        }
                            
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
        turnIndex = (++turnCounter) % players.size();
        System.out.println("Turn = " + turnCounter);
        notInTurnPlayer = inTurnPlayer;
        inTurnPlayer = players.get(turnIndex);
        
    }

    /**
     * Checks if it is possible for a ;layer to make any moves. Important because no moves = lose
     * @param player player to check
     * @return if any moves are possible
     */
    public boolean checkForPossibleMoves(Player player){
        if (gameState == GameState.PLACING) {
            return true;
        }

        //Find a possible move
        for (Piece p : inTurnPlayer.getPieces()) {
            if (p.getPosition() != null &&
            board.getPossibleMoves(GameState.MOVING, p, inTurnPlayer, notInTurnPlayer).size() != 0) {
                return true;
            }
        }
        gameState = GameState.POSTGAME;
        gameDisplay.playerWins(notInTurnPlayer);
        return false;
    }

    /**
     * Gets player who is in turn
     * @return player who is in turn
     */
    public Player getInTurnPlayer() {
        return inTurnPlayer;
    }

    /**
     * Gets player who is not in turn
     * @return player who is not in turn
     */
    public Player getNotInTurnPlayer() {
        return notInTurnPlayer;
    }


    /**
     * Gets currently selected piece (eg the piece which you have just selected to be move)
     * @return
     */
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

    /**
     * Gets list of all players
     * @return alist of all players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
