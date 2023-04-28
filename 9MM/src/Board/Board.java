package Board;

import Display.Display;
import java.util.ArrayList;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    Position[] positions;

    public Board(){
        for (int i = 0; i < 24; i++) {
            positions[i] = new Position();
        }
    }

    public Position[] gePositions(){
        return positions;
    }
}
