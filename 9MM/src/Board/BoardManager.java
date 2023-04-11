package Board;
import java.awt.Color;

/* Manages the board */
public class BoardManager {


    Player player1 = new Player(Color.blue);
    Player player2 = new Player(Color.red);
    Player[] inTurnPlayer = {player1}; //TODO: this does not need to be a list
    int turnCounter = 1;


    Board board;

    //

    /* managers the game for a board */
    public BoardManager(Board board){

        this.board = board;

    }

    /* starts the game. Has to be called seperately as it needs to wait for other things to be set up first */
    public void startGame(){

        //add some pieces to the board for testing - assume it is playey1's turn at the stat of the game
        board.createPiece(0,0,player1);
        board.createPiece(0,3,player1);
        board.createPiece(0,6,player2); 
        board.createPiece(6,6,player2); 
        

        String piecePhases[] = {"SelectPiece"};
       

        board.updatePieces(inTurnPlayer,piecePhases, "unsedValue", false);

    }


        /* this is called whenever a button is pressed. In future it will do actual stuff.*/
        public void buttonClicked(String type){
            System.out.println("Button was pressed. Type: " + type);

            //Common variables used
            String piecePhases1[] = {"SelectPiece"}; //TODO: I'm sure there is a better way to do. Maybe have all of the scenarios saved as class variables
            Player[] selectablePlayers1 = {player1};



            //Do appropriate turn action
            switch(type){
                case "SelectPiece":

                    //Select a piece to move
                    String piecePhases[] = {"SelectPiece"};
                
                    board.updatePieces(inTurnPlayer, piecePhases,"MoveToEmptySlot", true);
                    
                    //Show selectable locations
                    board.displayAvailableLocations();

                    break;
                case "MoveToEmptySlot":
                    //Move piece (updates the board)
                    board.movePiece(inTurnPlayer, piecePhases1,"unsedValue", false);

                    changeTurn();

                    break;


                default: System.out.println("Invalid button type given: " + type);
                    break;



            }
        }
    /* Changes the turn to the other player */
    private void changeTurn(){
        turnCounter ++;
        inTurnPlayer[0] = turnCounter % 2 == 0 ? player2 : player1;
        

    }
}
