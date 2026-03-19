package game.actors;

import utils.Direction;
import java.awt.*;

public class Ghost extends Actor {

    private final Point start;
    private boolean frozen = false;
    private boolean isEdible = false;

    public Ghost(Point position, Direction direction) {
        super(position);
        setSpeed(8);
        this.start = new Point(position.x, position.y);
    }

    public void resetPosition() {
        setPosition(new Point(start));
    }

    public void freeze() {
        frozen = true;
    }

    public void deFreeze() {
        frozen = false;
    }

    public void makeEdible(){
        isEdible = true;
    }

    public void makeNotEdible(){
        isEdible = false;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public Point getStart() {
        return start;
    }
}
