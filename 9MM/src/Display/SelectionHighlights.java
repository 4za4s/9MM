package Display;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JLayeredPane;


/* Creates a drawing of selection highlights. This drawing has to be added to a panel to be viewed 
 * Highlights all locations in a list given
 * 
*/
class SelectionHighlights extends JLayeredPane { //prev JPanel

    private int boardPadding; //padding to each side of the board
    private int gap; // distance between concentric squares
    private int highlightSize; //how large to make highlighting
    private ArrayList<int[]> availableLocations; //locations a piece or empty slot should be
    private Color highlightColour; //highlight color to show available locations




    public SelectionHighlights( int boardPadding, int gap, int slotSize, ArrayList<int[]> availableLocations, Color highlightColour) {

        this.boardPadding = boardPadding;
        this.gap = gap;
        this.highlightSize = slotSize * 3 / 2;
        this.availableLocations = availableLocations;
        this.highlightColour = highlightColour;

    }

    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(highlightColour);

        int row;
        int column;

        for (int i = 0; i < availableLocations.size(); i++){
            row = availableLocations.get(i)[0];
            column =  availableLocations.get(i)[1];
            g2.fillRect(boardPadding + row * gap  - highlightSize / 2 , boardPadding + column * gap - highlightSize / 2, highlightSize , highlightSize); 
            
          

        }

    }












}