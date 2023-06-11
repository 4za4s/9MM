package Player.AI.NeuralNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import Board.Position;
import Game.Game;
import Jama.Matrix;
import Player.AI.AIMove;
import Player.AI.RandomValidMove;

//

/**
 * A neural network. This is used to create an AI for making moves
 * Inspiration taken from https://www.youtube.com/watch?v=VYQZ-kjP1ec
 */
public class NeuralNet implements AIMove {

    private int numInputs = 53; // Input is (gamestate, position AIs pieces, position opponent's pieces
    private int numOutputs = 24; // Output will be list of 24 positions and their values (positions)

    
    private Matrix inputLayer = new Matrix(1, numInputs);

    private ArrayList<Matrix> hiddenLayers = new ArrayList<Matrix>();

    // Will output values for all positions. Later go with the best of these
    private Matrix outputLayer = new Matrix(1, numOutputs);

    public ArrayList<Matrix> weights = new ArrayList<Matrix>(); //weights for the matrix

    public ArrayList<double[]> biases = new ArrayList<double[]>(); //biases for each neuron

    private float fitness = 0;

    private int hiddenLayerCount; //number of hidden layers in the NN
    private int hiddenNeuronCount; //number of neurons in each hidden layer

    /**
     * Constructor
     */
    public NeuralNet(int hiddenLayerCount, int hiddenNeuronCount) {
        createNetwork(hiddenLayerCount, hiddenNeuronCount);
    }

    /**
     * Constructor
     */
    public NeuralNet() {
        createNetwork(4, 43);
    }

    /**
     * Initialises neral network with a file to use as weights and biases
     * @param Filename name of output eg "output1" . DO NOT include .txt or a /
     */
    public NeuralNet(String Filename) {
        String url = "SavedNeuralNets/" + Filename + ".txt";
        try {
            BufferedReader input = new BufferedReader(new FileReader(url));
            String line = input.readLine();
            if (line.startsWith("Size:")) {
                line = input.readLine();
                String[] splitLine = line.split(", ");
                hiddenLayerCount = Integer.parseInt(splitLine[0]);
                hiddenNeuronCount = Integer.parseInt(splitLine[1]);
            }

            createNetwork(hiddenLayerCount, hiddenNeuronCount);

            line = input.readLine();
            if (line.startsWith("Weights:")) {
                for (int i = 0; i < hiddenLayerCount + 1; i++) {
                    line = input.readLine();
                    double[][] weight = parse2dArray(line);
                    Matrix matrix = new Matrix(weight);
                    weights.set(i, matrix);
                }
            }

            line = input.readLine();
            if (line.startsWith("Biases:")){
                for (int i = 0; i < hiddenLayerCount+1; i++) {
                    line = input.readLine();
                    double[] bias = parseArray(line);
                    biases.set(i, bias);
                }
            }
            input.close();

        } catch (Exception e) {
            System.err.println("Error loading custom Neural Network");
            createNetwork(4, 43);
        }
    }

