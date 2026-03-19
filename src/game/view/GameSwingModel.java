package game.view;

import game.model.GameState;

import javax.swing.table.AbstractTableModel;

public class GameSwingModel extends AbstractTableModel {
    private GameState gameState;

    public GameSwingModel(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public int getRowCount() {
        return gameState.getRows();
    }

    @Override
    public int getColumnCount() {
        return gameState.getColumns();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gameState.getCellType(rowIndex, columnIndex);
    }
}
