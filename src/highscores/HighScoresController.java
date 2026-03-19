package highscores;

public class HighScoresController{
    private HighScoresView view;
    private HighScoresModel model;

    public HighScoresController(HighScoresView view, HighScoresModel model){
        this.view = view;
        this.model = model;
    }

    public void addScore(String name,int score){
        ScoreEntry entry = new ScoreEntry(score,name);
        model.addScore(entry);
        refresh();
    }

    public void refresh(){
        view.update(model.load());
    }

    public void showHighScores(){
        refresh();
        view.setVisible(true);
    }
}
