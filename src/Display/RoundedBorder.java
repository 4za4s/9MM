package Display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;


/**
 * Aloows for the positions to be round when they are displayed. Made using
 * advice from: https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
 */
public class RoundedBorder implements Border {

    private int radius; //radius of positions


    /**
     * Constructor
     */
    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color colour = g.getColor();
        g.setColor(Color.black);
        g.fillOval(x, y, width, height);
        g.setColor(colour);
        g.fillOval(x+2, y+2, width-4, height-4);
    }
}