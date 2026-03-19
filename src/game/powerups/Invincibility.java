package game.powerups;

import game.model.GameState;

import java.awt.*;

public class Invincibility extends PowerUp {

    public Invincibility(Point position) {
        super(position, 8000);
    }

    @Override
    public void apply(GameState state) {
        state.getPlayer().giveImmunity(8000);
    }

    @Override
    public void remove(GameState state) {
        // samo się usuwa i tak
    }
}
