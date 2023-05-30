package Player;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Board.Board;
import Board.Position;
import Game.Game;
import Player.AI.AIMove;

public class AIPlayer extends Player {
    private AIMove moveGenerator;
    private Timer timer;

    public AIPlayer(Color colour, String playerName, AIMove moveGenerator, Game game) {
        super(colour, playerName);
        this.moveGenerator = moveGenerator;
        this.isAI = true;

        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game.getInTurnPlayer() == AIPlayer.this) {
                    game.playAction(getMove(game.getBoard()));
                }
            }
        };
        this.timer = new Timer(1000, action);
        timer.start();
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public Position getMove(Board board) {
        return moveGenerator.getMove(board);
    }
}
