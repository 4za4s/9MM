package Display;

import java.awt.Dimension;

import javax.swing.JLayeredPane;

public abstract class AbstractDisplay extends JLayeredPane {
    Window window;

    public AbstractDisplay(int Width, int Height, Window window){
        this.window = window;
        setPreferredSize(new Dimension(Height, Width));
    }

    public abstract void updateDisplay();

    public abstract void resizeDisplay(Dimension size);

}
