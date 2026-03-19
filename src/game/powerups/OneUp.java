package game.powerups;

import game.model.GameState;

import java.awt.*;

public class OneUp extends PowerUp {
    public OneUp(Point position) {
        super(position, 0);
    }

    @Override
    public void apply(GameState state) {
        state.getPlayer().setLives(state.getPlayer().getLives() + 1);
    }

    @Override
    public void remove(GameState state) {
        // nic, instant effect
    }
}
