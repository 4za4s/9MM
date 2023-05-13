package Display;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class MenuDisplay extends AbstractDisplay {
    JButton start;

    /**
     * Class constructor
     */
    public MenuDisplay(int Width, int Height, Window window){
        super(Width, Height, window);
        start = new JButton("Start");
        createDisplay();
    }

    @Override
    public void createDisplay() {
        int y = window.getHeight()/2-50;
        int x = window.getWidth()/2-50;

        //Put piece on board
        start.setBounds(x,y,100,100);
        start.setBackground(Color.white);

        //creates a new listener for each position that will call the buttonPressed method in the game class
        start.addActionListener(e -> window.displayGame());
        window.add(start); 
    }

    @Override
    public void updateDisplay() {
    }

    @Override
    public void resizeDisplay(Dimension size) {
        setPreferredSize(size); 
        start.setBounds(size.width/2-50,size.height/2-50,100,100);
    }
}
