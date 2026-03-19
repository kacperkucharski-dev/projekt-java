package game.model;

import utils.CellType;
import utils.Direction;

import java.awt.*;

public class CollisionHandler {
    private GameState gameState;

    public CollisionHandler(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isLegal(Point position, Direction direction) {
        int newY = position.y + direction.getDy();
        int newX = position.x + direction.getDx();

        CellType target = gameState.getCellType(newX, newY);
        return target != CellType.WALL;
    }
}

