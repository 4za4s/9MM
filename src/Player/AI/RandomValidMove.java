package Player.AI;

import java.util.ArrayList;

import Board.Board;
import Board.Position;
import Game.Game;
import Game.GameState;
import Player.Player;
import Board.Piece;

public class RandomValidMove implements AIMove {

    @Override
    public Position getMove(Game game) {
        Board board = game.getBoard();
        GameState gameState = game.getGameState();
        Player inTurnPlayer = game.getInTurnPlayer();
        Player notInTurnPlayer = game.getNotInTurnPlayer();

        switch (gameState) {
            case PLACING:
                return randomPlace(board.getPossibleMoves(gameState, null, inTurnPlayer, notInTurnPlayer));
            case SELECTING:
                ArrayList<Position> positions = board.getPossibleMoves(gameState, null, inTurnPlayer, notInTurnPlayer);
                return randomSelect(positions, board, inTurnPlayer, notInTurnPlayer);
            case MOVING:
                return randomMove(board.getPossibleMoves(gameState, game.getSelectedPiece(), inTurnPlayer, notInTurnPlayer));
            case FLYING:
                return randomFly(board.getPossibleMoves(gameState, game.getSelectedPiece(), inTurnPlayer, notInTurnPlayer));
            case TAKING:
                return randomTake(board.getPossibleMoves(gameState, game.getSelectedPiece(), inTurnPlayer, notInTurnPlayer));
            default:
                return null;
        }
        // // Ensure at least 1 valid move in piece selected
        // ArrayList<Position> positions;

        // // Work out piece
        // ArrayList<Piece> pieces = inTurnPlayer.getPieces();

        // Piece piece;
        // do {
        //     double random = Math.random();
        //     int randomPos = (int) (random * pieces.size());
        //     piece = pieces.get(randomPos);

        //     // Ensure to get a piece with a valid position and if selecting the piece must
        //     // then be able to move
        // } while ((piece.getPosition() == null && gameState == GameState.PLACING) ||
        //         (gameState == GameState.SELECTING
        //                 && board.getPossibleMoves(GameState.MOVING, piece, inTurnPlayer, notInTurnPlayer).size() == 0));
        // // TODO add an exception for flying

        // // if (gameState == GameState.SELECTING) {
        // // System.out.println("game state is " + gameState);
        // // System.out.println("pos moves = " + (board.getPossibleMoves(GameState.MOVING,
        // // piece, inTurnPlayer, notInTurnPlayer).size()));

        // // }

        // // if (gameState == GameState.MOVING) {
        // // System.out.println("game state is " + gameState);
        // // System.out.println(" row , column = " + piece.getPosition().getRow() + " , "
        // // + piece.getPosition().getColumn() );
        // // System.out.println(piece.getOwner().getName());

        // // }

        // // Get possible positions
        // positions = board.getPossibleMoves(gameState, piece, inTurnPlayer, notInTurnPlayer);

        // // System.out.println("size = " + positions.size());
        // // System.out.println("gamestate = "+ gameState);

        // // Get position
        // int size = positions.size();
        // double random = Math.random();
        // int randomPos = (int) (random * size);
        // // System.out.println("Random position value = " + randomPos);

        // return positions.get(randomPos);
    }


    public Position randomPlace(ArrayList<Position> positions) {
        double random = Math.random();
        return positions.get((int) (random * positions.size()));
    }

    private Position randomSelect(ArrayList<Position> positions, Board board, Player inTurnPlayer, Player notInTurnPlayer) {
        Position pos;
        do {
        double random = Math.random();
        pos = positions.get((int) (random * positions.size()));
        } while (board.getPossibleMoves(GameState.MOVING, pos.getPiece(), inTurnPlayer, notInTurnPlayer).size() == 0);
        return pos;
    }

    private Position randomMove(ArrayList<Position> positions) {
        double random = Math.random();
        return positions.get((int) (random * positions.size()));
    }

    private Position randomFly(ArrayList<Position> positions) {
        double random = Math.random();
        return positions.get((int) (random * positions.size()));
    }

    private Position randomTake(ArrayList<Position> positions) {
        double random = Math.random();
        return positions.get((int) (random * positions.size()));
    }

}
