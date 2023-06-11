package Display;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Player.AIPlayer;
import Player.HumanPlayer;
import Player.Player;
import Player.AI.HeuristicMove;
import Player.AI.NeuralNetwork.NeuralNet;
import Player.AI.NeuralNetwork.TrainNeuralNet;
import Game.Game;

/**
 * Displays the pre-game menu
 */
public class MenuDisplay extends Display {

    private JButton start; // start game button
    private JLabel title; // game title
    private JButton neuralNetworkStart;
    private JLabel player1Title;
    private JLabel player2Title;
    private JLabel player1ColourLabel;
    private JLabel player2ColourLabel;
    private JComboBox<Player> player1Type;
    private JComboBox<Player> player2Type;
    private JComboBox<NamedColour> player1ColourType;
    private JComboBox<NamedColour> player2ColourType;
    private Player player1;
    private Player player2;

    /**
     * Class constructor
     */
    public MenuDisplay(int width, int height, Window window) {
        super(width, height, window);
        start = new JButton("Start");
        start.setBackground(Color.white);

        neuralNetworkStart = new JButton("Train AI");
        neuralNetworkStart.setBackground(Color.white);

        title = new JLabel("Nine Man's Morris", SwingConstants.CENTER);
        title.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        title.setOpaque(true);
        title.setBackground(Color.white);

        player1Title = new JLabel("Player 1: ", SwingConstants.CENTER);
        player2Title = new JLabel("Player 2: ", SwingConstants.CENTER);

        player1ColourLabel = new JLabel("Colour: ", SwingConstants.CENTER);
        player2ColourLabel = new JLabel("Colour: ", SwingConstants.CENTER);

        Player[] player1Choices = { 
                new HumanPlayer(),
                new AIPlayer(new HeuristicMove()),
                new AIPlayer(new NeuralNet("TrainedNeuralNet")),
                new AIPlayer(new NeuralNet("YourNeuralNetWork")) 
            };

        player1Type = new JComboBox<Player>(player1Choices);

        Player[] player2Choices = { 
                new HumanPlayer(),
                new AIPlayer(new HeuristicMove()),
                new AIPlayer(new NeuralNet("TrainedNeuralNet2")),
                new AIPlayer(new NeuralNet("YourNeuralNetWork"))  
            };

        player2Type = new JComboBox<Player>(player2Choices);

        NamedColour[] playerColours = { NamedColour.BLUE, NamedColour.RED, NamedColour.YELLOW, NamedColour.MAROON,
                NamedColour.GREEN, NamedColour.PURPLE, NamedColour.LIGHT_SLATE_GREY, NamedColour.MAGENTA,
                NamedColour.BLACK, NamedColour.WHITE };

        player1ColourType = new JComboBox<NamedColour>(playerColours);

        player2ColourType = new JComboBox<NamedColour>(playerColours);
        player2ColourType.setSelectedIndex(1);

        start.addActionListener(e -> startButtonPressed());
        neuralNetworkStart.addActionListener(e -> trainButtonPressed());

        add(player1Type);
        add(player2Type);
        add(start);
        add(title);
        add(player1Title);
        add(player2Title);
        add(player1ColourLabel);
        add(player2ColourLabel);
        add(neuralNetworkStart);
        add(player1ColourType);
        add(player2ColourType);
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

        int playerTitleWidth = size.width * 3 / 5;
        int playerTitleHeight = size.height / 20;

        int playerTypeWidth = size.width * 2 / 5;
        int playerTypeHeight = size.height / 20;


        int playerColourLabelWidth = size.width * 3 / 5;
        int playerColourLabelHeight = size.height / 20;

        int playerColourTypeWidth = size.width * 2 / 5;
        int playerColourTypeHeight = size.height / 20;

        Font dropDownIdentifierFont = new Font("Serif", Font.BOLD, minDim / 25);
        Font dropDownMenuFont = new Font("Serif", Font.PLAIN, minDim / 45);

        // Set positions/sizes
        start.setBounds(size.width / 2 - startButtonWidth / 2,
                (int) (size.height / (1.10) - startButtonHeight / (1.10)),
                (int) (startButtonWidth * (1.5)),
                (int) (startButtonHeight * (1.5)));
        start.setFont(new Font("Serif", Font.PLAIN, minDim / 40));

        neuralNetworkStart.setBounds(size.width / 20 - neuralNetworkStartWidth / 20,
                (int) (size.height / (1.10) - neuralNetworkStartHeight / (1.10)),
                (int) (neuralNetworkStartWidth * (1.5)),
                (int) (neuralNetworkStartHeight * (1.5)));
        neuralNetworkStart.setFont(new Font("Serif", Font.PLAIN, minDim / 40));

        title.setBounds(size.width / 2 - titleWidth / 2,
                (int) (size.height / (11.5) - titleHeight / (12.5)),
                titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, minDim / 20));

