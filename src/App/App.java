package App;

import Display.Display;
import Game.Game;
/**
 * This is the driver class for 9MM game
 */
public class App{

    /**
     * Main class for the game, will handle app stuff like the main menu later
     */
    public static void main(String[] args) {  

        // create new game
        Game game = new Game();
        Display display = new Display(game);
        game.setDisplay(display);

        //TODO run more games with 'display'
    }
}


//No highlihght option if can't select
//Show pieces remaining and taken away
//Better back ground
//Turn text
