package Board;
import java.awt.Color;

/**
 * Represents one of the players of the game
 */
public class Player {
    Color colour;
    String playerName;

    public Player(Color colour, String playerName){
        this.colour = colour;
        this.playerName = playerName;
    }

    public Color getColour(){
        return colour;
    }

    public String getName(){
        return playerName;
    }
}
