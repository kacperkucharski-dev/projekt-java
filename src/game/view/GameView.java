package game.view;

import app.Application;
import game.controller.GameController;
import game.model.CollisionHandler;
import game.model.GameState;
import utils.CellType;
import utils.Direction;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.util.zip.GZIPInputStream;

public class GameView extends JFrame {
    GameController gameController;
    GameSwingModel swingModel;
    GameState gameState;
    Application app;
    JTable gameTable;
    JPanel gamePanel;
    JScrollPane scrollPane;
    JLabel scoreLabel, livesLabel, timeLabel;
    private final static int minCellSize = 16;

    public GameView(GameController gameController, GameSwingModel swingModel, Application app) {
        super("The game");
        this.gameController = gameController;
        this.swingModel = swingModel;
        this.app = app;
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //upper-display
        JPanel upperPanel = new JPanel(new FlowLayout(SwingConstants.LEFT));
        upperPanel.setBackground(Color.BLACK);

        scoreLabel = new JLabel("Score: "+gameState.getScore()*100);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.YELLOW);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        livesLabel.setForeground(Color.YELLOW);

        timeLabel = new JLabel("Time: 300"); ;
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setForeground(Color.YELLOW);

        upperPanel.add(scoreLabel);
        upperPanel.add(livesLabel);
        upperPanel.add(timeLabel);

        add(upperPanel, BorderLayout.NORTH);

        //game-display
        gameTable = new JTable(swingModel);
        gameTable.setBackground(Color.BLACK);
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Renderer renderer = new Renderer();
        renderer.setGameState(gameState);

        gameTable.setPreferredScrollableViewportSize(new Dimension(gameState.getColumns()*minCellSize, gameState.getRows()*minCellSize));

        gameTable.setDefaultRenderer(Object.class, renderer);
        gameTable.setShowGrid(false);
        gameTable.setTableHeader(null);
        gameTable.setIntercellSpacing(new Dimension(0, 0));

        scrollPane = new JScrollPane(gameTable);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeCells();
            }
        });

        gameTable.addKeyListener(gameController);

        gameTable.setFocusable(true);
        SwingUtilities.invokeLater(() -> gameTable.requestFocus());
        pack();
        setLocationRelativeTo(null);
    }

    public void addGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Application getApp() {
        return app;
    }

    public GameSwingModel getSwingModel() {
        return swingModel;
    }

    public void showView(){ setVisible(true); }

    public void updateHUD(int score, int lives, int time){
        scoreLabel.setText("Score: "+score*100);
        livesLabel.setText("Lives: "+lives);
        timeLabel.setText("Time: "+(gameState.getTime()-time));
    }

    public void close(){ dispose(); }

    private void resizeCells(){
        Dimension size = scrollPane.getViewport().getSize();
        int width = size.width;
        int height = size.height;
        int cellWidth = width/gameTable.getColumnCount();
        int cellHeight = height/gameTable.getRowCount();
        int cellSize = Math.min(cellWidth, cellHeight);

        if(cellSize<minCellSize) cellSize = minCellSize;

        gameTable.setRowHeight(cellSize);

        TableColumnModel columnModel = gameTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(cellSize);
            column.setMinWidth(cellSize);
        }
    }

    public void gameOverSequence(String title){
        SwingUtilities.invokeLater(() -> {
           int score = gameState.getScore()*100;
           int level = gameState.getLevel();

           JPanel panel = new JPanel(new GridLayout(4,1,10,10));
           panel.add(new JLabel("Twój wynik: "+score));
           panel.add(new JLabel("Poziom: "+level));

           JTextField field = new JTextField();
           panel.add(new JLabel("Podaj nick:"));
           panel.add(field);

           int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

           if(result == JOptionPane.OK_OPTION){
               String name = field.getText();
               if (!name.isEmpty()) {
                   app.enterHighScore(name, score);
                   app.showMainMenu();
                   app.closeGame();
               }
               else{
                   JOptionPane.showMessageDialog(this, "Nick nie może być pusty");
                   gameOverSequence(title);
               }
           }
           else{
               app.showMainMenu();
               app.closeGame();
           }
        });
    }
}
