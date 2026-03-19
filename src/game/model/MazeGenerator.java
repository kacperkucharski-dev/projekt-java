package game.model;

import game.actors.Player;
import utils.CellType;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MazeGenerator {
    private GameState gameState;
    private int dots = -1;

    public MazeGenerator(GameState gameState) {
        this.gameState = gameState;
    }

    public void generateEmptyBoard(){ //legacy - użyte przy testowaniu
        for(int y = 0; y < gameState.getRows(); y++){
            for(int x = 0; x< gameState.getColumns(); x++){
                if(y == 0 || x == 0 || y == gameState.getRows()-1 || x == gameState.getColumns() - 1) gameState.getBoard()[y][x] = CellType.WALL;
                else gameState.getBoard()[y][x] = CellType.DOT;
            }
        }
        gameState.getBoard()[gameState.getPlayer().getPosition().y][gameState.getPlayer().getPosition().x] = CellType.PACMAN;
    }

    public void generate(){
        int rows = gameState.getRows();
        int columns = gameState.getColumns();
        CellType[][] board = gameState.getBoard();

        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++){
                board[y][x] = CellType.WALL;
            }
        }

        carveMaze(1,1);
        breakWalls(0.3);

        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++){
                if(board[y][x] == CellType.EMPTY){
                    board[y][x] = CellType.DOT;
                    dots++;
                }
            }
        }
        gameState.setDots(dots);
        gameState.getPlayer().setPosition(new Point(1,1));
        gameState.setCellType(1,1,CellType.PACMAN);
        dots = -1;
    }

    private void carveMaze(int row, int column){
        CellType[][] board = gameState.getBoard();
        int rows = gameState.getRows();
        int columns = gameState.getColumns();
        int[][] directions = {{0,2},{0,-2},{2,0},{-2,0}};
        Collections.shuffle(Arrays.asList(directions));

        board[row][column] = CellType.EMPTY;
        for(int[] direction : directions){
            int x = row+direction[0];
            int y = column+direction[1];

            if(x>0 && x<rows-1 && y>0 && y<columns-1 && board[x][y] == CellType.WALL){
                board[row+direction[0]/2][column+direction[1]/2] = CellType.EMPTY;
                carveMaze(x, y);
            }
        }
    }

    private void breakWalls(double chance){
        CellType[][] board = gameState.getBoard();
        int rows = gameState.getRows();
        int columns = gameState.getColumns();
        Random rand = new Random();

        for(int y = 1; y < rows-1; y++){
            for(int x = 1; x < columns-1; x++){
                if(board[y][x] == CellType.WALL){
                    boolean pion = board[y][x-1] != CellType.WALL && board[y][x+1] != CellType.WALL;
                    boolean poziom = board[y-1][x] != CellType.WALL && board[y+1][x] != CellType.WALL;
                    if((pion || poziom) && rand.nextDouble() < chance){
                        board[y][x] = CellType.DOT;
                        dots++;
                    }
                }
            }
        }
    }
}
