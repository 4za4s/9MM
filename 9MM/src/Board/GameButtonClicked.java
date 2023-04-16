package Board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Display.Display;

/**
 * Listener for when a button is clicked
 */
public class GameButtonClicked implements ActionListener {
    
    GameButton button; //the button that is clicked
    Display display; // where to send message one button is cliked

    public GameButtonClicked(GameButton button, Display display){

        this.button = button;
        this.display = display;

        button.addActionListener(this);
    }


    /**
     * What happens when the button is press. It tells display that it was pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        display.buttonClicked(button);

    }


}