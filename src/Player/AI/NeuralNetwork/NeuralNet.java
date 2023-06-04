package Player.AI.NeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Board.Position;
import Game.Game;
import Jama.Matrix;
import Player.AI.AIMove;


//Inspiration taken from https://www.youtube.com/watch?v=VYQZ-kjP1ec&t=0s

//https://github.com/AJTech2002/Self-Driving-Car-Series/blob/master/Self%20Driving%20Car%20-%20Part%202%20Completed/Assets/NNet.cs
public class NeuralNet implements AIMove{

    private int numInputs = 27;  //  (gamestate + selected piece row then column + positions)
    private int numOutputs = 24;
    
  
    //1 column input + rows 
    public Matrix inputLayer = new Matrix(1,numInputs); //TDO: make these package accessabl

    public ArrayList<Matrix> hiddenLayers = new ArrayList<Matrix>();

    // Will output values for all positions. Later go with the best of these
    public Matrix outputLayerMatrix = new Matrix(1,numOutputs); 
    public ArrayList<double[]> outputLayerSorted; 


    public ArrayList<Matrix> weights = new ArrayList<Matrix>();

    public ArrayList<Double> biases = new ArrayList<Double>();

    public float fitness;

    private int lastIndexReturned = 0; 
    private double lastTopValue =  5.0; //random value that will enver naturally happen
    private double nextLastTopValue = 5.0; 

    Game game;


    public NeuralNet(int hiddenLayerCount, int hiddenNeuronCount){

        //Add hidden layers + their connecting weightw
        for (int i = 0; i < hiddenLayerCount + 1; i++ ){ //+1 to account for exiting layer


            //Hidden layers
            Matrix hiddenLayer = new Matrix( hiddenNeuronCount, 1);
            hiddenLayers.add(hiddenLayer);


            //Weights
            if (i == 0){
                Matrix inputToH1 = Matrix.random(numInputs, hiddenNeuronCount); //so when multiplied gives size = to next layer
                weights.add(inputToH1);
            } 
            

            // Hidden -> hidden
            Matrix hiddenToHidden = Matrix.random(hiddenNeuronCount,hiddenNeuronCount);
            weights.add(hiddenToHidden); 

            biases.add((Math.random()-0.5)*2); //get random value in range (-1, 1)
        }

        //Replace last weight with  hidden -> output
        Matrix HLastToOutput = Matrix.random(hiddenNeuronCount, numOutputs); //so when multiplied gives size = to next layer
        weights.add(HLastToOutput);
    }

    public void RunNetWork(ArrayList<Double> inputs){

        //Create inputLayer matrix
        for (int i = 0; i <  inputs.size(); i++){
            inputLayer.set(0, i,inputs.get(i));
        }

        inputLayer = sigmoidMatrix(inputLayer);


        //Set first hidden layer
        hiddenLayers.set(0, sigmoidMatrix(biasMatrix(biases.get(0),inputLayer.times((weights.get(0)))))); //TODO: add bias


        //Run through rest of hidden layers
        for (int i = 1; i < hiddenLayers.size(); i++){
            hiddenLayers.set(i, sigmoidMatrix(biasMatrix(biases.get(i), (hiddenLayers.get(i-1).times(weights.get(i))))));


        }

        outputLayerMatrix = sigmoidMatrix(biasMatrix(biases.get(biases.size()-1), hiddenLayers.get(hiddenLayers.size()-1).times(weights.get(weights.size()-1))));

        //Convert output matrix to a sorted list showing positions
        double [] outputLayerUnsorted = outputLayerMatrix.getArray()[0];

        outputLayerSorted = listToSorrtedArrayList(outputLayerUnsorted);

        
        // System.out.println("Output matrix");
        // outputLayerMatrix.print(3,2);

        // System.out.println("Top value");
        // System.out.println("Value = " + outputLayerSorted.get(0)[0] + " position = " +  outputLayerSorted.get(0)[1]);


    }

