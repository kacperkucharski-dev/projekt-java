package game.actors;

import utils.Direction;

import java.awt.*;

public class Player extends Actor {
    private Direction direction = null;
    private int lives = 3;
    private boolean isImmune = false;
    private long immunityEndTime = 0;

    public Player(Point position) {
        super(position);
        setSpeed(8);
    }

    public int getLives(){
        return lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void loseLife(){
        if (!isImmune && lives > 0){
            lives--;
            isImmune = true;
            immunityEndTime = System.currentTimeMillis() + 3000;
        }
    }
    public void updateImmunity(){
        if(isImmune && System.currentTimeMillis() > immunityEndTime){
            isImmune = false;
        }
    }

    public boolean isImmune(){
        return isImmune;
    }

    public void giveImmunity(long time){
        isImmune = true;
        immunityEndTime = System.currentTimeMillis() + time;
    }
}
