package Player.AI.NeuralNetwork;

import java.util.ArrayList;

public class TrainNeuralNet {
    int popsize = 100;
    ArrayList<NeuralNet> population = new ArrayList<NeuralNet>();
    

    public TrainNeuralNet(){
        for (int i = 0; i < popsize; i++){
            population.add(new NeuralNet());
        }
    }
}
