package Board;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButtonClicked implements ActionListener {
    
    GameButton button;
    BoardManager manager;
    Color colour = Color.pink;

    public GameButtonClicked(GameButton button,BoardManager manager){

        this.button = button;
        this.manager = manager;

        button.addActionListener(this);
    }

    //for when the button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        manager.buttonClicked(button.getRowPos(),button.getColumnPos());

    }


}