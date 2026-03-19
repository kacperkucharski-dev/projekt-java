package game.threads;

import game.actors.Ghost;
import game.model.GameState;
import game.model.ThreadManager;
import game.powerups.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PowerUpSpawner extends Thread {
    private GameState gameState;
    private ThreadManager threadManager;
    private boolean running = true;

    public PowerUpSpawner(GameState gameState,ThreadManager threadManager) {
        this.gameState = gameState;
        this.threadManager = threadManager;
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try{
                Thread.sleep(5000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if (Math.random() < 0.25) {
                ArrayList<Ghost> temp = new ArrayList<>();
                for(Ghost g:gameState.getGhosts()) {
                    temp.add(g);
                }
                Collections.shuffle(temp);
                spawnRandomPowerUp(temp.get(0).getPosition());
            }
        }
    }

    private void spawnRandomPowerUp(Point position) {
        Point position1 = new Point(position);
        int value = (int)(Math.random()*6);
        switch (value) {
            case 0 -> gameState.addPowerUp(new SpeedBoost(position1));
            case 1 -> gameState.addPowerUp(new Stun(position1));
            case 2 -> gameState.addPowerUp(new Invincibility(position1));
            case 3 -> gameState.addPowerUp(new OneUp(position1));
            case 4 -> gameState.addPowerUp(new GodMode(position1));
            case 5 -> gameState.addPowerUp(new SlowMo(position1));
        }
        SwingUtilities.invokeLater(()->threadManager.getGameLoop().getGameSwingModel().fireTableCellUpdated(position.x, position.y));
    }
}
