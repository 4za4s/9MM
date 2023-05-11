package Display;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Board.Position;


/* Creates a drawing of selection highlights. This drawing has to be added to a panel to be viewed 
 * Highlights all locations in a list given
*/
class SelectionHighlights extends JLayeredPane implements ResizableDisplay{
    private int highlightSize; //how large to make highlighting
    private ArrayList<Position> availableLocations = new ArrayList<Position>(); //locations to highlight, represents possible moves for a piece
    private Color highlightColour; //highlight color to show available locations
    private int offset;
    int slotSize;

    /**
     * Class constructor
     */
    public SelectionHighlights(Dimension size) {
        setBoardElementsSize( size);
 
    }

    /**
     * Adds highlights to the givel locations
     * @param availableLocations locations to highlight, possible moves for a piece
     * @param highlightColour colour of the player who's move it is
     */
    public void addHighlights(ArrayList<Position> availableLocations, Color highlightColour){
        this.availableLocations = availableLocations;
        this.highlightColour = highlightColour;
    }

    /**
     * Removes all highlights
     * Panel will get redrawn after the action and this will make sure no highlights are drawn
     */
    public void removeHighlights() {
        this.availableLocations = new ArrayList<Position>();
    }
    
    /**
     * Draws the highlights, called when the panel is drawn
     * Part of the JLayeredPane class
     */
    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(highlightColour);

        for (Position pos : availableLocations){
            g2.fillRect(pos.getX()-offset, pos.getY()-offset, highlightSize, highlightSize); 
        }
    }

    //TODO: this is semi duplicate code
    private void setBoardElementsSize(Dimension size){
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        int minDim = Math.min(width, height);
        int boardPadding = (minDim * 8 ) / 40;
    
       

        slotSize = (minDim - boardPadding) / 20;
        highlightSize = (slotSize * 3) / 2;
        offset = (highlightSize - slotSize)/2;
      
    }


    @Override
    public void resizeDisplay(Dimension size) {
        setBoardElementsSize( size);
    }

}