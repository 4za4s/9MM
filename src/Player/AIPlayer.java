package Player;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Board.Board;
import Board.Piece;
import Board.Position;
import Game.Game;
import Game.GameState;
import Player.AI.AIMove;

public class AIPlayer extends Player {
    private AIMove moveGenerator;
    

    public AIPlayer(Color colour, String playerName, AIMove moveGenerator, Game game) {
        super(colour, playerName);
        this.moveGenerator = moveGenerator;
        this.isAI = true;
        

    }

    @Override
    public Position getMove(Board board, GameState gameState, Player inTurnPlayer, Player notInTurnPlayer) {
        return moveGenerator.getMove( board, gameState, inTurnPlayer, notInTurnPlayer);
    }


}
