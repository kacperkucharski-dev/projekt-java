package menu;

import app.Application;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    private MainMenuController controller;
    private JButton newGameButton, highScoresButton, exitButton;

    public MainMenuView(Application app) {
        super("Pac-Man: Main Menu");
        this.controller = new MainMenuController(app);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Logo logo = new Logo();
        logo.setBackground(Color.BLACK);
        add(logo, BorderLayout.CENTER);
        JLabel title = new JLabel("Pac-Man", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.YELLOW);
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(title, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));

        newGameButton = createButton("New Game", Color.YELLOW, Color.BLACK);
        newGameButton.addActionListener(controller);

        highScoresButton = createButton("High Scores", Color.YELLOW, Color.BLACK);
        highScoresButton.addActionListener(controller);

        exitButton = createButton("Exit", Color.YELLOW, Color.BLACK);
        exitButton.addActionListener(controller);

        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        panel.add(logo, BorderLayout.CENTER);
        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setSize(600,600);
        setLocationRelativeTo(null);
    }

    private JButton createButton(String text, Color color, Color color2) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(color);
        button.setBackground(color2);
        button.setBorder(BorderFactory.createEmptyBorder(10,15,5,15));
        button.setActionCommand(text);
        return button;
    }

    public void showView(){
        setVisible(true);
    }

    public void close(){
        setVisible(false);
    }
}
