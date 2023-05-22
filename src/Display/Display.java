package Display;

import java.awt.Dimension;

import javax.swing.JLayeredPane;

public abstract class Display extends JLayeredPane {
    Window window;

    public Display(int width, int height, Window window){
        this.window = window;
        setPreferredSize(new Dimension(height, width));
    }

    public abstract void updateDisplay();

    public abstract void resizeDisplay(Dimension size);

}
