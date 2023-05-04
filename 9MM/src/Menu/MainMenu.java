package Menu;

import Display.GameDisplay;
import Game.Game;

public class MainMenu {
    
    public static void createGame(){
        Game game = new Game();
        GameDisplay display = new GameDisplay(game);
        game.setDisplay(display);
    }
}
