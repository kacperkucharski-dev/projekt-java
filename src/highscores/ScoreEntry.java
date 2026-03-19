package highscores;

import java.io.Serializable;

public class ScoreEntry implements Serializable, Comparable<ScoreEntry> {

    private final int score;
    private final String name;

    public ScoreEntry(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(ScoreEntry o) {
        return this.score - o.getScore();
    }

    @Override
    public String toString() {
        return name + " - " + score;
    }
}
