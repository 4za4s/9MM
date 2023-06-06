package Player.AI.NeuralNetwork;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Display.Window;
import Game.Game;
import Jama.Matrix;
import Player.AIPlayer;

public class TrainNeuralNet {
    private int popsize = 100;
    private ArrayList<NeuralNet> population = new ArrayList<NeuralNet>(popsize);

    private int bestNetWorkSelection = 20;
    private int WorstNetWorkSelection = 20;
    private int numberToCrossover = 40;

    private AIPlayer AIplayer1;
    private AIPlayer AIplayer2;
    private int player1Net = 0;
    private int player2Net = 1;

    private Game currentGame;
    private Window window;

    public TrainNeuralNet(Window window) {
        this.window = window;
        for (int i = 0; i < popsize; i++) {
            try {
                population.add(new NeuralNet("NeuralNet" + (i+1)));
            } catch (Exception e) {
                population.add(new NeuralNet());
            }
        }
    }

    /**
     * Start training
     */
    public void start() {
        AIplayer1 = new AIPlayer(Color.blue, "Player Blue", population.get(player1Net));
        AIplayer2 = new AIPlayer(Color.red, "Player Red", population.get(player2Net));
        currentGame = new Game(AIplayer1, AIplayer2, this);
        updateDisplay();
    }

    public void gameOver(Boolean stop) {
        if (stop){ 
            int i = 1;
            sortPop();
            for (NeuralNet pop : population) {
                pop.save("NeuralNet" + i);
                i++;
            }
            return;
        }

        player2Net++;
        if (player2Net >= popsize){
            player1Net++;
            player2Net = 0;
        }

        if (player1Net >= popsize){
            RePopulate();
            player1Net = 0;
            player2Net = 1;
        }

        AIplayer1 = new AIPlayer(Color.blue, "Player Blue", population.get(player1Net));
        AIplayer2 = new AIPlayer(Color.red, "Player Red", population.get(player2Net));

        currentGame = new Game(AIplayer1, AIplayer2, this);
        updateDisplay();
    }

    public void updateDisplay() {
        window.displayGame(currentGame);
    }

    public void sortPop(){
        Collections.sort(population, new Comparator<NeuralNet>() {
            @Override
            public int compare(NeuralNet nn1, NeuralNet nn2) {
                return nn1.getFitness() > nn2.getFitness() ? -1 : (nn1.getFitness() < nn2.getFitness()) ? 1 : 0;
            }
        });
    }

    private void RePopulate(){
        sortPop();
        ArrayList<NeuralNet> newPopulation = new ArrayList<NeuralNet>(popsize);
        for (int i = 0; i < bestNetWorkSelection; i++) {
            newPopulation.add(population.get(i));
        }
        for (int i = 0; i < WorstNetWorkSelection; i++) {
            newPopulation.add(population.get(popsize - i - 1));
        }
        for (int i = 0; i < numberToCrossover; i++) {
            NeuralNet A = population.get((int) (Math.random() * (bestNetWorkSelection + WorstNetWorkSelection)));
            NeuralNet B = population.get((int) (Math.random() * (bestNetWorkSelection + WorstNetWorkSelection)));

            ArrayList<Matrix> weights1 = A.weights;
            ArrayList<Matrix> weights2 = B.weights;
            ArrayList<Matrix> newWeights = new ArrayList<Matrix>(weights1.size());

            for (int j = 0; j < weights1.size(); j++) {
                Matrix newWeight = new Matrix(weights1.get(j).getRowDimension(), weights1.get(j).getColumnDimension());
                for (int k = 0; k < weights1.get(j).getRowDimension(); k++) {
                    for (int l = 0; l < weights1.get(j).getColumnDimension(); l++) {
                        if (Math.random() < 0.01) {
                            newWeight.set(k, l, Math.random());
                        } else if (Math.random() < 0.5) {
                            newWeight.set(k, l, weights1.get(j).get(k, l));
                        } else {
                            newWeight.set(k, l, weights2.get(j).get(k, l));
                        }
                    }
                }
                newWeights.add(newWeight);
            }

            ArrayList<double[]> biases1 = A.biases;
            ArrayList<double[]> biases2 = B.biases;
            ArrayList<double[]> newBiases = new ArrayList<double[]>(biases1.size());

            for (int j = 0; j < biases1.size(); j++) {
            double[] newBias = new double[biases1.get(j).length];
                for (int l = 0; l < biases1.get(j).length; l++) {
                    if (Math.random() < 0.01) {
                        newBias[j] = Math.random();
                    } else if (Math.random() < 0.5) {
                        newBias[j] = biases1.get(l)[j]
                    } else {
                        newBias[j] = Math.random();
                    }
            }
            newBiases.add(newBias);
        }


        }
        for (int i = newPopulation.size(); i < popsize; i++) {
            newPopulation.add(new NeuralNet());
        }
        population = newPopulation;
    }

    public void updateFitness(){
        int lost1 = AIplayer1.getNoOfPiecesLost();
        int lost2 = AIplayer2.getNoOfPiecesLost();

        population.get(player1Net).setFitness((9-lost1)/(9-lost2));
        population.get(player2Net).setFitness((9-lost2)/(9-lost1));
    }
}
