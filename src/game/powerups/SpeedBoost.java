package game.powerups;

import game.actors.Ghost;
import game.model.GameState;

import java.awt.*;

public class SpeedBoost extends PowerUp {

    public SpeedBoost(Point position) {
        super(position,8000);
    }

    @Override
    public void apply(GameState state) {
        state.getPlayer().setSpeed(16);
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
        state.getPlayer().setSpeed(8);
    }
}
