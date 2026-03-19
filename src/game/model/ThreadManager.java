package game.model;

import game.actors.Player;
import game.controller.GameController;
import game.threads.GameClock;
import game.threads.GameLoop;
import game.threads.GhostMovementThread;
import game.threads.PowerUpSpawner;
import game.view.GameSwingModel;
import game.view.GameView;

public class ThreadManager {

    private GameLoop gameLoop;
    private GhostMovementThread ghostMovementThread;
    private PowerUpSpawner powerUpSpawner;
    private GameClock gameClock;
    private GameSwingModel gameSwingModel;
    private GameState gameState;
    private Player player;
    private GameController gameController;
    private GameView gameView;
    private CollisionHandler collisionHandler;

    public ThreadManager(GameSwingModel gameSwingModel, Player player,GameState gameState, GameController gameController, GameView gameView) {
        this.gameSwingModel = gameSwingModel;
        this.player = player;
        this.gameState = gameState;
        this.gameController = gameController;
        this.gameView = gameView;
        this.collisionHandler = gameController.getCollisionHandler();
    }

    public void runThreads(){
        gameLoop = new GameLoop(gameSwingModel, player, gameState, gameController,gameView, this);
        gameLoop.start();
        ghostMovementThread = new GhostMovementThread(gameState,gameSwingModel,collisionHandler);
        ghostMovementThread.start();
        powerUpSpawner = new PowerUpSpawner(gameState, this);
        powerUpSpawner.start();
        gameClock = new GameClock(gameView, gameState, player, this);
        gameClock.start();
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void stopThreads(){
        gameLoop.stopLoop();
        ghostMovementThread.stopThread();
        powerUpSpawner.stopThread();
        gameClock.stopThread();
    }

    public int getTime(){
        return gameClock.getTime();
    }

    public void setTime(int time){
        gameClock.setTime(time);
    }
}
