// package Player.AI.NeuralNetwork;

// import java.util.ArrayList;

// import Game.Game;

// public class TrainNeuralNet {
//     private int popsize = 100;
//     private ArrayList<NeuralNet> population;

//     private int bestNetWorkSelection = 20;
//     private int WorstNetWorkSelection = 20;
//     private int numberToCrossover= 40;
    
//     private int currentPopsize = 0;

//     public TrainNeuralNet(){
//         for (int i = 0; i < popsize; i++){
//             population.add(new NeuralNet());
//         }
//     }

//     private void Start()
//     {
//         Game game = new Game();
//         CreatePopulation();
//     }

//     private void CreatePopulation()
//     {
//         population = new ArrayList<NeuralNet>(popsize);
//         FillPopulationWithRandomValues(population, 0);
//         population[50] = new NeuralNetwork(File.ReadAllText("./nn534.txt"));
//         ResetToCurrentGenome();
//     }

//     private void ResetToCurrentGenome()
//     {
//         controller.ResetWithNetwork(population[currentGenome]);
//     }

//     private void FillPopulationWithRandomValues (NeuralNetwork[] newPopulation, int startingIndex)
//     {
//         while (startingIndex < initialPopulation)
//         {
//             newPopulation[startingIndex] = new NeuralNetwork(new int[] {6,5,2});
//             startingIndex++;
//         }
//     }

//     public void GameEnd(float fitness[], NeuralNet network[])
//     {
//         if (currentGenome < population.Length -1)
//         {
//             population[currentGenome].fitness = fitness;
//             currentGenome++;
//             ResetToCurrentGenome();
//         }
//         else
//         {
//             RePopulate();
//         }

//     }

    
//     private void RePopulate()
//     {
//         genePool.Clear();
//         currentGeneration++;
//         naturallySelected = 0;
//         SortPopulation();

//         NeuralNetwork[] newPopulation = PickBestPopulation();

//         Crossover(newPopulation);
//         Mutate(newPopulation);

//         //FillPopulationWithRandomValues(newPopulation, naturallySelected);

//         population = newPopulation;

//         currentGenome = 0;

//         ResetToCurrentGenome();

//     }

//     private void Mutate (NeuralNetwork[] newPopulation)
//     {

//         for (int i = 1; i < naturallySelected; i++)
//         {
//             for (int l = 0; l < newPopulation[i].NumLayers(); l++)
//             {   
//                 if (Random.Range(0.0f, 1.0f) < mutationRate)
//                 {
//                     for (int n = 0; n < newPopulation[i].layers[l].NumNeurons(); n++)
//                     {   
//                         for (int d = 0; d < newPopulation[i].layers[l].neurons[n].NumDendrites(); d++)
//                         {  
//                             double w = newPopulation[i].layers[l].neurons[n].dendrites[d].weight;
//                             w = w + Random.Range(-0.5f, 0.5f);
//                             if (w < -1){
//                                 w = -1;
//                             }
//                             if (w > 1){
//                                 w = 1;
//                             }
//                             newPopulation[i].layers[l].neurons[n].dendrites[d].weight = (double) w;
//                         }
//                     }
//                 }
//             }
//         }
//     }

//     private void Crossover (NeuralNetwork[] newPopulation)
//     {
//         for (int i = 0; i < numberToCrossover; i+=1)
//         {
//             int AIndex = i;
//             int BIndex = i + 1;

//             if (genePool.Count >= 1)
//             {
//                 for (int l = 0; l < 100; l++)
//                 {
//                     AIndex = Random.Range(0, 20);
//                     BIndex = Random.Range(0, 20);

//                     if (AIndex != BIndex)
//                         break;
//                 }
//             }

//             NeuralNetwork Child1 = new NeuralNetwork(new int[]{6,5,2});
//             NeuralNetwork Child2 = new NeuralNetwork(new int[]{6,5,2});

//             Child1.fitness = 0;
//             Child2.fitness = 0;


//             for (int l = 0; l < Child1.NumLayers(); l++)
//             {   
//                 for (int n = 0; n < Child1.layers[l].NumNeurons(); n++)
//                 {   
//                     for (int d = 0; d < Child1.layers[l].neurons[n].NumDendrites(); d++)
//                     {  
//                         if (Random.Range(0.0f, 1.0f) < 0.5f)
//                         {
//                             Child1.layers[l].neurons[n].dendrites[d].weight = population[AIndex].layers[l].neurons[n].dendrites[d].weight;
//                             Child2.layers[l].neurons[n].dendrites[d].weight = population[BIndex].layers[l].neurons[n].dendrites[d].weight;
//                         }
//                         else
//                         {
//                             Child2.layers[l].neurons[n].dendrites[d].weight = population[AIndex].layers[l].neurons[n].dendrites[d].weight;
//                             Child1.layers[l].neurons[n].dendrites[d].weight = population[BIndex].layers[l].neurons[n].dendrites[d].weight;
//                         }
//                     }
//                 }
//             }

//             for (int l = 0; l < Child1.NumLayers(); l++)
//             {   
//                 for (int n = 0; n < Child1.layers[l].NumNeurons(); n++)
//                 {
//                     if (Random.Range(0.0f, 1.0f) < 0.5f)
//                     {
//                         Child1.layers[l].neurons[n].bias = population[AIndex].layers[l].neurons[n].bias;
//                         Child2.layers[l].neurons[n].bias = population[BIndex].layers[l].neurons[n].bias;
//                     }
//                     else
//                     {
//                         Child2.layers[l].neurons[n].bias = population[AIndex].layers[l].neurons[n].bias;
//                         Child1.layers[l].neurons[n].bias = population[BIndex].layers[l].neurons[n].bias;
//                     }
//                 }
//             }

//             newPopulation[naturallySelected] = Child1;
//             naturallySelected++;

//             newPopulation[naturallySelected] = Child2;
//             naturallySelected++;

//         }
//     }

//     private NeuralNetwork[] PickBestPopulation()
//     {

//         NeuralNetwork[] newPopulation = new NeuralNetwork[initialPopulation];

//         for (int i = 0; i < bestAgentSelection; i++)
//         {
//             population[i].Save();
//             string nn = File.ReadAllText("./nn" + (int)population[i].fitness + ".txt");
//             newPopulation[naturallySelected] = new NeuralNetwork(nn);
//             newPopulation[naturallySelected].fitness = 0;
//             naturallySelected++;
            
//             int f = Mathf.RoundToInt(population[i].fitness * 10);

//             for (int c = 0; c < f; c++)
//             {
//                 genePool.Add(i);
//             }

//         }

//         for (int i = 0; i < worstAgentSelection; i++)
//         {
//             int last = population.Length - 1;
//             last -= i;

//             int f = Mathf.RoundToInt(population[last].fitness * 10);

//             for (int c = 0; c < f; c++)
//             {
//                 genePool.Add(last);
//             }

//         }

//         return newPopulation;

//     }

//     private void SortPopulation()
//     {
//         for (int i = 0; i < population.Length; i++)
//         {
//             for (int j = i; j < population.Length; j++)
//             {
//                 if (population[i].fitness < population[j].fitness)
//                 {
//                     NeuralNetwork temp = population[i];
//                     population[i] = population[j];
//                     population[j] = temp;
//                 }
//             }
//         }
 
// }
