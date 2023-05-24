package Display;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Board.Player;
import Game.GameState;

/**
 * Text to give player guidance during game
 */
public class TurnText extends JLabel{

    /**
     * Constructor
     */
    public TurnText(){
        super("",SwingConstants.CENTER);
    }
    
    /**
     * Change the text
     * @param player player text is refering to
     * @param gameState current game state
     */
    public void setText(Player player, GameState gameState){
        String textUpper = player.getName();
        String textLower = getGameStateText( gameState);
        super.setText(textUpper + textLower);

    }

    /**
     * Work out specific text to add based on a game state
     * @param gameState gamestate
     * @return specific text to add
     */
    private String getGameStateText(GameState gameState){

        switch (gameState){

            case PLACING:
                return ": Select an empty position to place a piece in";

            case FLYING:
            return ": Select an empty position to fly to";


            case MOVING:
                return ": Select an empty position to move a piece to";


            case SELECTING:
                return ": Select a piece to move";
                
            case TAKING:
                return ": Select an available enemy piece to take";
            
            case POSTGAME:
                return " Wins!";

            default:
                return "";
        
           

        }



        
    }
}
