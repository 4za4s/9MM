package Board;
import java.awt.Color;

/* Manages the board */
public class BoardManager {


    Player player1 = new Player(Color.blue);
    Player player2 = new Player(Color.red);


    boolean player1Turn = false;

    Board board;

    //

    /* managers the game for a board */
    public BoardManager(Board board){

        this.board = board;

  



    }

    /* starts the game. Has to be called seperately as it needs to wait for other things to be set up first */
    public void startGame(){

        // ////add some pieces to the board for testing
        // board.createPiece(0,0,player1);
        // board.createPiece(0,3,player1);
        // board.createPiece(6,6,player2);

        // Player currentPlayer;

        //     //  while (true){

        //     //change player turn
        //     player1Turn =  !player1Turn;

        //     //run turn for that player
        //     currentPlayer = player1Turn ? player1 :  player2;

            runTurn(player1);


        // // }

    }

    /* Runs 1 players turn of the game  */
    private void runTurn(Player player){

        System.out.println("Turn = " + player.colour.toString());


        //set all pieces that are not that players to non selectable
        // board.restrictPieceAccessToOnly(player);

        


        //wait for player to select a piece


        //show available moves

        //get new location selected


        //move the piece to selected location


    }

        /* this is called whenever a button is pressed. In future it will do actual stuff.*/
        public void buttonClicked(String type){
            System.out.println("Button was pressed. Type: " + type);

            //Do appropriate turn action
            switch(type){
                case "SelectPiece":
                    //TODO:
                    // board.removeButtons
                    // board.addNewButtons
                    board.displayAvailableLocations();

                    break;
                case "ChangeSelection":
                    //TODO: show new selection for positions available
                    break;

                default: System.out.println("Invalid button type given");
                    break;



            }
        }
}
