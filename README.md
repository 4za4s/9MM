# FIT3077_project
This is team Red Kat's project for FIT3077.

Code is located in /src 
Saved Neural Nets are located in /SavedNeuralNets (the jar executable needs a folder in the same directory under this name to save and load the nerual nets from)

Team photo and some info and the deliverable pdf from sprint 1 is in /SprintsInfo

## Creating Executable
Project is made to run with Java 15

Generate .class files:
javac src/App/App.java src/Board/*.java src/Display/*.java src/Game/*.java src/Jama/*.java src/Jama/util/*.java src/Player/*.java src/Player/AI/*.java src/Player/AI/NeuralNetwork/*.java

Generate .jar file:
jar -cvf 9MM.jar src/*

All neural networks should be save in the SavedNeuralNets folder in the same directory as this jar file

## Running code
double click 9MM.jar

select your players for the game

press start to play the game

optionally you can press start training to train a Neural Network, this requires leaving the game running for long periods of time and is how we have trained our networks. PreTrained networks are included. 

First the game will enter piece placement mode. Then it will allow the user to move pieces around. The game is turn based.
