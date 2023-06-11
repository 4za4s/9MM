# FIT3077_project
This is team Red Kat's project for FIT3077.

Code is located in /src 
Saved Neural Nets are located in /SavedNeuralNets (the jar executable needs a folder in the same directory under this name to save and load the nerual nets from)

Team photo and some info and the deliverable pdf from sprint 1 is in /SprintsInfo

## Creating Executable
Project is made to run with Java 15. A jar file is included in the repo.

Open a terminal in the Project folder that containt SavedNeuralNets and src

Go the the folder one out from the src folder


Generate .class files:
javac src/App/App.java src/Board/*.java src/Display/*.java src/Game/*.java src/Jama/*.java src/Jama/util/*.java src/Player/*.java src/Player/AI/*.java src/Player/AI/NeuralNetwork/*.java -d bin


Go to bin folder cd bin

Generate .jar file:

jar cfm 9MM.jar Manifest.txt

Exit bin folder:
cd ../

java -jar 9MM.jar


If you have vs code and the java extension you can just ctrl-shift-p and export jar.


All neural networks should be save in the SavedNeuralNets folder in the same directory as this jar file

## Running code
double click 9MM.jar

select your players for the game

press start to play the game

optionally you can press start training to train a Neural Network, this requires leaving the game running for long periods of time and is how we have trained our networks. PreTrained networks are included in the repo.

First the game will enter piece placement mode. Then it will allow the user to move pieces around. The game is turn based.
