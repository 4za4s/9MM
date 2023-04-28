package Board;

import java.util.ArrayList;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    ArrayList<Position> positions = new ArrayList<Position>();

    public Board(){
        System.out.println("Creating Positions");
        for (int i = 0; i < 24; i++) {
            positions.add(new Position());
        }
    }

    public ArrayList<Position> getPositions(){
        return positions;
    }
}
