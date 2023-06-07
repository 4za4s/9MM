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

public class MenuDisplay extends Display {

    private JButton start; //start game button
    private JButton neuralNetworkStart;
    private JLabel title; // game title
    private JLabel player1Title;
    private JLabel player2Title;
    private JComboBox<Player> player1Type;
    private JComboBox<Player> player2Type;
    private Player player1 = new AIPlayer(Color.blue, "Player Blue", new HeuristicMove());
    private Player player2 = new HumanPlayer(Color.green, "Player Green");

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

        Player[] player1Choices = {new HumanPlayer(Color.BLUE, "Player Blue"), new AIPlayer(Color.red, "Player Red", new HeuristicMove()), new AIPlayer(Color.green, "Player Green", new NeuralNet("NeuralNet1"))}; 
        player1Type = new JComboBox<Player>(player1Choices); 
        
        Player[] player2Choices = {new HumanPlayer(Color.CYAN, "Player Cyan"), new AIPlayer(Color.orange, "Player Orange", new HeuristicMove()), new AIPlayer(Color.magenta, "Player Magenta", new NeuralNet("NeuralNet1"))}; 
        player2Type = new JComboBox<Player>(player2Choices); 
        
        start.addActionListener(e -> startButtonPressed());
        neuralNetworkStart.addActionListener(e -> trainButtonPressed());

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
        int player1TypeWidth = size.width*2/5;
        int player1TypeHeight = size.height/20;
        int player2TypeWidth = size.width*2/5;
        int player2TypeHeight = size.height/20;

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

        player2Title.setBounds(size.width/60-player2TitleWidth/4, 
            (int) (size.height/(1.55)-player2TitleHeight/(2.55)), 
            player2TitleWidth, player2TitleHeight);
        player2Title.setFont(new Font("Serif", Font.BOLD, minDim/20));

        player1Type.setBounds(size.width/2-player1TypeWidth/2, 
            (int) (size.height/(3.43)-player1TypeHeight/(4.45)), 
            player1TypeWidth, player1TypeHeight);
        player1Type.setFont(new Font("Serif", Font.PLAIN, minDim/32));

        player2Type.setBounds(size.width/2-player2TypeWidth/2, 
            (int) (size.height/(1.53)-player2TypeHeight/(2.45)), 
            player2TypeWidth, player2TypeHeight);
        player2Type.setFont(new Font("Serif", Font.PLAIN, minDim/32));
        
    }

    /**
     * Start training a new neural network
     */
    private void trainButtonPressed() {
        TrainNeuralNet trainNeuralNet = new TrainNeuralNet(window);

        trainNeuralNet.start();
        System.out.println("Training a new neural network...");
    }

    private void startButtonPressed() {
        player1 = (Player) player1Type.getSelectedItem();
        player2 = (Player) player2Type.getSelectedItem();

        Game startGame = new Game(player1, player2);
        window.displayGame(startGame);
    }

}
