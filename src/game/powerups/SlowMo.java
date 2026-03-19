package game.powerups;

import game.actors.Ghost;
import game.model.GameState;

import java.awt.*;

public class SlowMo extends PowerUp {
    public SlowMo(Point position) {
        super(position,8000);
    }

    @Override
    public void apply(GameState state) {
        for(Ghost g:state.getGhosts()){
            g.setSpeed(4);
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
        for(Ghost g:state.getGhosts()){
            g.setSpeed(8);
        }
    }
}
