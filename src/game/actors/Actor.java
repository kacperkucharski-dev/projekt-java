package game.actors;

import utils.Direction;

import java.awt.*;

public abstract class Actor implements Movable {
    private Point position;
    private int speed; // tiles per second
    private long lastMoveTime = 0;

    public Actor(Point position) {
        this.position = position;
        this.speed = 4; // 4 tiles per second = 250ms per tile
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void move(Direction direction) {
            position.translate(direction.getDx(), direction.getDy());
    }
}
