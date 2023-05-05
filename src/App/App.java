package App;

import javax.swing.JFrame;

import Display.Display;
import Game.Game;
import Menu.MainMenu;
/**
 * This is the driver class for 9MM game
 */
public class App{
    /**
     * Main class for the game, will handle app stuff like the main menu later
     */
    public static void main(String[] args) {  
        Display window = new Display();
        window.displayMenu(new MainMenu());
    }
}


//No highlihght option if can't select
//Show pieces remaining and taken away
//Better back ground
//Turn text
