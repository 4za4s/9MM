package Board;
import java.awt.Color;


/**
 * Manages the board
 */
public class BoardManager {
   
    private Player player1 = new Player(Color.blue, "Player Blue"); //There are two players in the game
    private Player player2 = new Player(Color.red, "Player Red");

    private Player inTurnPlayer = player1; //Which player whose turn it currently is
    private int turnCounter = 0; //Keep track of the turn number

    private Board board; //the board that is managed
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

       

        board.updateBoard(inTurnPlayer,"SelectPiece", "unused", true,
        inTurnPlayer.getName() + ": select a position to place your piece");
        board.displayAvailableLocations();

    }

        /**
         * This is called whenever a button is pressed. Managed the outcome
         * @param type the action the button represents when clicked - eg select a piece -> SelectPiece
         */
        public void buttonClicked(String type){
            System.out.println("Button was pressed. Type: " + type);

            //Common variables used

            //Do appropriate turn action
            switch(type){

                case "createPiece":

                    //TODO: logic for creating pieces
                    





                    break; 


                case "SelectPiece":

                    //Select a piece to move
                    board.updateBoard(inTurnPlayer, "SelectPiece","MoveToEmptySlot",
                    true, "<html>" + inTurnPlayer.getName() + ": select a position to move your piece to <br/>or a new piece to move</html>");
                    
                    //Show selectable locations
                    board.displayAvailableLocations();

                    break;

                case "MoveToEmptySlot":
                    //Move piece (updates the board)
                    changeTurn();

                    board.moveLatestPiece();

                    board.updateBoard(inTurnPlayer, "SelectPiece","unsedValue", false,
                    inTurnPlayer.getName() + ": select a piece to move");

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