        player1Title.setBounds(size.width / 60 - playerTitleWidth / 4,
                (int) (size.height / (3.5) - playerTitleHeight / (4.5)),
                playerTitleWidth, playerTitleHeight);
        player1Title.setFont(dropDownIdentifierFont);

        player2Title.setBounds(size.width / 60 - playerTitleWidth / 4,
                (int) (size.height / (1.55) - playerTitleHeight / (2.55)),
                playerTitleWidth, playerTitleHeight);
        player2Title.setFont(dropDownIdentifierFont);

        player1ColourLabel.setBounds(size.width / 60 - playerColourLabelWidth / 4,
                (int) (size.height / (2.71) - playerColourLabelHeight / (4.5)),
                playerColourLabelWidth, playerColourLabelHeight);
        player1ColourLabel.setFont(dropDownIdentifierFont);

        player2ColourLabel.setBounds(size.width / 60 - playerColourLabelWidth / 4,
                (int) (size.height / (1.36) - playerColourLabelHeight / (2.55)),
                playerColourLabelWidth, playerColourLabelHeight);
        player2ColourLabel.setFont(dropDownIdentifierFont);

        player1Type.setBounds(size.width / 2 - playerTypeWidth / 2,
                (int) (size.height / (3.43) - playerTypeHeight / (4.45)),
                playerTypeWidth, playerTypeHeight);
        player1Type.setFont(dropDownMenuFont);

        player2Type.setBounds(size.width / 2 - playerTypeWidth / 2,
                (int) (size.height / (1.53) - playerTypeHeight / (2.45)),
                playerTypeWidth, playerTypeHeight);
        player2Type.setFont(dropDownMenuFont);

        player1ColourType.setBounds(size.width / 2 - playerColourTypeWidth / 2,
                (int) (size.height / (2.53) - playerColourTypeHeight / (1.45)),
                playerColourTypeWidth, playerColourTypeHeight);
        player1ColourType.setFont(dropDownMenuFont);

        player2ColourType.setBounds(size.width / 2 - playerColourTypeWidth / 2,
                (int) (size.height / (1.33) - playerColourTypeHeight / (1.45)),
                playerColourTypeWidth, playerColourTypeHeight);
        player2ColourType.setFont(dropDownMenuFont);

    }

    /**
     * Start training a new neural network
     */
    private void trainButtonPressed() {
        TrainNeuralNet trainNeuralNet = new TrainNeuralNet(window);

        trainNeuralNet.start();
        System.out.println("Training a new neural network...");
    }

    /**
     * Initialises a game
     */
    private void startButtonPressed() {
        player1 = (Player) player1Type.getSelectedItem();
        player2 = (Player) player2Type.getSelectedItem();

        player1.setColour((NamedColour) player1ColourType.getSelectedItem());
        player2.setColour((NamedColour) player2ColourType.getSelectedItem());

        Game startGame = new Game(player1, player2);
        window.displayGame(startGame);
    }

}
