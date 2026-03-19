package game.controller;

import game.actors.Player;
import game.model.CollisionHandler;
import game.model.GameState;
import game.model.ThreadManager;
import game.threads.GameLoop;
import game.view.GameView;
import game.view.Renderer;
import utils.CellType;
import utils.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    GameState gameState;
    GameView gameView;
    Player player;
    CollisionHandler collisionHandler;

    public GameController(GameState gameState) {
        this.gameState = gameState;
        player = gameState.getPlayer();
        collisionHandler = new CollisionHandler(gameState);
    }
    public void addGameView(GameView gameView) {
        this.gameView = gameView;
    }

    private Direction mapKeyToDirection(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP: return Direction.UP;
            case KeyEvent.VK_DOWN: return Direction.DOWN;
            case KeyEvent.VK_LEFT: return Direction.LEFT;
            case KeyEvent.VK_RIGHT: return Direction.RIGHT;
            default: return null;
        }
    }

    public void movePlayer(Direction direction){
        long now = System.currentTimeMillis();
        int delay;
        if(player.getSpeed() != 0) delay = 1000 / player.getSpeed();
        else delay = Integer.MAX_VALUE;

        if(now - player.getLastMoveTime() < delay) return;

        Point oldPosition = new Point(player.getPosition());
        Point newPosition = new Point(player.getPosition().x+direction.getDx(), player.getPosition().y+direction.getDy());

        if (collisionHandler.isLegal(oldPosition, direction)){
            CellType target = gameState.getCellType(newPosition.x, newPosition.y);
            if(target == CellType.POWER){
                if(gameState.getPowerUp(newPosition).isOverridenDot()){
                    gameState.setDots(gameState.getDots()-1);
                    gameState.setScore(gameState.getScore()+1);
                }
                gameState.getPowerUp(newPosition).setActive(gameState);
                gameState.removePowerUp(newPosition);
            }
            if(target == CellType.DOT){
                gameState.setScore(gameState.getScore()+1*gameState.getLevel());
                gameState.setDots(gameState.getDots()-1);
            }
            player.setPosition(newPosition);
            gameState.setCellType(oldPosition.x, oldPosition.y, CellType.EMPTY);
            gameState.setCellType(newPosition.x, newPosition.y, CellType.PACMAN);
            gameView.getSwingModel().fireTableCellUpdated(oldPosition.x, oldPosition.y);
            gameView.getSwingModel().fireTableCellUpdated(newPosition.x, newPosition.y);
            player.setLastMoveTime(now);
        }
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction = mapKeyToDirection(e.getKeyCode());
        if(direction != null){
            player.setDirection(direction);
            Renderer.setDirection(direction);
            movePlayer(direction);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
