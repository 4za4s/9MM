package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;

import Board.Board;
import Board.Player;
import Board.Position;
import Game.Game;

/**
 * Manages the display for a game
 */
public class GameDisplay extends Display {
    private Game game; //game that this class manages display for
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
    private MillHighlights millHighlights; //highlighs the mills
    private PieceCounter leftPieceCounter; //shows left piece counter
    private PieceCounter rightPieceCounter; //shows right piece counter
    private JButton exit; //exit button
    private TurnText turnText; //text telling player what to do


    //Variables for all of the size elements needed
    private int gap; //distance between concentric squares of buttons
    private int slotSize; //size of buttons
    private int width; //total width of display
    private int height; //total height of display
    private int boardXPosStart; // x pos for start of board
    private int boardYPosStart;  // y pos for start of board
    private int highlightSize; //size of highlights
    private int pieceCounterWidth; //width of the piece counter
    private int pieceCounterHeight; //height of the piece counter
    private int lineWidth; //width of the lines
    private int millHighlightsWidth; //How wide mill highlights are
    private int fontSize; //Size of font for text

    
    
    /**
     * Class constructor
     */
    public GameDisplay(int height, int width, Game game, Window window){
        super(height, width, window);
        this.game = game;

        ArrayList<Player> players = game.getPlayers();
        //Create layers
        background = new Background();
        buttonDisplay = new ButtonDisplay();
        selectionHighlights = new SelectionHighlights();
        leftPieceCounter = new PieceCounter(players.get(0),players.get(1));
        rightPieceCounter = new PieceCounter(players.get(1),players.get(0));
        millHighlights = new MillHighlights(players.get(0),players.get(1), game.getBoard().getMills());

        buttonDisplay.createButtonDisplay(game, game.getBoard().getPositions(), buttonLocations, gap, slotSize);

        exit = new JButton("EXIT");

        exit.setBackground(Color.white);

        exit.addActionListener(e -> window.displayMenu());
        window.add(exit); 
;
        turnText = new TurnText();
        turnText.setBorder(new javax.swing.border.LineBorder(Color.black, 3));
        turnText.setOpaque(true);
        turnText.setBackground(Color.white);
        window.add(turnText);
    

        //Add layers to the frame
        window.add(buttonDisplay);
        window.add(background);
        window.add(selectionHighlights);
        window.add(leftPieceCounter);
        window.add(rightPieceCounter);
        window.add(millHighlights);
    
        window.repaint();
    }

 
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
        turnText.setText(game.getInTurnPlayer(), game.getGameState());

        displayPossibleMoveHighlights(board.getPossibleMoves(game.getGameState(), 
                                          game.getSelectedPiece(), game.getInTurnPlayer(),game.getNotInTurnPlayer()), 
                                          game.getInTurnPlayer().getColour()); 
                        
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

        millHighlightsWidth = lineWidth * 5 / 2;
        slotSize = gap  / 2;

        highlightSize = (slotSize * 3) / 2;
       
        boardXPosStart = width / 2  - gap * 3 ;
        boardYPosStart = height / 2  - gap * 3 ;

        fontSize = minDim / 50;

        pieceCounterWidth = minDim / 17;
        pieceCounterHeight = (int) (pieceCounterWidth * 3.8);

        


    }

    /**
     * What to display when a player wins
     * @param player play who won
     */
    public void playerWins(Player player){
        

        //Colour which is a lighter version of the player's icon
        Color baseWinnerColor = new Color(
            255  - (255 - player.getColour().getRed() ) / 5, 
            255 -  (255 - player.getColour().getGreen()) / 5,
            255 -  (255 - player.getColour().getBlue()) / 5
        );


        window.getContentPane().setBackground(baseWinnerColor);
    }


    @Override
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

        leftPieceCounter.resizeDisplay(pieceCounterWidth);
        leftPieceCounter.setLocation(boardXPosStart / 2 - pieceCounterWidth / 2,  height / 2 - pieceCounterHeight / 2 );
        leftPieceCounter.setSize(pieceCounterWidth, pieceCounterHeight);

        rightPieceCounter.resizeDisplay(pieceCounterWidth);
        rightPieceCounter.setLocation(width - boardXPosStart / 2 - pieceCounterWidth / 2,  height / 2 - pieceCounterHeight / 2 );
        rightPieceCounter.setSize(pieceCounterWidth, pieceCounterHeight);

        millHighlights.resizeDisplay(millHighlightsWidth);
        millHighlights.setLocation(boardXPosStart - millHighlightsWidth / 2, boardYPosStart - millHighlightsWidth / 2);
        millHighlights.setSize(gap*6 + millHighlightsWidth, gap*6 + millHighlightsWidth);
        
        turnText.setBounds(width/2 - gap*3,boardYPosStart/3,gap*6,boardYPosStart/3);
        turnText.setFont(new Font("Serif", Font.PLAIN, fontSize));

        exit.setBounds(width/2-gap,height * 6 / 7 ,gap*2,boardYPosStart/3);
        exit.setFont(new Font("Serif", Font.BOLD, fontSize));


    }
}

