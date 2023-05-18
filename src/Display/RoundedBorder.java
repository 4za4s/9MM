package Display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;

import javax.swing.border.Border;


// https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
public class RoundedBorder implements Border {

    private int radius;


    RoundedBorder(int radius) {
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color colour = g.getColor();
        g.setColor(Color.black);
        g.fillOval(x, y, width, height);
        g.setColor(colour);
        g.fillOval(x+2, y+2, width-4, height-4);
    }
}