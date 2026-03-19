package menu;

import app.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private Application app;

    public MainMenuController(Application app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New Game")) {
            askSize();
        }
        else if(e.getActionCommand().equals("High Scores")) {
            app.showHighScores();
        }
        else if(e.getActionCommand().equals("Exit")) {
            app.exit();
        }
    }

    public void askSize(){
        SizeDialog s = new SizeDialog(app.getMainMenuView(), "Podaj wymiary planszy");
        if(s.isConfimed()){
            int a = s.getValue1();
            int b = s.getValue2();
            JOptionPane.showMessageDialog(
                    app.getMainMenuView(),
                    "Wymiary: "+a+"x"+b,
                    "Plansza utworzona poprawnie",
                    JOptionPane.INFORMATION_MESSAGE
            );
            app.startNewGame(b,a);
        }
    }
}
