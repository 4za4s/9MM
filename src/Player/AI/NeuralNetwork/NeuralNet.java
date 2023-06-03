package Player.AI.NeuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class NeuralNet implements AIMove, Serializable {

    private int numInputs = 30; // (gamestates + positions)
    private int numOutputs = 24; // (positions)

    // 1 column input + rows
    public Matrix inputLayer = new Matrix(1, numInputs); // TDO: make these package accessabl

    public ArrayList<Matrix> hiddenLayers = new ArrayList<Matrix>();

    // Will output values for all positions. Later go with the best of these
    public Matrix outputLayerMatrix = new Matrix(1, numOutputs);

    public ArrayList<Matrix> weights = new ArrayList<Matrix>();

    public ArrayList<Double> biases = new ArrayList<Double>();

    public float fitness;

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
            this.outputLayerMatrix = nn.outputLayerMatrix;
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
        // Add hidden layers + their connecting weightw
        for (int i = 0; i < hiddenLayerCount + 1; i++) { // +1 to account for exiting layer

            // Hidden layers
            Matrix hiddenLayer = new Matrix(hiddenNeuronCount, 1);
            hiddenLayers.add(hiddenLayer);

            // Weights
            if (i == 0) {
                Matrix inputToH1 = Matrix.random(numInputs, hiddenNeuronCount); // so when multiplied gives size = to
                                                                                // next layer
                weights.add(inputToH1);
            }

            // Hidden -> hidden
            Matrix hiddenToHidden = Matrix.random(hiddenNeuronCount, hiddenNeuronCount);
            weights.add(hiddenToHidden);

            biases.add((Math.random() - 0.5) * 2); // get random value in range (-1, 1)
        }

        // Replace last weight with hidden -> output
        Matrix HLastToOutput = Matrix.random(hiddenNeuronCount, numOutputs); // so when multiplied gives size = to next
                                                                             // layer
        weights.add(HLastToOutput);
    }

    public ArrayList<double[]> RunNetWork(Double inputs[]) {

        // Create inputLayer matrix
        for (int i = 0; i < inputs.length; i++) {
            inputLayer.set(0, i, inputs[i]);
        }

        inputLayer = sigmoidMatrix(inputLayer);

        // Set first hidden layer
        hiddenLayers.set(0, sigmoidMatrix(biasMatrix(biases.get(0), inputLayer.times((weights.get(0)))))); // TODO: add
                                                                                                           // bias

        // Run through rest of hidden layers
        for (int i = 1; i < hiddenLayers.size(); i++) {
            hiddenLayers.set(i,
                    sigmoidMatrix(biasMatrix(biases.get(i), (hiddenLayers.get(i - 1).times(weights.get(i))))));

        }

        outputLayerMatrix = sigmoidMatrix(biasMatrix(biases.get(biases.size() - 1),
                hiddenLayers.get(hiddenLayers.size() - 1).times(weights.get(weights.size() - 1))));

        // Convert output matrix to a sorted list showing positions
        double[] outputLayerUnsorted = outputLayerMatrix.getArray()[0];

        ArrayList<double[]> outputLayerSorted = listToSorrtedArrayList(outputLayerUnsorted);

        // System.out.println("Output matrix");
        // outputLayerMatrix.print(3,2);

        // System.out.println("Top value");
        // System.out.println("Value = " + outputLayerSorted.get(0)[0] + " position = "
        // + outputLayerSorted.get(0)[1]);

        return outputLayerSorted;

    }

    public ArrayList<double[]> listToSorrtedArrayList(double[] listToSort) {

        ArrayList<double[]> arrayList = new ArrayList<double[]>();

        for (int i = 0; i < listToSort.length; i++) {
            arrayList.add(new double[] { listToSort[i], i });
        }

        Collections.sort(arrayList, Comparator.comparingDouble(arr -> arr[0]));

        return arrayList;
    }

    /**
     * Adds a bias of b to matrix m
     */

    public Matrix biasMatrix(Double b, Matrix m) {

        Matrix biasMatrix = new Matrix(m.getRowDimension(), m.getColumnDimension(), 1.0);

        return m.plus(biasMatrix);

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
        for (double out[]: outputs){
            System.out.println("" + out[0] + " " + out[1]);
            ArrayList<Position> possibleMoves = game.getBoard().getPossibleMoves(game.getGameState(), game.getSelectedPiece(), game.getInTurnPlayer(), game.getNotInTurnPlayer());
            Position selectedPos = game.getBoard().getPositions().get((int) out[1]);
            if (possibleMoves.contains(selectedPos)){
                return selectedPos;
            }
        }

        // If the neural network somehow fails to select a move return a random valid move
        return new RandomValidMove().getMove(game);
    }

    public void save(String Filename){
        String url = "src/Assets/SavedNeuralNets/"+Filename + ".txt";
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(url));
            output.writeObject(this);
            output.close();
        } catch (Exception e) {
            System.err.println("Error saving custom Neural Network");
            System.err.println(e);
        }
    }

    public void save(){
        String url = "src/Assets/SavedNeuralNets/default.txt";
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(url));
            output.writeObject(this);
            output.close();
        } catch (Exception e) {
            System.err.println("Error saving Neural Network");
            System.err.println(e);
        }
    }
}
