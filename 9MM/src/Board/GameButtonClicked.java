package Board;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Display.Display;

public class GameButtonClicked implements ActionListener {
    
    GameButton button;
    Display display;
    Color colour = Color.pink;

    public GameButtonClicked(GameButton button, Display display){

        this.button = button;
        this.display = display;

        button.addActionListener(this);
    }

    //for when the button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        display.buttonClicked(button);

    }


}