    public ArrayList<double[]> listToSorrtedArrayList(double[] listToSort){

        ArrayList<double[]> arrayList = new ArrayList<double[]>();

        for (int i =0; i < listToSort.length; i++){
            arrayList.add(new double[]{listToSort[i],i});
        }

        Collections.sort(arrayList, Comparator.comparingDouble(arr -> arr[1]));

        
        return arrayList;
    }



    /**
     * Adds a bias of b to matrix m
     */

    public Matrix biasMatrix(Double b, Matrix m){

        Matrix biasMatrix = new Matrix(m.getRowDimension(), m.getColumnDimension(), 1.0);

        return m.plus(biasMatrix);


    }
    /**
     * Sigmoids all values of a matrix
     */
    public Matrix sigmoidMatrix(Matrix m){
        double[][] ar = m.getArray();


        for (int i = 0; i < ar.length; i++){
            for (int j = 0; j< ar[i].length; j++){
                ar[i][j] = sigmoid(ar[i][j]);
            }
        }
        return m;
    }


    private double sigmoid(double input){
        return 1 / (1 + Math.pow(Math.E, (-1 *  input))); //https://stackoverflow.com/questions/33612029/sigmoid-function-of-a-2d-array

    }

    @Override
    public Position getMove(Game game) {
        this.game = game;
        
        ArrayList<Double> inputs = new ArrayList<Double>();
        //Order is gamestate, selected piece, positions 
        //TODO: hopefully positions won't massively outweight the gamestate and piece

        //Add gaemstate to input
        Double toadd;
        switch (game.getGameState()){

            case PLACING:
                toadd = 0.0;
                break;

            case SELECTING: 
                toadd = 0.2;
                break;
            case MOVING:
                toadd = 0.4;
                break;
            case FLYING:
                toadd = 0.6;
                break;
            case TAKING:
                toadd = 0.8;
                break;
            default:
                toadd = 0.8;
            
                System.out.println("PLACEHOLDER ERROR");
                //TODO; give error

        }
        inputs.add(toadd);

        //Add selected piece to input
        if (game.getSelectedPiece() == null){ //If no piecce selected give 0,0
            inputs.add(0.0); 
            inputs.add(0.0);

        } else { //Otherwise give (0,1) range
            inputs.add(sigmoid((double) game.getSelectedPiece().getPosition().getRow() + 5));
            inputs.add(sigmoid((double) game.getSelectedPiece().getPosition().getColumn() + 5));

        }
       


        //Add positions to input
        ArrayList<Position> positions = game.getBoard().getPositions();
        for (Position pos : positions){
            if (pos.getPiece() == null){
                inputs.add(0.0);

            } else if (pos.getPiece().getOwner() == game.getInTurnPlayer()){
                inputs.add(0.5);
            } else {
                inputs.add(1.0);
            }


        }


        RunNetWork(inputs);

        return getNextPosition();
    }

    /**
     * Make sure ai will not continuously try to return the same invalid position
     * @return
     */
    private Position getNextPosition(){

        double topValue = outputLayerSorted.get(0)[0];

        //if ai is chosing same invalid spot choose ai's next best positon
        if (topValue == lastTopValue || topValue == nextLastTopValue){

            //90% chance to do a random move
            double random = Math.random();
            if (random < 0.9){
                int randomMove = (int)  (Math.random()*game.getBoard().getPositions().size());
                return game.getBoard().getPositions().get(randomMove);

            } else {
                //10% chance to do next best move
                lastIndexReturned = (++lastIndexReturned) % outputLayerSorted.size();
                return game.getBoard().getPositions().get((int) outputLayerSorted.get(lastIndexReturned)[1]);


            }




        } else { //Return ai's best value
            lastIndexReturned = 0;
            nextLastTopValue = lastTopValue;
            lastTopValue = topValue;
            return game.getBoard().getPositions().get((int) outputLayerSorted.get(0)[0]);
        }

        

        

    }

    
}

