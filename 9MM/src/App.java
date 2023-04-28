import Display.Display;
/**
 * This is the driver class for 9MM game
 */
public class App{
    public static void main(String[] args) {  
        // create new game
        System.out.println("INITIALISING GAME");
        Game game = new Game(new Display());
        System.out.println("GAME CREATED");
    }
}


