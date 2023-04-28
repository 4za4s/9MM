package Board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Display.Display;

/**
 * Listener for when a button is clicked
 */
public class GameButtonClicked implements ActionListener {
    
    private Piece piece; //the button that is clicked
    private Display display; // where to send message one button is cliked

    public GameButtonClicked(Piece piece, Display display){

        this.piece = piece;
        this.display = display;

        piece.addActionListener(this);
        
    }

    /**
     * What happens when the button is press. It tells display that it was pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        display.buttonClicked(piece);

    }
}