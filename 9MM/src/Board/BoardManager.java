package Board;
import java.awt.Color;


/**
 * Manages the board
 */
public class BoardManager {
   
    Player player1 = new Player(Color.blue); //There are two players in the game
    Player player2 = new Player(Color.red);


    Player inTurnPlayer = player1; //Which player whose turn it currently is
    int turnCounter = 0; //Keep track of the turn number


    Board board;

    public BoardManager(Board board){

        this.board = board;

    }

    /**
     * Starts the game. Has to be called seperately as it needs to wait for other things - eg the board-
     *  to be set up first
     */
    public void startGame(){

        //add some pieces to the board for testing - assume it is player1's turn at the start of the game
        board.createPiece(0,0,player1);
        board.createPiece(0,3,player1);
        board.createPiece(0,6,player2); 
        board.createPiece(6,6,player2); 
        

        String piecePhases = "SelectPiece";
       

        board.updatePieces(inTurnPlayer,piecePhases, "unsedValue", false);

    }

        /**
         * This is called whenever a button is pressed. Managed the outcome
         * @param type the action the button represents when clicked - eg select a piece -> SelectPiece
         */
        public void buttonClicked(String type){
            System.out.println("Button was pressed. Type: " + type);

            //Common variables used
            String piecePhases1 = "SelectPiece"; //TODO: I'm sure there is a better way to do. Maybe have all of the scenarios saved as class variables



            //Do appropriate turn action
            switch(type){
                case "SelectPiece":

                    //Select a piece to move
                    String piecePhase = "SelectPiece";
                    board.updatePieces(inTurnPlayer, piecePhase,"MoveToEmptySlot", true);
                    
                    //Show selectable locations
                    board.displayAvailableLocations();

                    break;

                case "MoveToEmptySlot":
                    //Move piece (updates the board)
                    changeTurn();

                    board.moveLatestPiece();

                    board.updatePieces(inTurnPlayer, piecePhases1,"unsedValue", false);

                    break;


                default: System.out.println("Invalid button type given: " + type);
                    break;



            }
        }

    /**
     * Changes the turn to the other player
     */
    private void changeTurn(){
        turnCounter ++;
        System.out.println("Turn: " + turnCounter);
        int turn = turnCounter % 2 ;

        if (turn == 0){
            inTurnPlayer = player1;

        } else {
            inTurnPlayer = player2;
            
        }

        //Not sure why but this gives an error on compilation:
        // inTurnPlayer = turnCounter % 2 == 0 ? player1 : player2;
        

    }
}
