package game.threads;

import game.actors.Player;
import game.model.GameState;
import game.model.ThreadManager;
import game.view.GameView;

import javax.swing.*;

public class GameClock extends Thread {
    private GameView gameView;
    private GameState gameState;
    private Player player;
    private ThreadManager threadManager;
    private boolean running = true;
    private int time = 0;

    public GameClock(GameView gameView, GameState gameState, Player player, ThreadManager threadManager) {
        this.gameView = gameView;
        this.gameState = gameState;
        this.player = player;
        this.threadManager = threadManager;
    }

    public void stopThread(){
        running = false;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    @Override
    public void run(){
        while(running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
            SwingUtilities.invokeLater(()->gameView.updateHUD(gameState.getScore(), player.getLives(), threadManager.getTime()));
            if (time > gameState.getTime()) {
                threadManager.getGameLoop().gameOverSequence();
                SwingUtilities.invokeLater(()->gameView.gameOverSequence("Koniec czasu!"));
            }
        }
    }
}
