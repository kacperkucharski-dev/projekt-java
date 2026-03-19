package app;

import game.controller.GameController;
import game.model.GameState;
import game.model.ThreadManager;
import game.view.GameSwingModel;
import game.view.GameView;
import highscores.HighScoresController;
import highscores.HighScoresModel;
import highscores.HighScoresView;
import menu.MainMenuController;
import menu.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Application {
    private MainMenuController mainMenuController;
    private MainMenuView mainMenuView;

    private HighScoresController highScoresController;
    private HighScoresModel highScoresModel;
    private HighScoresView highScoresView;

    private GameController gameController;
    private GameView gameView;
    private GameSwingModel gameSwingModel;
    private GameState gameState;
    private ThreadManager threadManager;

    public void start(){
        this.showMainMenu();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getID() == KeyEvent.KEY_PRESSED) {
                    if(e.getKeyCode() == KeyEvent.VK_Q && e.isControlDown() && e.isShiftDown()) {
                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                "Czy na pewno chcesz wrócić do menu głównego?",
                                "Wciśnięto CTRL+SHIFT+Q",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if(choice == JOptionPane.YES_OPTION) {
                            closeGame();
                            closeHighScores();
                            showMainMenu();
                        }
                    }
                }
                return false;
            }
        });
    }

    public void showMainMenu(){
        if(mainMenuView == null) mainMenuView = new MainMenuView(this);
        mainMenuView.showView();
        SwingUtilities.invokeLater(()->mainMenuView.requestFocus());
    }

    public void showHighScores(){
        if(highScoresView == null) {
            highScoresView = new HighScoresView();
            highScoresModel = new HighScoresModel();
            highScoresController = new HighScoresController(highScoresView, highScoresModel);
        }
        highScoresController.showHighScores();
        SwingUtilities.invokeLater(()->highScoresView.requestFocus());
        closeGame();
        mainMenuView.close();
    }

    public void startNewGame(int rows, int columns){
        gameState = new GameState(rows,columns);
        gameSwingModel = new GameSwingModel(gameState);
        gameController = new GameController(gameState);
        gameView = new GameView(gameController, gameSwingModel, this);
        gameView.addGameState(gameState);
        gameView.initialize();
        gameView.showView();
        gameController.addGameView(gameView);
        threadManager = new ThreadManager(gameSwingModel,gameState.getPlayer(),gameState, gameController,gameView);
        threadManager.runThreads();
        mainMenuView.close();
    }

    public void closeGame(){
        if(gameView != null) {
            gameView.close();
            threadManager.stopThreads();
        }
    }

    public void closeHighScores(){
        if(highScoresView == null) {
            highScoresView = new HighScoresView();
            highScoresModel = new HighScoresModel();
            highScoresController = new HighScoresController(highScoresView, highScoresModel);
        }
        highScoresView.setVisible(false);
    }

    public void exit(){ System.exit(0); }

    public MainMenuView getMainMenuView(){
        return mainMenuView;
    }

    public void enterHighScore(String name, int score){
        if(highScoresView == null) {
            highScoresView = new HighScoresView();
            highScoresModel = new HighScoresModel();
            highScoresController = new HighScoresController(highScoresView, highScoresModel);
        }
        highScoresController.addScore(name, score);
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }
}
