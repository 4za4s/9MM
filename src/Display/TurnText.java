package Display;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Board.Player;
import Game.GameState;

public class TurnText extends JLabel{

    public TurnText(){
        super("",SwingConstants.CENTER);
        // layerTurn = new JLabel("Currently " + game.getInTurnPlayer().getName() + "'s Turn",SwingConstants.CENTER);
    }
    
    public void setText(Player player, GameState gameState){
        String textUpper = player.getName();
        String textLower = getGameStateText( gameState);
        super.setText(textUpper + textLower);

    }

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
