import java.awt.Color;

public class TurnManager {


    Player player1 = new Player(Color.blue);
    Player player2 = new Player(Color.red);


    boolean player1Turn = false;

    Board board;

    //

    /* goes through all of the steps of a turn */
    public TurnManager(Board board){

        this.board = board;


        //add some pieces to the board for testing
        board.createPiece(0,0,player1);
        board.createPiece(6,6,player2);

        Player currentPlayer;

        while (true){

            //change player turn
            player1Turn =  !player1Turn;

        
            //run turn for that player
            currentPlayer = player1Turn ? player1 :  player2;

            runTurn(currentPlayer);



            

            //

        }
    }


    private void runTurn(Player player){

        System.out.println("Turn = " + player.colour.toString());


        //set all pieces that are not that players to non selectable

        //select a piece


        //show available moves

        //get new location selected


        //move the piece to selected location


    }
}
