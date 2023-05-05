package Menu;

import Display.Display;
import Game.Game;

public class MainMenu {

    public void startGame(Display display) {
        display.displayGame(new Game());
    }
}
