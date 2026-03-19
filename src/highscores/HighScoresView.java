package highscores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class HighScoresView extends JFrame {

    private DefaultListModel<ScoreEntry> model = new DefaultListModel<>();
    private JList<ScoreEntry> list = new JList<>(model);

    public HighScoresView() {
        super("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.YELLOW);
        title.setBorder(new EmptyBorder(0,0,10,0));
        panel.add(title, BorderLayout.NORTH);

        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setBackground(new Color(25,25,25));
        list.setForeground(Color.YELLOW);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.YELLOW),
                "Best players",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.YELLOW
        ));
        scrollPane.setBackground(new Color(25,25,25));

        panel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panel);

        pack();
        setLocationRelativeTo(null);
    }

    public void update(List<ScoreEntry> scores){
        model.clear();
        for(ScoreEntry s : scores){
            model.addElement(s);
        }
    }
}
