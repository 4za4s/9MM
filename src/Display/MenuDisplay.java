package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout; // added code
import java.awt.Component; // added code
import javax.swing.SwingConstants;


public class MenuDisplay extends Display {

    private JButton start; //start game button
    private JButton neuralNetworkStart;
    private JLabel title; //game title
    private JLabel player1Title;
    private JLabel player2Title;
    private JPanel playerPanel;
    private JButton playerSelectionButton;
  

    /**
     * Class constructor
     */
    public MenuDisplay(int width, int height, Window window){
        super(width, height, window);
        start = new JButton("Start");
        start.setBackground(Color.white);

        neuralNetworkStart = new JButton("Start Training");
        neuralNetworkStart.setBackground(Color.white);

        title = new JLabel("Nine Man's Morris",SwingConstants.CENTER);
        title.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        title.setOpaque(true);
        title.setBackground(Color.white);

        player1Title = new JLabel("Player 1: ", SwingConstants.CENTER);

        player2Title = new JLabel("Player 2: ", SwingConstants.CENTER);

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS)); // added code

        add(playerPanel);

        playerPanel.add(player1Title);
        playerPanel.add(player2Title);

        String[] playerChoices = { "AI Player", "Human Player"};

        final JComboBox<String> playerSelections = new JComboBox<String>(playerChoices);

        playerSelections.setMaximumSize(playerSelections.getPreferredSize()); // added code
        playerSelections.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        //cb.setVisible(true); // Not needed
        playerPanel.add(playerSelections);

        playerSelectionButton = new JButton("OK");
        playerSelectionButton.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
        playerPanel.add(playerSelectionButton);

        playerPanel.setVisible(true); // added code
        
        start.addActionListener(e -> window.displayGame());
        // neuralNetworkStart.addActionListener(e -> start);

        add(start); 
        add(title);
        add(player1Title);
        add(player2Title);
        add(neuralNetworkStart);
    }

    @Override
    public void updateDisplay() {
        //Nothing to update here
    }

    @Override
    public void resizeDisplay(Dimension size) {
        setSize(size);
        int minDim = Math.min( size.width, size.height);
        int startButtonWidth = size.width/10;
        int startButtonHeight = size.height/20;
        int neuralNetworkStartWidth = size.width/10;
        int neuralNetworkStartHeight = size.height/20;
        int titleWidth = size.width*3/5;
        int titleHeight = size.height/15;
        int player1TitleWidth = size.width*3/5;
        int player1TitleHeight = size.height/20;
        int player2TitleWidth = size.width*3/5;
        int player2TitleHeight = size.height/20;
        int playerPanelWidth = size.width;
        int playerPanelHeight = size.height;

        //Set positions/sizes
        start.setBounds(size.width/2-startButtonWidth/2,
       (int) (size.height/(1.10)-startButtonHeight/(1.10)),
        (int) (startButtonWidth * (1.5)),
        (int) (startButtonHeight * (1.5)));
        start.setFont(new Font("Serif", Font.PLAIN, minDim/40));

        neuralNetworkStart.setBounds(size.width/20-neuralNetworkStartWidth/20,
       (int) (size.height/(1.10)-neuralNetworkStartHeight/(1.10)),
        (int) (neuralNetworkStartWidth * (2.5)),
        (int) (neuralNetworkStartHeight * (2.5)));
        neuralNetworkStart.setFont(new Font("Serif", Font.PLAIN, minDim/40));
        
        title.setBounds(size.width/2-titleWidth/2, 
        (int) (size.height/(11.5)-titleHeight/(12.5)), 
        titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, minDim/20));

        player1Title.setBounds(size.width/60-player1TitleWidth/4, 
        (int) (size.height/(3.5)-player1TitleHeight/(4.5)), 
        player1TitleWidth, player1TitleHeight);
        player1Title.setFont(new Font("Serif", Font.BOLD, minDim/20));

        player2Title.setBounds(size.width/60-player2TitleWidth/4, 
        (int) (size.height/(1.55)-player2TitleHeight/(2.55)), 
        player2TitleWidth, player2TitleHeight);
        player2Title.setFont(new Font("Serif", Font.BOLD, minDim/20));

        playerPanel.setBounds(player2TitleWidth, player2TitleHeight, playerPanelWidth, playerPanelHeight);
    }

//    private void demoButtonPressed() {
//        System.out.println("HELLO");
//    }

}
