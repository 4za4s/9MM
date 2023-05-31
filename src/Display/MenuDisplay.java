package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuDisplay extends Display {

    private JButton start; //start game button
    private JLabel title; //game title
    private JButton demo;

    /**
     * Class constructor
     */
    public MenuDisplay(int width, int height, Window window){
        super(width, height, window);
        start = new JButton("Start");
        demo = new JButton("DEMO TEXT!");

        title = new JLabel("Nine Man's Morris",SwingConstants.CENTER);
        title.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        title.setOpaque(true);
        title.setBackground(Color.white);

        start.setBackground(Color.white);
        
        start.addActionListener(e -> window.displayGame());
        demo.addActionListener(e -> demoButtonPressed());


        window.add(start); 
        window.add(title);
        window.add(demo);
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
        // int demoWidth = size.width/10;
        // int demoHeight = size.height/20;

        //Set positions/sizes
        start.setBounds(size.width/2-startButtonWidth /2,
       (int) (size.height/(1.10)-startButtonHeight/(1.10)),
        (int) (startButtonWidth * (1.5)),
        (int) (startButtonHeight * (1.5)));
        start.setFont(new Font("Serif", Font.PLAIN, minDim/40));
        // demo.setBounds(size.width/1-demoWidth/2, size.height/3-demoHeight, demoWidth, demoHeight);
        // demo.setFont(new Font("Serif", Font.PLAIN, minDim/60));
        
        title.setBounds(size.width/2-titleWidth/2, 
        (int) (size.height/(11.5)-titleHeight/(12.5)), 
        titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, minDim/20));


        
    }

    private void demoButtonPressed() {
        System.out.println("HELLO");
    }
}
