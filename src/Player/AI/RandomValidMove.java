package Player.AI;

import java.util.ArrayList;

import Board.Board;
import Board.Position;
import Game.GameState;
import Player.Player;
import Board.Piece;

public class RandomValidMove implements AIMove {

    @Override
    public Position getMove(Board board, GameState gameState, Player inTurnPlayer, Player notInTurnPlayer) {

        //Ensure at least 1 valid move in piece selected

        ArrayList<Position> positions;
        
       
        //Work out piece
        ArrayList<Piece> pieces = inTurnPlayer.getPieces();


            
        Piece piece;
        do {
            double random = Math.random();
            int randomPos = (int) (random * pieces.size());
            piece = pieces.get(randomPos);

        //Ensure to get a piece with a valid position and if selecting the piece must then be able to move
        } while( //TODO: simplify this?
            (piece.getPosition() == null && gameState != GameState.PLACING) ||
            (gameState == GameState.SELECTING &&  
             board.getPossibleMoves(GameState.MOVING, piece, inTurnPlayer, notInTurnPlayer).size() == 0)
            ); //TODO add an exception for flying

        // if (gameState == GameState.SELECTING) {
        // System.out.println("game state is " + gameState);
        // System.out.println("pos moves = " + (board.getPossibleMoves(GameState.MOVING, piece, inTurnPlayer, notInTurnPlayer).size()));
        
        // }

        // if (gameState == GameState.MOVING) {
        //     System.out.println("game state is " + gameState);
        //     System.out.println(" row , column = " + piece.getPosition().getRow() + " , " + piece.getPosition().getColumn() );
        //     System.out.println(piece.getOwner().getName());
            
        //     }


        //Get possible positions
        positions = board.getPossibleMoves(gameState, piece, inTurnPlayer, notInTurnPlayer);

        // System.out.println("size = " + positions.size());
        // System.out.println("gamestate = "+  gameState);


        //Get position
        int size = positions.size();
        double random = Math.random();
        int randomPos = (int) (random * size);
        // System.out.println("Random position value = " + randomPos);




        return positions.get(randomPos);
    }
}
