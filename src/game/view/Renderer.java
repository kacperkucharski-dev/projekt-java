package game.view;

import game.model.GameState;
import game.threads.GameLoop;
import utils.CellType;
import utils.Direction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer {

    private CellType type;
    private static Direction direction = Direction.RIGHT;
    private GameState gameState;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public static void setDirection(Direction direction) {
        Renderer.direction = direction;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(null);
        setBorder(null);
        type = (CellType)value;

        if(gameState != null && gameState.isGhost(row, column)) {
            type = CellType.GHOST;
        }

        setOpaque(true);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(type==null) return;

        Graphics2D g2d = (Graphics2D)g;
        int size = Math.min(getWidth(), getHeight());

        if(type == CellType.WALL) setBackground(Color.BLUE);
        else setBackground(Color.BLACK);

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        switch(type) {
            case DOT:
                setBackground(Color.BLACK);
                g2d.setColor(Color.GREEN);
                g2d.fillOval(size / 2 - 2, size / 2 - 2, 4, 4);
                break;
            case POWER:
                setBackground(Color.BLACK);
                g2d.setColor(Color.CYAN);
                g2d.fillOval(size / 2 - 4, size / 2 - 4, 12, 12);
                break;
            case GHOST:
                setBackground(Color.BLACK);
                Color color = Color.MAGENTA;
                if(gameState.getGhosts().get(0).isFrozen()) color = Color.CYAN;
                if(gameState.getGhosts().get(0).isEdible()) color = Color.GREEN;
                drawGhost(g2d,size,color);
                break;
            case PACMAN:
                setBackground(Color.BLACK);
                if(gameState.getPlayer().isImmune()) g2d.setColor(Color.RED);
                else g2d.setColor(Color.YELLOW);
                int angle = 30 + GameLoop.getpState() * 12;
                int startangle;

                switch(direction){
                    case RIGHT: startangle = angle / 2; break;
                    case UP: startangle = 90 + angle / 2; break;
                    case LEFT: startangle = 180+ angle / 2; break;
                    case DOWN: startangle = 270 + angle / 2; break;
                    default: startangle = angle / 2;
                }

                g2d.fillArc(0,0,size,size, startangle,360-angle);
                break;
            default:
                setBackground(Color.BLACK);
        }
    }

    private void drawGhost(Graphics2D g2d, int size, Color color) {
        g2d.setColor(color);
        g2d.fillArc(0,0,size,size,0,180);
        g2d.fillRect(0,size/2,size,size/2);

        int eyeWidth = size/5;
        int eyeHeight = size/4;

        int leftEye = size/4-eyeWidth/2;
        int rightEye = size/4+eyeWidth;

        g2d.setColor(Color.WHITE);
        g2d.fillOval(leftEye,size/6,eyeWidth,eyeHeight);
        g2d.fillOval(rightEye,size/6,eyeWidth,eyeHeight);

        g2d.setColor(Color.BLACK);
        g2d.fillOval(leftEye+eyeWidth-7,size/6+2,eyeWidth/2,eyeHeight/2);
        g2d.fillOval(rightEye+eyeWidth-7,size/6+2,eyeWidth/2,eyeHeight/2);
    }
}
