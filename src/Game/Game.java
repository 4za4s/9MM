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
import Player.AI.HeuristicPlayer;
import Player.AI.RandomValidMove;
import Player.AI.NeuralNetwork.NeuralNet;
import Player.AI.NeuralNetwork.TrainNeuralNet;

/**
 * Handles all the game logic and actions
 */
public class Game {
    private GameDisplay gameDisplay; // display for the game
    private Board board; // board the game is moving pieces around on
    private ArrayList<Player> players = new ArrayList<Player>(); // players of the game
    private Player inTurnPlayer; // player whos turn it is to make an action
    private Player notInTurnPlayer; // player who is not in turn to make an action
    private int turnCounter = 0; // counter for nubmer of turns passes
    private GameState gameState; // what state the game is in
    private Piece selectedPiece; // piece that has been selected to be moved
    private int toTake = 0; // how many pieces are left to take. In 1 turn a player can take up to 2 pieces
    private final int maxGameUpdatesToWait = 1; //max time to wait between game updates
    private int gameUpdatesToWait = maxGameUpdatesToWait; //how long left to wait for next game update
    private Timer timer; //keeps track of time for game updates
    public static final int statlemateCounter = 150; //number of moves that can happen before a stalemate
    

    /**
     * Creates a new game, can be extended later to include different player types
     * (AI, human, etc.)
     */
    public Game() {

        // this.players.add(new HumanPlayer(Color.blue, "Player Blue"));
        // this.players.add(new AIPlayer(Color.blue, "Player Blue", new RandomValidMove()));
        // this.players.add(new HumanPlayer(Color.blue, "Player Blue"));
        // this.players.add(new HumanPlayer(Color.green, "Player Green"));
        // this.players.add(new HumanPlayer(Color.red, "Player Red"));
        // this.players.add(new AIPlayer(Color.red, "Player Red", new RandomValidMove()));
        // this.players.add(new AIPlayer(Color.red, "Player Red", new NeuralNet("test")));
        this(new AIPlayer(Color.blue, "Player Blue", new RandomValidMove()), new AIPlayer(Color.green, "Player Green", new RandomValidMove()));
    }

    public Game(Player player1, Player player2) {
        //create game board
        this.board = new Board();

        //add players to the game
        this.players.add(player1);
        this.players.add(player2);

        //set the first player to be in turn
        inTurnPlayer = players.get(0);
        notInTurnPlayer = players.get(1);

        // Place pieces
        gameState = GameState.PLACING;
    }

    public Game(Player player1, Player player2, TrainNeuralNet trainNeuralNet) {
        this(player1, player2);
        this.training = trainNeuralNet;
    }

    /**
     * Please don't remove. I keep on finding I need this later and then have to reimpliment it
     */
    public void startGame(){
        updateDisplay();

        //Ensure timely game updates
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    updateGame();
            }
        };
        timer = new Timer(1, action); 
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

                        //Check for opposition will have no moves on his upcoming turn
                        checkForPossibleMoves();

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

                     checkForPossibleMoves();
                    
                    changeTurn();

                   
                    
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
                        playerWins(inTurnPlayer);
                        break;
                    }

                    // No more pieces to take this turn
                    if (toTake <= 0) {


                        // Win conditions (is turn of the potential winnier)
                        checkForPossibleMoves();
                       

                        if (notInTurnPlayer.getNoOfPiecesLost() == notInTurnPlayer.maxPieces - 2) {
                            playerWins(inTurnPlayer);
                        }



                        // Work out correct new gamestate
                        changeTurn();

                        if (inTurnPlayer.getNumOfPiecesPlaced() < inTurnPlayer.maxPieces) {
                            gameState = GameState.PLACING;

                        } else {
                            gameState = GameState.SELECTING;
                        }



                    }
                }
                selectedPiece = null;
                break;

            // An unknown gamestate was given
            default:
                break;
        }
        // Check for stalemantes
       if (turnCounter >= statlemateCounter) {
            stalemate();
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
    public boolean checkForPossibleMoves() {
        if (gameState == GameState.PLACING) {
            return true;
        }

        // Find a possible move
        for (Piece p : notInTurnPlayer.getPieces()) {
            if (p.getPosition() != null &&
                    board.getPossibleMoves(GameState.MOVING, p, notInTurnPlayer, inTurnPlayer).size() != 0) {
                return true;
            }
        }
        //Game ends on no possible moves
        playerWins(inTurnPlayer);
        
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

        //Don't change turn if game is over
        if (gameState == GameState.PLAYERWON || gameState == GameState.STALEMATE){ 
            return;
        }

        notInTurnPlayer = inTurnPlayer;
        inTurnPlayer = players.get((++turnCounter) % players.size());

        System.out.println("Turn = " + turnCounter + " " + inTurnPlayer.getName() );

        updateDisplay();

    }

    /**
     * What to update 
     */
    private void updateDisplay(){
        //Update rest of display
        if (gameDisplay != null){
            gameDisplay.updateDisplay();
        }
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
    public void playerWins(Player player){
        System.out.println("Winner is " + player.getName());
        timer.stop();
        gameState = GameState.PLAYERWON;
        if (gameDisplay != null){
            gameDisplay.playerWins(player);
        }

        if (training != null){
            training.gameOver(false);
        }
    }

    public void stalemate(){
        System.out.println("Statemate");
        timer.stop();
        gameState = GameState.STALEMATE;
        if (gameDisplay != null){
            gameDisplay.stalemate(getPlayers());
        }

        if (training != null){
            training.gameOver(false);
        }
    }

    public void exitGame(){
        timer.stop();
        if (training != null){
            training.gameOver(true);
        }
    }
    
}
