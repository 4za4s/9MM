package Player.AI;

import java.util.ArrayList;

import Board.Board;
import Board.Position;

public class RandomMove implements AIMove {

    @Override
    public Position getMove(Board board) {
        ArrayList<Position> positions = board.getPositions();

        int size = positions.size();
        double random = Math.random();

        int randomPos = (int) (random * size);
        System.out.println("Random position = " + randomPos);

        return positions.get(randomPos);
    }
}
