package game.powerups;

import game.actors.Ghost;
import game.model.GameState;

import java.awt.*;

public class Stun extends PowerUp {

    public Stun(Point position) {
        super(position, 5000);
    }

    @Override
    public void apply(GameState state) {
        for(Ghost g : state.getGhosts()) {
            g.freeze();
        }
        new Thread(()->{
            try{
                Thread.sleep(duration());
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            remove(state);
        }).start();
    }

    @Override
    public void remove(GameState state) {
        for(Ghost g : state.getGhosts()) {
            g.deFreeze();
        }
    }
}
