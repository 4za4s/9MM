package Player.AI.NeuralNetwork;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Board.Position;
import Game.Game;
import Jama.Matrix;
import Player.AI.AIMove;
import Player.AI.RandomValidMove;

//Inspiration taken from https://www.youtube.com/watch?v=VYQZ-kjP1ec&t=0s

//https://github.com/AJTech2002/Self-Driving-Car-Series/blob/master/Self%20Driving%20Car%20-%20Part%202%20Completed/Assets/NNet.cs
public class NeuralNet implements AIMove {

    private int numInputs = 30; // (gamestates + positions)
    private int numOutputs = 24; // (positions)

    // 1 column input + rows
    public Matrix inputLayer = new Matrix(1, numInputs); // TDO: make these package accessabl

    public ArrayList<Matrix> hiddenLayers = new ArrayList<Matrix>();

    // Will output values for all positions. Later go with the best of these
    public Matrix outputLayer = new Matrix(1, numOutputs);

    public ArrayList<Matrix> weights = new ArrayList<Matrix>();

    public ArrayList<double[]> biases = new ArrayList<double[]>();

    public float fitness = 0;

    public NeuralNet(int hiddenLayerCount, int hiddenNeuronCount) {
        createNetwork(hiddenLayerCount, hiddenNeuronCount);
    }

    public NeuralNet() {
        createNetwork(3, 5);
    }

    public NeuralNet(String Filename) {
        String url = "src/Assets/SavedNeuralNets/" + Filename + ".txt";
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(url));
            NeuralNet nn = (NeuralNet) input.readObject();
            this.numInputs = nn.numInputs;
            this.numOutputs = nn.numOutputs;
            this.inputLayer = nn.inputLayer;
            this.hiddenLayers = nn.hiddenLayers;
            this.outputLayer = nn.outputLayer;
            this.weights = nn.weights;
            this.biases = nn.biases;
            this.fitness = nn.fitness;
            input.close();
        } catch (Exception e) {
            System.out.println("Failed to load Neural Network");
            createNetwork(3, 5);
        }
    }

    private void createNetwork(int hiddenLayerCount, int hiddenNeuronCount) {

        Matrix hiddenLayer = new Matrix(hiddenNeuronCount, 1);
        hiddenLayers.add(hiddenLayer);

        Matrix inputToH1Weights = Matrix.random(numInputs, hiddenNeuronCount); // so when multiplied gives size = to
        weights.add(inputToH1Weights); // next layer

        double[] H1Biases = new double[hiddenNeuronCount];
        for (int j = 0; j < hiddenNeuronCount; j++) {
            H1Biases[j] = (double) (Math.random() - 0.5) * 2;
        }

        biases.add(H1Biases);

        // Add hidden layers + their connecting weights
        for (int i = 0; i < hiddenLayerCount; i++) {

            // Hidden layers
            hiddenLayer = new Matrix(hiddenNeuronCount, 1);
            hiddenLayers.add(hiddenLayer);

            // Hidden -> hidden
            Matrix HtoHWeights = Matrix.random(hiddenNeuronCount, hiddenNeuronCount);
            weights.add(HtoHWeights);

            // Hidden layer i biases
            double[] HiBiases = new double[hiddenNeuronCount];
            for (int j = 0; j < hiddenNeuronCount; j++) {
                HiBiases[j] = (double) (Math.random() - 0.5) * 2;
            }

            biases.add(HiBiases);
        }

        // Hidden -> Output
        Matrix HtoOutputWeights = Matrix.random(hiddenNeuronCount, numOutputs);
        weights.add(HtoOutputWeights);

        // Output Bias
        double[] OutputBiases = new double[numOutputs];
        for (int j = 0; j < hiddenNeuronCount; j++) {
            OutputBiases[j] = (double) (Math.random() - 0.5) * 2;
        }

        biases.add(OutputBiases);
    }

    public ArrayList<double[]> RunNetWork(Double inputs[]) {

        // Create inputLayer matrix
        for (int i = 0; i < inputs.length; i++) {
            inputLayer.set(0, i, inputs[i]);
        }

        // Set first hidden layer
        hiddenLayers.set(0, sigmoidMatrix(addBias(inputLayer.times((weights.get(0))), biases.get(0))));

        // Run through rest of hidden layers
        for (int i = 1; i < hiddenLayers.size(); i++) {
            hiddenLayers.set(i, sigmoidMatrix(addBias(hiddenLayers.get(i - 1).times((weights.get(i))), biases.get(i))));
        }

        int index = hiddenLayers.size();

        outputLayer = sigmoidMatrix(addBias(hiddenLayers.get(index - 1).times((weights.get(index))), biases.get(index)));

        // Convert output matrix to a sorted list showing positions
        double[] outputLayerUnsorted = outputLayer.getArray()[0];

        ArrayList<double[]> outputLayerSorted = listToSorrtedArrayList(outputLayerUnsorted);

        return outputLayerSorted;

    }

    private Matrix addBias(Matrix m, double[] bias) {
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                m.set(i, j, m.get(i, j) + bias[j]);
            }
        }
        return m;
    }

    public ArrayList<double[]> listToSorrtedArrayList(double[] listToSort) {

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
     * Adds a bias of b to matrix m
     */
    public Matrix biasMatrix(Matrix b, Matrix m) {
        return m.plus(b);
    }

    /**
     * Sigmoids all values of a matrix
     */
    public Matrix sigmoidMatrix(Matrix m) {
        double[][] ar = m.getArray();

        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[i].length; j++) {
                ar[i][j] = sigmoid(ar[i][j]);
            }
        }
        return m;
    }

    private double sigmoid(double input) {
        return 1 / (1 + Math.pow(Math.E, (-1 * input))); // https://stackoverflow.com/questions/33612029/sigmoid-function-of-a-2d-array

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
                // TODO; give error
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
                inputs[index] = -1.0;
            }
            index++;
        }

        ArrayList<double[]> output = RunNetWork(inputs);

        return getBestlegalPosition(output, game);
    }

    /**
     * Make sure ai will not continuously try to return the same invalid position
     * 
     * @return
     */
    private Position getBestlegalPosition(ArrayList<double[]> outputs, Game game) {
        System.out.println(outputs);
        for (double out[] : outputs) {
            System.out.println("" + out[0] + " " + out[1]);
        }

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

    public void save(String Filename) {
        String url = "src/Assets/SavedNeuralNets/" + Filename + ".txt";
        StringBuilder saveString = new StringBuilder();
        for (Matrix weight : weights) {
            saveString.append(weight.getArray()[0]);
            saveString.append("\n");
        }

        for (double[] bias : biases) {
            saveString.append(bias);
            saveString.append("\n");
        }

        System.out.println(saveString.toString());

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
}