    /**
     * Creates a neural network
     * @param hiddenLayerCount number of hidden layers in the NN
     * @param hiddenNeuronCount number of neurons in each hidden layer
     */
    private void createNetwork(int hiddenLayerCount, int hiddenNeuronCount) {
        this.hiddenLayerCount = hiddenLayerCount;
        this.hiddenNeuronCount = hiddenNeuronCount;

        if (hiddenLayerCount == 0) {
            Matrix inputToH1Weights = Matrix.random(numInputs, numOutputs); // so when multiplied gives size = to
            weights.add(inputToH1Weights); // next layer

            double[] H1Biases = new double[numOutputs];
            for (int j = 0; j < numOutputs; j++) {
                H1Biases[j] = 0.0;
            }

            biases.add(H1Biases);
            return;
        }

        Matrix hiddenLayer = new Matrix(hiddenNeuronCount, 1);
        hiddenLayers.add(hiddenLayer);

        Matrix inputToH1Weights = Matrix.random(numInputs, hiddenNeuronCount); // so when multiplied gives size = to
        weights.add(inputToH1Weights); // next layer

        double[] H1Biases = new double[hiddenNeuronCount];
        for (int j = 0; j < hiddenNeuronCount; j++) {
            H1Biases[j] = 0.0;
        }

        biases.add(H1Biases);

        // Add hidden layers + their connecting weights
        for (int i = 0; i < hiddenLayerCount-1; i++) {

            // Hidden layers
            hiddenLayer = new Matrix(hiddenNeuronCount, 1);
            hiddenLayers.add(hiddenLayer);

            // Hidden -> hidden
            Matrix HtoHWeights = Matrix.random(hiddenNeuronCount, hiddenNeuronCount);
            weights.add(HtoHWeights);

            // Hidden layer i biases
            double[] HiBiases = new double[hiddenNeuronCount];
            for (int j = 0; j < hiddenNeuronCount; j++) {
                HiBiases[j] = 0.0;
            }

            biases.add(HiBiases);
        }

        // Hidden -> Output
        Matrix HtoOutputWeights = Matrix.random(hiddenNeuronCount, numOutputs);
        weights.add(HtoOutputWeights);

        // Output Bias
        double[] OutputBiases = new double[numOutputs];
        for (int j = 0; j < numOutputs; j++) {
            OutputBiases[j] = 0.0;
        }

        biases.add(OutputBiases);
    }

    /**
     * Run the neural network
     * @param inputs input to run the NN on
     * @return list of position and their fitness chosen by the NN in order of fitness
     */
    public ArrayList<double[]> RunNetWork(Double inputs[]) {
        // Create inputLayer matrix
        for (int i = 0; i < inputs.length; i++) {
            inputLayer.set(0, i, inputs[i]);
        }



        hiddenLayers.set(0, sigmoidMatrix(addBias(inputLayer.times(weights.get(0)), biases.get(0))));

        // Run through rest of hidden layers
        for (int i = 1; i < hiddenLayers.size(); i++) {
            hiddenLayers.set(i, sigmoidMatrix(addBias(hiddenLayers.get(i - 1).times(weights.get(i)), biases.get(i))));
        }

        int index = hiddenLayers.size();

        outputLayer = sigmoidMatrix(addBias(hiddenLayers.get(index - 1).times((weights.get(index))), biases.get(index)));

        // Convert output matrix to a sorted list showing positions
        double[] outputLayerUnsorted = outputLayer.getArray()[0];

        ArrayList<double[]> outputLayerSorted = listToSortedArrayList(outputLayerUnsorted);

        return outputLayerSorted;

    }

