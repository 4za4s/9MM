package Player.AI.NeuralNetwork;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Display.Window;
import Game.Game;
import Jama.Matrix;
import Player.AIPlayer;
import Player.Player;

public class TrainNeuralNet {
    private int popsize = 50;
    private ArrayList<NeuralNet> population = new ArrayList<NeuralNet>(popsize);

    private int bestNetWorkSelection = 10;
    private int WorstNetWorkSelection = 10;
    private int numberToCrossover = 10;
    private int numberToMutate = 10;

    private AIPlayer AIplayer1;
    private AIPlayer AIplayer2;
    private int player1Net = 0;
    private int player2Net = 1;

    private float mutateRate = 0.05f;

    private Game currentGame;
    private Window window;

    public TrainNeuralNet(Window window) {
        this.window = window;
        for (int i = 0; i < popsize; i++) {
            try {
                population.add(new NeuralNet("NeuralNet" + (i + 1)));
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

    public void gameOver(Boolean stop, Player winner, int turn) {
        if (stop) {
            int i = 1;
            sortPop();
            for (NeuralNet pop : population) {
                pop.save("NeuralNet" + i);
                i++;
            }
            return;
        }

        updateFitness(winner, turn);

        player2Net++;
        if (player2Net >= popsize) {
            player1Net++;
            player2Net = 0;
        }

        if (player1Net >= popsize) {
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

    public void sortPop() {
        Collections.sort(population, new Comparator<NeuralNet>() {
            @Override
            public int compare(NeuralNet nn1, NeuralNet nn2) {
                return nn1.getFitness() > nn2.getFitness() ? -1 : (nn1.getFitness() < nn2.getFitness()) ? 1 : 0;
            }
        });

        for (int i = 0; i < popsize; i++) {
            System.out.println(population.get(i).getFitness());
        }
    }

    private void RePopulate() {
        sortPop();
        ArrayList<NeuralNet> newPopulation = new ArrayList<NeuralNet>(popsize);
        for (int i = 0; i < bestNetWorkSelection; i++) {
            newPopulation.add(population.get(i));
        }

        for (int i = 0; i < WorstNetWorkSelection; i++) {
            newPopulation.add(population.get(popsize - i - 1));
        }

        for (int i = 0; i < numberToCrossover; i++) {
            NeuralNet Parent1 = population.get((int) (Math.random() * (bestNetWorkSelection + WorstNetWorkSelection)));
            NeuralNet Parent2 = population.get((int) (Math.random() * (bestNetWorkSelection + WorstNetWorkSelection)));

            newPopulation.add(crossOver(Parent1, Parent2));
        }

        for (int i = 0; i < numberToMutate; i++) {
            NeuralNet Parent1 = population.get(i);
            newPopulation.add(mutate(Parent1));
        }

        for (int i = newPopulation.size(); i < popsize; i++) {
            newPopulation.add(new NeuralNet());
        }
        population = newPopulation;

        for (NeuralNet pop : population) {
            pop.setFitness(0);
        }
    }

    public void updateFitness(Player winner, int turn) {
        int lost1 = AIplayer1.getNoOfPiecesLost();
        int lost2 = AIplayer2.getNoOfPiecesLost();

        float score1 = population.get(player1Net).getFitness() + ((9 - lost1)*5 / (8 - lost2))*10;
        float score2 = population.get(player2Net).getFitness() + ((9 - lost2)*5 / (8 - lost1))*10;

        if (winner == AIplayer1) {
            score1 += 100;
        } else if (winner == AIplayer2) {
            score2 += 100;
        }
        
        population.get(player1Net).setFitness(score1);
        population.get(player2Net).setFitness(score2);

        for (int i = 0; i < popsize; i++) {
            System.out.println(i+1 + ": " + population.get(i).getFitness());
        }
    }

    public NeuralNet crossOver(NeuralNet Parent1, NeuralNet Parent2) {
        NeuralNet child = new NeuralNet();
        ArrayList<Matrix> weights1 = Parent1.weights;
        ArrayList<Matrix> weights2 = Parent2.weights;
        ArrayList<Matrix> newWeights = new ArrayList<Matrix>(weights1.size());

        for (int j = 0; j < weights1.size(); j++) {
            Matrix newWeight = new Matrix(weights1.get(j).getRowDimension(), weights1.get(j).getColumnDimension());
            for (int k = 0; k < weights1.get(j).getRowDimension(); k++) {
                for (int l = 0; l < weights1.get(j).getColumnDimension(); l++) {
                    if (Math.random() < 0.01) {
                        newWeight.set(k, l, (Math.random() - 0.5) * 2);
                    } else if (Math.random() < 0.5) {
                        newWeight.set(k, l, weights1.get(j).get(k, l));
                    } else {
                        newWeight.set(k, l, weights2.get(j).get(k, l));
                    }
                }
            }
            newWeights.add(newWeight);
        }

        ArrayList<double[]> biases1 = Parent1.biases;
        ArrayList<double[]> biases2 = Parent2.biases;
        ArrayList<double[]> newBiases = new ArrayList<double[]>(biases1.size());

        for (int j = 0; j < biases1.size(); j++) {
            double[] newBias = new double[biases1.get(j).length];
            for (int l = 0; l < biases1.get(j).length; l++) {
                if (Math.random() < 0.01) {
                    newBias[j] = (Math.random() - 0.5) * 2;
                } else if (Math.random() < 0.5) {
                    newBias[j] = biases1.get(j)[l];
                } else {
                    newBias[j] = biases2.get(j)[l];
                }
            }
            newBiases.add(newBias);
        }

        child.weights = newWeights;
        child.biases = newBiases;
        return child;
    }

    public NeuralNet mutate(NeuralNet nn){
        NeuralNet mutated = new NeuralNet();
        ArrayList<Matrix> weights = nn.weights;
        ArrayList<Matrix> newWeights = new ArrayList<Matrix>(weights.size());
        
        for (int j = 0; j < weights.size(); j++) {
            Matrix newWeight = new Matrix(weights.get(j).getRowDimension(), weights.get(j).getColumnDimension());
            for (int k = 0; k < weights.get(j).getRowDimension(); k++) {
                for (int l = 0; l < weights.get(j).getColumnDimension(); l++) {
                    if (Math.random() < mutateRate) {
                        newWeight.set(k, l, (Math.random() - 0.5) * 2);
                    } else {
                        newWeight.set(k, l, weights.get(j).get(k, l));
                    }
                }
            }
            newWeights.add(newWeight);
        }

        ArrayList<double[]> biases = nn.biases;
        ArrayList<double[]> newBiases = new ArrayList<double[]>(biases.size());

        for (int j = 0; j < biases.size(); j++) {
            double[] newBias = new double[biases.get(j).length];
            for (int l = 0; l < biases.get(j).length; l++) {
                if (Math.random() < mutateRate) {
                    newBias[j] = (Math.random() - 0.5) * 2;
                } else {
                    newBias[j] = biases.get(j)[l];
                }
            }
            newBiases.add(newBias);
        }

        mutated.weights = newWeights;
        return mutated;
    }
}
