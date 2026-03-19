package game.model;

import game.actors.Ghost;
import game.actors.Player;
import game.powerups.PowerUp;
import game.threads.GameLoop;
import utils.CellType;
import utils.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameState {
    private int rows;
    private int columns;
    private CellType[][] board;
    private List<Ghost> ghosts;
    private Map<Point,PowerUp> powerUps;
    private Player player;
    private int score;
    private int level = 1;
    private int dots = 0;
    private MazeGenerator maze;
    private int time;

    public GameState(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        allocateTime();
        this.board = new CellType[rows][columns];
        this.ghosts = new ArrayList<>();
        this.score = 0;
        createPlayer();
        this.maze = new MazeGenerator(this);
        maze.generate();
        if(columns%2!=0) createGhost(new Point(1,columns - 2));
        else createGhost(new Point(1,columns - 3));
        if(rows%2!=0) createGhost(new Point(rows - 2, 1));
        else createGhost(new Point(rows - 3, 1));
        if(rows%2!=0&&columns%2!=0) createGhost(new Point(rows - 2, columns - 2));
        else if(rows%2!=0&&columns%2==0) createGhost(new Point(rows - 2, columns - 3));
        else if(rows%2==0&&columns%2!=0) createGhost(new Point(rows - 3, columns - 2));
        else createGhost(new Point(rows - 3, columns - 3));
        this.powerUps = new HashMap<>();
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public CellType[][] getBoard() { return board; }
    public CellType getCellType(int row, int column) {
        return board[row][column];
    }
    public void setCellType(int row, int column, CellType cellType) {
        board[row][column] = cellType;
    }
    public Player getPlayer() { return player; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public void createPlayer() { player = new Player(new Point(1,1));}

    public void createGhost(Point position) {
        Ghost g = new Ghost(position, Direction.RIGHT);
        ghosts.add(g);
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public boolean isGhost(int row, int column) {
        for(Ghost g : ghosts) {
            if(g.getPosition().equals(new Point(row,column))){
                return true;
            }
        }
        return false;
    }

    public int getLevel(){ return level; }

    public void setLevel(int level){ this.level = level; }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    public MazeGenerator getMaze() {
        return maze;
    }

    public int getTime() {
        return time;
    }

    public void allocateTime(){
        int size = rows * columns;
        if(size>50*50) time = 1000;
        else if(size>25*25) time = 500;
        else time = 300;
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.put(powerUp.getPosition(), powerUp);
        if(board[powerUp.getPosition().x][powerUp.getPosition().y]==CellType.DOT){
            powerUp.setOverridenDot(true);
        }
        board[powerUp.getPosition().x][powerUp.getPosition().y] = CellType.POWER;
    }

    public PowerUp getPowerUp(Point position) {
        return powerUps.get(position);
    }

    public void removePowerUp(Point position) {
        powerUps.remove(position);
    }

    public Map<Point, PowerUp> getPowerUps() {
        return powerUps;
    }
}