    /**
     * Adds a bias to the matrix a 1x? matrix. Each row gets its own constant to add 
     */
    private Matrix addBias(Matrix m, double[] bias) {
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                m.set(i, j, m.get(i, j) + bias[j]);
            }
        }
        return m;
    }

    /**
     * 
     * @param listToSort
     * @return
     */
    public ArrayList<double[]> listToSortedArrayList(double[] listToSort) {

        ArrayList<double[]> arrayList = new ArrayList<double[]>();

        for (int i = 0; i < listToSort.length; i++) {
            arrayList.add(new double[] { listToSort[i], i });
        }

        Collections.sort(arrayList, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                return Double.compare(o2[0], o1[0]);
            }
        });


        return arrayList;
    }

     /**
      * Sigmoids all values of a matrix
      * @param m matrix to sigmoid
      * @return sigmoided matrix (NOTE: matrix given will be affected, returned value is not a deepcopy)
      */
    public Matrix sigmoidMatrix(Matrix m) {
        double[][] ar = m.getArray();

        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[i].length; j++) {
                m.set(i, j, sigmoid(ar[i][j]));
            }
        }
        return m;
    }

    /**
     * Get the sigmoid of a value
     */
    private double sigmoid(double input) {
        return 1 / (1 + Math.pow(Math.E, (-1 * input))); // https://stackoverflow.com/questions/33612029/sigmoid-function-of-a-2d-array

    }


    /**
     * Gets fittness
     */
    public float getFitness() {
        return fitness;
    }

    /**
     * Sets fittness
     */
    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    @Override
    public Position getMove(Game game) {
        Double inputs[] = new Double[numInputs];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = 0.0;
        }

        // Order is gamestate, selected piece, positions

        // Add gamestate to input
        switch (game.getGameState()) {
            case PLACING:
                inputs[0] = 1.0;
                break;
            case SELECTING:
                inputs[1] = 1.0;
                break;
            case MOVING:
                inputs[2] = 1.0;
                break;
            case FLYING:
                inputs[3] = 1.0;
                break;
            case TAKING:
                inputs[4] = 1.0;
                break;
            default:
                System.out.println("PLACEHOLDER ERROR");
        }

        // Add positions to input
        int index = 5;
        ArrayList<Position> positions = game.getBoard().getPositions();
        for (Position pos : positions) {
            if (pos.getPiece() == null) {
                inputs[index] = 0.0;

            } else if (pos.getPiece().getOwner() == game.getInTurnPlayer()) {
                inputs[index] = 1.0;
            } else {
                inputs[index+24] = 1.0;
            }
            index++;
        }

        ArrayList<double[]> output = RunNetWork(inputs);

        return getBestlegalPosition(output, game);
    }

    /**
     * Make sure ai will not continuously try to return the same invalid position
     * 
     * @return valid position
     */
    private Position getBestlegalPosition(ArrayList<double[]> outputs, Game game) {

        for (double out[] : outputs) {
            ArrayList<Position> possibleMoves = game.getBoard().getPossibleMoves(game.getGameState(),
                    game.getSelectedPiece(), game.getInTurnPlayer(), game.getNotInTurnPlayer());
            Position selectedPos = game.getBoard().getPositions().get((int) out[1]);
            if (possibleMoves.contains(selectedPos)) {
                return selectedPos;
            }
        }

        // If the neural network somehow fails to select a move return a random valid
        // move
        return new RandomValidMove().getMove(game);
    }

    /**
     * Saves the neural network to a file
     * @param Filename name of file to save to (not including path) 
     */
    public void save(String Filename) {
        String url = "SavedNeuralNets/" + Filename + ".txt";
        StringBuilder saveString = new StringBuilder();
        saveString.append("Size:\n");
        saveString.append(hiddenLayerCount + ", " + hiddenNeuronCount + "\n");

        saveString.append("Weights:\n");
        for (Matrix weightMatrix : weights) {
            for (double[] weight : weightMatrix.getArray()){
                saveString.append(Arrays.toString(weight));
                saveString.append(",");
            }
            saveString.append("\n");
        }

        saveString.append("Biases:\n");
        for (double[] bias : biases) {
            saveString.append(Arrays.toString(bias));
            saveString.append("\n");
        }

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(url));
            output.write(saveString.toString());
            output.close();
        } catch (Exception e) {
            System.err.println("Error saving custom Neural Network");
            System.err.println(e);
        }
    }

    public void save() {
        save("CustomNeuralNetwork");
    }

    /**
     * Get an 1d array of doubles from a string
     * @param string
     * @return
     */
    public double[] parseArray(String string){
        string = string.replace("[", "");
        string = string.replace("]", "");
        String[] stringArray = string.split(", ");
        double[] doubleArray = new double[stringArray.length];
        for (int i = 0; i < stringArray.length; i++){
            doubleArray[i] = Double.parseDouble(stringArray[i]);
        }
        return doubleArray;
    }

    /**
     * Get a 2d array of doubles from a string
     * @param string
     * @return 2d array
     */
    public double[][] parse2dArray(String string){
        String[] stringArray = string.split("],");
        double[][] doubleArray = new double[stringArray.length][];
        for (int i = 0; i < stringArray.length; i++){
            doubleArray[i] = parseArray(stringArray[i]);
        }
        return doubleArray;
    }
}
