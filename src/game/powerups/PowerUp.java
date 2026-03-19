package game.powerups;

import game.model.GameState;

import java.awt.*;

public abstract class PowerUp {
    private Point position;
    private boolean active;
    private long activationTime;
    private long duration;
    private boolean overridenDot = false;

    public PowerUp(Point position, int duration) {
        this.position = position;
        this.duration = duration;
        this.active = false;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(GameState gameState) {
        active = true;
        activationTime = System.currentTimeMillis();
        apply(gameState);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - activationTime > duration;
    }

    public void deactivate(GameState gameState) {
        active = false;
        remove(gameState);
    }

    public boolean isOverridenDot() {
        return overridenDot;
    }

    public void setOverridenDot(boolean overridenDot) {
        this.overridenDot = overridenDot;
    }

    public long duration(){
        return duration;
    }

    public abstract void apply(GameState state);
    public abstract void remove(GameState state);
}
