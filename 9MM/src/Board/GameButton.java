package Board;
import javax.swing.JButton;

/**
 * Like a normal button java button, but with a way to save some attributes
 */
public class GameButton extends JButton{

    private Piece piece; // what piece this button represents


    public GameButton(Piece piece){
        this.piece = piece;
        
    }

    public Piece getPiece(){
        return piece;
    }
    
}
