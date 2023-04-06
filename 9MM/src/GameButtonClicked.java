import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButtonClicked implements ActionListener {
    
    GameButton button;
    Board board;
    Color colour = Color.pink;

    public GameButtonClicked(GameButton button, Board board){

        this.button = button;
        this.board = board;

        button.addActionListener(this);
    }

    //for when the button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        board.buttonClicked(button.getRowPos(),button.getColumnPos());



    }


}