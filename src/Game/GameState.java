package Game;

/** 
 * Used to keep track of new and potentially new game states
 */
public enum GameState {
    PLACING, //Placing down a piece
    SELECTING, //Select a piece
    MOVING, //Move a piece to a neighbour
    FLYING, //Move anywhere :)
    TAKING, //Take opponents piece
    PLAYERWON, //Player has won
    STALEMATE //1000 moves have passed without a winner
}
