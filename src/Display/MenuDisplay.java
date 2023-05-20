package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuDisplay extends AbstractDisplay {
    private JButton start;
    private JLabel title;

    /**
     * Class constructor
     */
    public MenuDisplay(int width, int height, Window window){
        super(width, height, window);
        start = new JButton("Start");

        title = new JLabel("Nine Man's Morris",SwingConstants.CENTER);
        title.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        title.setOpaque(true);
        title.setBackground(Color.white);

        start.setBackground(Color.white);
        
        start.addActionListener(e -> window.displayGame());


        window.add(start); 
        window.add(title);
    }

    @Override
    public void updateDisplay() {
        //Nothing to update here
    }

    @Override
    public void resizeDisplay(Dimension size) {
        int minDim = Math.min( size.width, size.height);
        int startButtonWidth = size.width/10;
        int startButtonHeight = size.height/20;
        int titleWidth = size.width*3/5;
        int titleHeight = size.height/15;

        //Set positions/sizes
        start.setBounds(size.width/2-startButtonWidth /2,
        size.height/2-startButtonHeight/2,
        startButtonWidth,
        startButtonHeight);
        
        title.setBounds(size.width/2-titleWidth / 2, size.height/3 - titleHeight/2, titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, minDim/20));


        start.setFont(new Font("Serif", Font.PLAIN, minDim/60));
    }
}
