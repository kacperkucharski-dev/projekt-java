package game.threads;

import game.actors.Ghost;
import game.actors.Player;
import game.controller.GameController;
import game.model.CollisionHandler;
import game.model.GameState;
import game.model.ThreadManager;
import game.powerups.PowerUp;
import game.view.GameSwingModel;
import game.view.GameView;
import utils.CellType;
import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameLoop extends Thread {
    private GameSwingModel gameSwingModel;
    private Player player;
    private GameState gameState;
    private CollisionHandler collisionHandler;
    private GameController gameController;
    private GameView gameView;
    private ThreadManager threadManager;
    private boolean running = true;
    private boolean nextLevel = false;
    private static int pState = 0;
    private static int pAdd = 1;
    private static int tick = 0;
    private static int time;

    public GameLoop(GameSwingModel gameSwingModel, Player player, GameState gameState, GameController gameController, GameView gameView, ThreadManager threadManager) {
        this.gameSwingModel = gameSwingModel;
        this.player = player;
        this.gameState = gameState;
        this.gameController = gameController;
        this.collisionHandler = gameController.getCollisionHandler();
        this.gameView = gameView;
        this.threadManager = threadManager;
    }

    public void stopLoop(){
        running = false;
    }

    @Override
    public void run() {
        final int fps = 30;
        final long frameTime = 1000/fps;
        time = 0;
        while (running) {
                long now = System.currentTimeMillis();
                if (tick % 3 == 0) {
                    animPacman();
                    gameSwingModel.fireTableCellUpdated(player.getPosition().x, player.getPosition().y);
                    player.updateImmunity();
                }
                synchronized (gameState.getGhosts()) {
                    checkPlayerGhostCollision();
                }

                SwingUtilities.invokeLater(()->gameView.updateHUD(gameState.getScore(), player.getLives(), threadManager.getTime()));

                isWon();

                long now1 = System.currentTimeMillis();
                long sleep = frameTime - (now1 - now);
                tick++;

                if (sleep > 0) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void animPacman(){
        pState += pAdd;
        if(pState >= 5 || pState <= 0){
            pAdd *= -1;
        }
    }

    public static int getpState() {
        return pState;
    }

    private void checkPlayerGhostCollision(){
        Point playerPosition = player.getPosition();

        for(Ghost g: gameState.getGhosts()){
            if(g.getPosition().equals(playerPosition)){
                if(g.isEdible()){
                    gameState.setScore(gameState.getScore() + 10*gameState.getLevel());
                    g.resetPosition();
                }
                else {
                    if (!player.isImmune()) {
                        player.loseLife();
                    }
                    if (player.getLives() == 0) {
                        gameOverSequence();
                        SwingUtilities.invokeLater(() -> gameView.gameOverSequence("Koniec żyć!"));
                    }
                }
            }
        }
    }
    public void gameOverSequence(){
        SwingUtilities.invokeLater(()->gameView.updateHUD(gameState.getScore(),player.getLives(),threadManager.getTime()));
        player.setSpeed(0);
        threadManager.stopThreads();
    }

    public void isWon(){
        if (gameState.getDots()==0 && !nextLevel){
            nextLevel = true;
            nextLevel();
        }
    }

    private void nextLevel() {
        player.giveImmunity(1000);
        gameState.setScore(gameState.getScore()+20*gameState.getLevel());
        gameState.setLevel(gameState.getLevel()+1);
        threadManager.setTime(gameState.getLevel()*10);
        gameState.getMaze().generate();
        synchronized (gameState.getGhosts()) {
            for (Ghost g : gameState.getGhosts()) {
                g.resetPosition();
            }
        }
        nextLevel = false;
        SwingUtilities.invokeLater(()->gameSwingModel.fireTableDataChanged());
    }

    public GameSwingModel getGameSwingModel() {
        return gameSwingModel;
    }
}
