package Display;

import javax.swing.JLayeredPane;

public class MenuDisplay extends JLayeredPane {
    /**
     * Class constructor
     */
    public MenuDisplay(){
        super("9 Mans Morris");
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    public void createDisplay(Board board){
        System.out.println("Making board in display");

        //Work out values for the spacing
        int minSize = Math.min(frameWidth, frameHeight);
        int effectiveSize = minSize - boardPadding * 2;
        int gap = effectiveSize/6;
        int slotSize = (minSize-boardPadding)/20; //(Accessibility feature)

        //Create layers
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);
        layeredPaneSlots = new ButtonDisplay();
        layeredPaneHighlights = new SelectionHighlights(slotSize);

        layeredPaneSlots.createButtonDisplay(game, board.getPositions(), buttonLocations, boardPadding, gap, slotSize);
    
        //Add layers to the frame
        add(layeredPaneSlots);
        add(layeredPaneBackground);
        add(layeredPaneHighlights);
        
        //Set display sizes
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight));
        size = layeredPaneSlots.getPreferredSize();
        layeredPaneBackground.setSize(size);
        layeredPaneSlots.setSize(size);
        layeredPaneHighlights.setSize(size);

        repaint();
    }

    /**
     * Updates the display 
     * @param board the current state of the board
     */
    public void updateDisplay(Board board){
        for (Position pos : board.getPositions()) {
            if (pos.getPiece() != null) {
                pos.setBackground(pos.getPiece().getColour());
            }
            else {
                pos.setBackground(Color.white);
            }
        }
        repaint(); //A repaint is not normally triggered otherwise. There would be ghosting with the highlights
    }

    /**
     * Highlight an available location the selected piece can move 
     * @param possibleMoves the locations available
     * @param playerColour Colour of the player who's turn it is, for the correct highlight colour
     */
    public void displayPossibleMoves(ArrayList<Position> possibleMoves, Color playerColour){
        Color highLightcolour = new Color(
            playerColour.getRed(), 
            playerColour.getGreen(),
            playerColour.getBlue(),
            playerColour.getAlpha()*2/5
        );

        layeredPaneHighlights.addHighlights(possibleMoves, highLightcolour);
    }

    /**
     * Remove all highlights, calls the removeHighlights method in the highlight layer
     */
    public void removeHighlights(){
        layeredPaneHighlights.removeHighlights();
    }


    /**
     * Set window size of game relative to display
     */
    private void setWindowSize(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Height and Width calculated relative to the screen so the 
        // game is roughly the same size on most screens
        frameHeight = Math.max(minSize,(int)size.getHeight()*5/6);
        frameWidth = Math.max(minSize,frameHeight*13/14);

    }
}
