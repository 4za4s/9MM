package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Board.Board;
import Board.Player;
import Board.Position;
import Game.Game;
import Game.GameState;

/**
 * Manages the display
 */
public class GameDisplay extends AbstractDisplay {
    private Game game;
    private int[][] buttonLocations = { 
        { 0, 0 }, { 0, 3 }, { 0, 6 },   // {row,column}
        { 1, 1 }, { 1, 3 }, { 1, 5 }, 
        { 2, 2 }, { 2, 3 }, { 2, 4 }, 
        { 3, 0 }, { 3, 1 }, { 3, 2 }, 
        { 3, 4 }, { 3, 5 }, { 3, 6 },
        { 4, 2 }, { 4, 3 }, { 4, 4 },
        { 5, 1 }, { 5, 3 }, { 5, 5 },
        { 6, 0 }, { 6, 3 }, { 6, 6 } 
    };

    private ButtonDisplay buttonDisplay; //button layer for game
    private SelectionHighlights selectionHighlights; //highlight layer for game 
    private Background background; //background layer for game
    PieceCounter leftPieceCounter;
    PieceCounter rightPieceCounter;
    JButton exit;
    JLabel playerTurn;




    //Commonly used variables for all of the size elements needed
    int gap;
    int slotSize;
    int width; //total width of display
    int height; //total height of display
    int boardXPosStart; // x pos for start of board
    int boardYPosStart;  // y pos for start of board
    int highlightSize; //size of highlights
    int pieceCounterWidth; //width of the piece counter
    int pieceCounterHeight; //height of the piece counter
    int lineWidth; //width of the lines

    
    
    /**
     * Class constructor
     */
    public GameDisplay(int Height, int Width, Game game, Window window){
        super(Height, Width, window);
        this.game = game;

        ArrayList<Player> players = game.getPlayers();
        //Create layers
        background = new Background();
        buttonDisplay = new ButtonDisplay();
        selectionHighlights = new SelectionHighlights();
        leftPieceCounter = new PieceCounter(players.get(0),players.get(1));
        rightPieceCounter = new PieceCounter(players.get(1),players.get(0));

        buttonDisplay.createButtonDisplay(game, game.getBoard().getPositions(), buttonLocations, gap, slotSize);

        exit = new JButton("EXIT");

        exit.setBackground(Color.white);

        exit.addActionListener(e -> window.displayMenu());
        window.add(exit); 

        playerTurn = new JLabel("Currently " + game.getInTurnPlayer().getName() + "'s Turn",SwingConstants.CENTER);
        playerTurn.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        playerTurn.setOpaque(true);
        playerTurn.setBackground(Color.white);
        window.add(playerTurn);
    

        //Add layers to the frame
        window.add(buttonDisplay);
        window.add(background);
        window.add(selectionHighlights);
        window.add(leftPieceCounter);
        window.add(rightPieceCounter);
    
        window.repaint();
    }

    /**
     * Updates the display 
     */
    @Override
    public void updateDisplay(){
        Board board = game.getBoard();
        for (Position pos : board.getPositions()) {
            if (pos.getPiece() != null) {
                pos.setForeground(pos.getPiece().getColour());
            }
            else {
                pos.setForeground(Color.white);
            }
        }

        removeHighlights();

        if (game.getGameState() != GameState.POSTGAME) {
            displayPossibleMoveHighlights(board.getPossibleMoves(game.getGameState(), game.getSelectedPiece(), game.getInTurnPlayer()), game.getInTurnPlayer().getColour());
            playerTurn.setText("Currently " + game.getInTurnPlayer().getName() + "'s Turn");
        }

        window.repaint();
    }

    /**
     * Highlight an available location the selected piece can move 
     * @param possibleMoves the locations available
     * @param playerColour Colour of the player who's turn it is, for the correct highlight colour
     */
    public void displayPossibleMoveHighlights(ArrayList<Position> possibleMoves, Color playerColour){
        Color highlightcolour = new Color(
            playerColour.getRed(), 
            playerColour.getGreen(),
            playerColour.getBlue(),
            playerColour.getAlpha()*2/5
        );

        selectionHighlights.addHighlights(possibleMoves, highlightcolour);
    }

    /**
     * Remove all highlights, calls the removeHighlights method in the highlight layer
     */
    public void removeHighlights(){
        selectionHighlights.removeHighlights();
    }

    private void setBoardElementsSize(Dimension size){

        width = (int) size.getWidth();
        height = (int) size.getHeight();

        int minDim = Math.min(width, height);
        gap = (minDim / 10);

        lineWidth = gap/10;
        slotSize = gap/2;

        highlightSize = (slotSize * 3) / 2;
       
        boardXPosStart = width / 2  - gap * 3 ;
        boardYPosStart = height / 2  - gap * 3 ;

        pieceCounterWidth = minDim / 17;
        pieceCounterHeight = (int) (pieceCounterWidth * 3.8);

    }

    public void playerWins(Player player){
        playerTurn.setText(player.getName() + " Wins!");
    }

    public void resizeDisplay(Dimension size) {

        setBoardElementsSize(size);

        buttonDisplay.resizeDisplay(gap, slotSize);
        buttonDisplay.setLocation(boardXPosStart - slotSize / 2, boardYPosStart - slotSize / 2);
        buttonDisplay.setSize(gap*6 + slotSize, gap*6 + slotSize);

        selectionHighlights.resizeDisplay(highlightSize); 
        selectionHighlights.setLocation(boardXPosStart - highlightSize / 2, boardYPosStart - highlightSize / 2);
        selectionHighlights.setSize(gap*6 + highlightSize, gap*6 + highlightSize);

        background.resizeDisplay(gap, lineWidth); 
        background.setLocation(boardXPosStart - lineWidth / 2, boardYPosStart - lineWidth / 2);
        background.setSize(gap*6 + lineWidth, gap*6 + lineWidth);

        leftPieceCounter.resizeDisplay(pieceCounterWidth,pieceCounterHeight);
        leftPieceCounter.setLocation(boardXPosStart / 2 - pieceCounterWidth / 2,  height / 2 - pieceCounterHeight / 2 );
        leftPieceCounter.setSize(pieceCounterWidth, pieceCounterHeight);

        rightPieceCounter.resizeDisplay(pieceCounterWidth,pieceCounterHeight);
        rightPieceCounter.setLocation(width - boardXPosStart / 2 - pieceCounterWidth / 2,  height / 2 - pieceCounterHeight / 2 );
        rightPieceCounter.setSize(pieceCounterWidth, pieceCounterHeight);

        exit.setBounds(size.width/2-gap,boardYPosStart+gap*13/2,gap*2,gap);
        playerTurn.setBounds(window.getWidth()/2 - gap*3,boardYPosStart-100,gap*6,gap);
    }
}

