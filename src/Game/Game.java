package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import Board.Board;
import Board.Piece;
import Board.Position;
import Display.GameDisplay;
import Player.AIPlayer;
import Player.HumanPlayer;
import Player.Player;
import Player.AI.RandomValidMove;
import Player.AI.NeuralNetwork.NeuralNet;

/**
 * Handles all the game logic and actions
 */
public class Game {
    private Game game = this;
    private GameDisplay gameDisplay; // display for the game
    private Board board; // board the game is moving pieces around on
    private ArrayList<Player> players = new ArrayList<Player>(); // players of the game
    private Player inTurnPlayer; // player whos turn it is to make an action
    private Player notInTurnPlayer; // player who is not in turn to make an action
    private int turnIndex = 0; // index in list of player whose turn it isgameUpdatesToWait
    private int turnCounter = 0; // counter for nubmer of turns passes
    private GameState gameState; // what state the game is in
    private Piece selectedPiece; // piece that has been selected to be moved
    private int toTake = 0; // how many pieces are left to take. In 1 turn a player can take up to 2 pieces
    private final int maxGameUpdatesToWait = 5; //max time to wait between game updates
    private int gameUpdatesToWait = maxGameUpdatesToWait; //how long left to wait for next game update
    private Timer timer; //keeps track of time for game updates
    public static final int statlemateCounter = 50; //number of moves that can happen before a stalemate
    

    /**
     * Creates a new game, can be extended later to include different player types
     * (AI, human, etc.)
     */
    public Game() {
        this.board = new Board();

        // this.players.add(new HumanPlayer(Color.blue, "Player Blue"));
        this.players.add(new AIPlayer(Color.blue, "Player Blue", new RandomValidMove(), this));

        this.players.add(new AIPlayer(Color.red, "Player Red", new NeuralNet(3,5), this));

        inTurnPlayer = players.get(turnIndex);
        notInTurnPlayer = players.get(turnIndex + 1);
        // Place pieces
        gameState = GameState.PLACING;
    }


