package menu;

import javax.swing.*;
import java.awt.*;

public class Logo extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPacman((Graphics2D)g);
    }

    private void drawPacman(Graphics2D g) {
        int size = Math.min(getWidth(), getHeight()) - 100;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g.setColor(Color.YELLOW);
        g.fillArc(x, y, size, size, 45,270);
    }
}
