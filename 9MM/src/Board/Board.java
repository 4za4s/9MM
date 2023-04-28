package Board;

/**
 * The board where all of the pices go on. Keeps track of pieces and their
 * movement
 */
public class Board {
    Position[] positions;

    public Board(){
        System.out.println("Creating Positions");
        for (int i = 0; i < 24; i++) {
            positions[i] = new Position();
        }
    }

    public Position[] gePositions(){
        return positions;
    }
}
