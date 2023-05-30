package Display;

import java.awt.Dimension;

import javax.swing.JLayeredPane;


/**
 * Generic form for displays
 */
public abstract class Display extends JLayeredPane {
    Window window;

    public Display(int width, int height, Window window){
        this.window = window;
        setPreferredSize(new Dimension(height, width));
    }

    /**
     * Update the contents of the display
     */
    public abstract void updateDisplay();

    /**
     * Resize the display and its contents
     * @param size size to change to
     */
    public abstract void resizeDisplay(Dimension size);

}
