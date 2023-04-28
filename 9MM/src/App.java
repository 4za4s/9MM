import Display.Display;
import Game.Game;
/**
 * This is the driver class for 9MM game
 */
public class App{
    public static void main(String[] args) {  
        // create new game
        System.out.println("INITIALISING GAME");
        Game game = new Game();
        Display display = new Display(game);

        game.setDisplay(display);
        System.out.println("GAME CREATED");

        //TODO run more games with 'display'
    }
}