    /**
     * Please don't remove. I keep on finding I need this later and then have to reimpliment it
     */
    public void startGame(){
        updateDisplay();

        //Ensure timely game updates
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                   game.updateGame();
            }
        };
        timer = new Timer(5, action); 
        timer.start();
    }

    /**
     * Get the board the game is base on
     * 
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get current game state
     * 
     * @return current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Handles  
     * @param pos the position that was clicked
     */
    public void buttonPressed(Position pos) {
        playAction(pos);
    }

    public void playAction(Position pos) {
        switch (gameState) {

            // Place a piece
            case PLACING:
                // First phase of the game, players place their pieces
                int lastPieceIndex = inTurnPlayer.getNumOfPiecesPlaced();
                Piece piece = inTurnPlayer.getPieces().get(lastPieceIndex);

                if (pos.getPiece() == null) {
                    toTake = board.movePiece(piece, pos);
                    inTurnPlayer.piecePlaced();
                    if (toTake > 0) {
                        gameState = GameState.TAKING;
                        break;
                    }

                    // If all pieces have been placed - and it is the last player to do so
                    if (inTurnPlayer.getNumOfPiecesPlaced() == inTurnPlayer.maxPieces
                            && inTurnPlayer == players.get(players.size() - 1)) {
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
                    if (inTurnPlayer.getNumOfPiecesPlaced() - inTurnPlayer.getNoOfPiecesLost() > 3) {
                        gameState = GameState.MOVING;
                    } else {
                        gameState = GameState.FLYING;
                    }
                }
                break;

            // A piece can be moved anywhere
            case FLYING:
            case MOVING:
                if ((gameState == GameState.FLYING && pos.getPiece() == null) ||
                        (gameState == GameState.MOVING
                                && selectedPiece.getPosition().getEmptyNeighbours().contains(pos))) {
                    // Make sure piece is moving to an empty neighbour
                    toTake = board.movePiece(selectedPiece, pos);
                    if (toTake > 0) {
                        gameState = GameState.TAKING;
                        selectedPiece = null;
                        break;
                    }
                    gameState = GameState.SELECTING;
                    changeTurn();
                    checkForPossibleMoves(inTurnPlayer);
                    break;
                    // If user selects a different piece belonging to him, change selection to that
                    // piece
                } else if (pos.getPiece() != null && selectedPiece != pos.getPiece()
                        && pos.getPiece().getOwner() == inTurnPlayer) {

                    selectedPiece = pos.getPiece();
                } else {
                    // Otherwise deselect piece selected
                    gameState = GameState.SELECTING;
                    selectedPiece = null;
                }
                break;

            // A piece can take another piece
            case TAKING:
                selectedPiece = pos.getPiece();

                if (board.canTakePiece(gameState, selectedPiece, inTurnPlayer, notInTurnPlayer)) {
                    Player opponent = selectedPiece.getOwner();
                    opponent.pieceLost();
                    board.movePiece(selectedPiece, null);
                    toTake--;
                    if (opponent.getPieces().size() - opponent.getNoOfPiecesLost() < 3) {
                        gameDisplay.playerWins(inTurnPlayer);
                        gameState = GameState.PLAYERWON;
                        break;
                    }

                    // No more pieces to take this turn
                    if (toTake <= 0) {

                        // Work out correct new gamestate
                        changeTurn();

                        if (inTurnPlayer.getNumOfPiecesPlaced() < inTurnPlayer.maxPieces) {
                            gameState = GameState.PLACING;

                        } else {
                            gameState = GameState.SELECTING;
                        }

                        // Win conditions
                        checkForPossibleMoves(inTurnPlayer);

                        if (inTurnPlayer.getNoOfPiecesLost() == inTurnPlayer.maxPieces - 2) {
                            gameState = GameState.PLAYERWON;
                            gameDisplay.playerWins(notInTurnPlayer);
                        }

                    }
                }
                selectedPiece = null;
                break;

            // An unknown gamestate was given
            default:
                break;
        }

        // Always update the display after an action
        if (gameState == GameState.PLAYERWON){
            game.endGame();
        } else if (turnCounter == statlemateCounter) {
            game.stalemate();
        }
        updateDisplay();
    }

    /**
     * Checks if it is possible for a ;layer to make any moves. Important because no
     * moves = lose
     * 
     * @param player player to check
     * @return if any moves are possible
     */
    public boolean checkForPossibleMoves(Player player) {
        if (gameState == GameState.PLACING) {
            return true;
        }

        // Find a possible move
        for (Piece p : inTurnPlayer.getPieces()) {
            if (p.getPosition() != null &&
                    board.getPossibleMoves(GameState.MOVING, p, inTurnPlayer, notInTurnPlayer).size() != 0) {
                return true;
            }
        }
        gameState = GameState.PLAYERWON;
        gameDisplay.playerWins(notInTurnPlayer);
        return false;
    }

    /**
     * Gets player who is in turn
     * 
     * @return player who is in turn
     */
    public Player getInTurnPlayer() {
        return inTurnPlayer;
    }

    /**
     * Gets player who is not in turn
     * 
     * @return player who is not in turn
     */
    public Player getNotInTurnPlayer() {
        return notInTurnPlayer;
    }

    /**
     * Gets currently selected piece (eg the piece which you have just selected to
     * be move)
     * 
     * @return
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Sets the display for this game
     * 
     * @param gameDisplay the display to be used
     */
    public void setGameDisplay(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;
        updateDisplay();
    }

    /**
     * Gets list of all players
     * 
     * @return alist of all players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Changes the player who is in turn
     */
    public void changeTurn() {
        turnIndex = (++turnCounter) % players.size();

        System.out.println("Turn = " + turnCounter);
        notInTurnPlayer = inTurnPlayer;
        inTurnPlayer = players.get(turnIndex);

        updateDisplay();

    }

    /**
     * What to update 
     */
    private void updateDisplay(){

        //Update selectability
        if (inTurnPlayer.isAI()){
            gameDisplay.updateSelectability(false);
        } else {
            gameDisplay.updateSelectability(true);
        }

        //Update rest of display TODO: put above into update display?
        gameDisplay.updateDisplay();
    }

    private void updateGame(){
        if (inTurnPlayer.isAI() && --gameUpdatesToWait < 0){
            gameUpdatesToWait = maxGameUpdatesToWait;

            playAction(inTurnPlayer.getMove(this));
        }
    }

    /**
     * All that needs to happen when game ends
     */
    public void endGame(){
        timer.stop();
    }

    public void stalemate(){
        timer.stop();
        gameState = GameState.STALEMATE;
        gameDisplay.stalemate(game.getPlayers());
    }
    
}
