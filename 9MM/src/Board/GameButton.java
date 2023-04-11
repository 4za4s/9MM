package Board;
import javax.swing.JButton;

/* like a normal button, but with a way to save some attributes */
public class GameButton extends JButton{

    private Piece piece;


    public GameButton(Piece piece){

        super(); //run the super stuff

        this.piece = piece;

        
    }

    public Piece getPiece(){
        return piece;
    }

    // public int getColumnPos(){
    //     return columnPos;
    // }
    
    // public String getType(){
    //     return type;
    // }
}
