package Display;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Display extends JFrame{

    /**
     * Class constructor
     */
    public Display(){
        super("9 Mans Morris");

        //Frame settings
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window
        getContentPane().setBackground(backgroundColor);
        setWindowSize();
        pack();
        setSize(new Dimension(frameWidth, frameHeight));
        setVisible(true);
        setLayout(null); //try setLayout(new GridLayout()); if bored
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    public void createDisplay(Board board){
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
