package Display;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Board.Position;


/* Creates a drawing of selection highlights. This drawing has to be added to a panel to be viewed 
 * Highlights all locations in a list given
*/
class SelectionHighlights extends JLayeredPane { //prev JPanel
    private int highlightSize; //how large to make highlighting
    private ArrayList<Position> availableLocations = new ArrayList<Position>(); //locations a piece or empty slot should be
    private Color highlightColour; //highlight color to show available locations
    private int offset;

    /**
     * Class constructor
     */
    public SelectionHighlights(int slotSize) {
        this.highlightSize = slotSize * 3 / 2;
        this.offset = (highlightSize - slotSize)/2;
    }


    public void addHighlights(ArrayList<Position> availableLocations, Color highlightColour){
        this.availableLocations = availableLocations;
        this.highlightColour = highlightColour;
    }

    public void removeHighlights() {
        this.availableLocations = new ArrayList<Position>();
    }
    
    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(highlightColour);

        for (Position pos : availableLocations){
            g2.fillRect(pos.getX()-offset, pos.getY()-offset, highlightSize, highlightSize); 
        }
    }
}