package Board;
import java.awt.Color;

/**
 * Represents one of the players of the game
 */
public class Player {
    Color colour;

    public Player(Color colour){
        this.colour = colour;
    }


    public Color getColour(){
        return colour;
    }
}
