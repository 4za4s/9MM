package Display;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Player.AI.NeuralNetwork.NeuralNet;
import Player.AI.NeuralNetwork.TrainNeuralNet;

public class MenuDisplay extends Display {

    public JButton start; // start game button
    private JButton neuralNetworkStart;
    private JLabel title; // game title
    private JLabel player1Title;
    private JLabel player2Title;

    /**
     * Class constructor
     */
    public MenuDisplay(int width, int height, Window window) {
        super(width, height, window);
        start = new JButton("Start");
        start.setBackground(Color.white);

        neuralNetworkStart = new JButton("Start Training");
        neuralNetworkStart.setBackground(Color.white);

        title = new JLabel("Nine Man's Morris", SwingConstants.CENTER);
        title.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        title.setOpaque(true);
        title.setBackground(Color.white);

        player1Title = new JLabel("Player 1: ", SwingConstants.CENTER);

        player2Title = new JLabel("Player 2: ", SwingConstants.CENTER);

        start.addActionListener(e -> window.displayGame());
        neuralNetworkStart.addActionListener(e -> trainButtonPressed());

        String[] player1Choices = { "AI Player", "Human Player" }; 
        final JComboBox<String> player1Type = new JComboBox<String>(player1Choices); 
        player1Type.setBounds(230, 245, 100*2, 100/3);

        String[] player2Choices = { "AI Player", "Human Player" }; 
        final JComboBox<String> player2Type = new JComboBox<String>(player2Choices); 
        player2Type.setBounds(230, 547, 100*2, 100/3);

        add(player1Type);
        add(player2Type);
        add(start); 
        add(title);
        add(player1Title);
        add(player2Title);
        add(neuralNetworkStart);
    }

    @Override
    public void updateDisplay() {
        // Nothing to update here
    }

    @Override
    public void resizeDisplay(Dimension size) {
        setSize(size);
        int minDim = Math.min(size.width, size.height);
        int startButtonWidth = size.width / 10;
        int startButtonHeight = size.height / 20;
        int neuralNetworkStartWidth = size.width / 10;
        int neuralNetworkStartHeight = size.height / 20;
        int titleWidth = size.width * 3 / 5;
        int titleHeight = size.height / 15;
        int player1TitleWidth = size.width * 3 / 5;
        int player1TitleHeight = size.height / 20;
        int player2TitleWidth = size.width * 3 / 5;
        int player2TitleHeight = size.height / 20;

        // Set positions/sizes
        start.setBounds(size.width / 2 - startButtonWidth / 2,
                (int) (size.height / (1.10) - startButtonHeight / (1.10)),
                (int) (startButtonWidth * (1.5)),
                (int) (startButtonHeight * (1.5)));
        start.setFont(new Font("Serif", Font.PLAIN, minDim / 40));

        neuralNetworkStart.setBounds(size.width / 20 - neuralNetworkStartWidth / 20,
                (int) (size.height / (1.10) - neuralNetworkStartHeight / (1.10)),
                (int) (neuralNetworkStartWidth * (2.5)),
                (int) (neuralNetworkStartHeight * (2.5)));
        neuralNetworkStart.setFont(new Font("Serif", Font.PLAIN, minDim / 40));

        title.setBounds(size.width / 2 - titleWidth / 2,
                (int) (size.height / (11.5) - titleHeight / (12.5)),
                titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, minDim / 20));

        player1Title.setBounds(size.width / 60 - player1TitleWidth / 4,
                (int) (size.height / (3.5) - player1TitleHeight / (4.5)),
                player1TitleWidth, player1TitleHeight);
        player1Title.setFont(new Font("Serif", Font.BOLD, minDim / 20));

        player2Title.setBounds(size.width / 60 - player2TitleWidth / 4,
                (int) (size.height / (1.55) - player2TitleHeight / (2.55)),
                player2TitleWidth, player2TitleHeight);
        player2Title.setFont(new Font("Serif", Font.BOLD, minDim / 20));

    }

    /**
     * Start training a new neural network
     */
    private void trainButtonPressed() {
        TrainNeuralNet trainNeuralNet = new TrainNeuralNet(window);

        trainNeuralNet.start();
        System.out.println("Training a new neural network...");
    }

}
