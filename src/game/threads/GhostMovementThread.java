package game.threads;

import game.actors.Ghost;
import game.model.CollisionHandler;
import game.model.GameState;
import game.view.GameSwingModel;
import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GhostMovementThread extends Thread {
    private GameState gameState;
    private GameSwingModel gameSwingModel;
    private CollisionHandler collisionHandler;
    private boolean running = true;

    public GhostMovementThread(GameState gameState, GameSwingModel gameSwingModel, CollisionHandler collisionHandler) {
        this.gameState = gameState;
        this.gameSwingModel = gameSwingModel;
        this.collisionHandler = collisionHandler;
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (gameState.getGhosts()) {
                updateGhosts();
            }
            Ghost g = gameState.getGhosts().get(0);
                try{
                    Thread.sleep(1000/g.getSpeed());
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
        }
    }

    private void updateGhosts(){
        Set<Point> newGhostPositions = new HashSet<>();

        for(Ghost g: gameState.getGhosts()){
            Point oldPosition = new Point(g.getPosition());

            Direction direction = Direction.getRandomDirection();
            Point check = new Point(oldPosition.x+direction.getDx(), oldPosition.y+direction.getDy());
            boolean legal = collisionHandler.isLegal(oldPosition, direction)
                    && !gameState.isGhost(check.x, check.y)
                    && !newGhostPositions.contains(check);

            if (legal && !g.isFrozen()) {
                g.move(direction);
            }

            Point newPosition = new Point(g.getPosition());
            newGhostPositions.add(newPosition);

            SwingUtilities.invokeLater(()->{
                gameSwingModel.fireTableCellUpdated(oldPosition.x, oldPosition.y);
                gameSwingModel.fireTableCellUpdated(newPosition.x, newPosition.y);
            });
        }
    }
}
