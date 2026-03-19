package game.powerups;

import game.actors.Ghost;
import game.model.GameState;

import java.awt.*;

public class GodMode extends PowerUp {
    public GodMode(Point position){
        super(position, 10000);
    }

    @Override
    public void apply(GameState state) {
        for(Ghost g: state.getGhosts()){
            g.makeEdible();
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
        for(Ghost g: state.getGhosts()){
            g.makeNotEdible();
        }
    }
}
