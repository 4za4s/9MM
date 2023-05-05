package Display;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

import Menu.MainMenu;

public class MenuDisplay extends JLayeredPane {
    private Display display;

    /**
     * Class constructor
     */
    public MenuDisplay(MainMenu menu, Display display){
        this.display = display;
        int y = 100;
        int x = 100;

        //Put piece on board
        JButton start = new JButton("Start");
        start.setBounds(x,y,100,100);
        start.setBackground(Color.white);

        //creates a new listener for each position that will call the buttonPressed method in the game class
        start.addActionListener(e -> menu.startGame(display));

        display.add(start); 
    }
}
