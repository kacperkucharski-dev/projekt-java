package highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresModel {
    private static final String path = "highscores.dat";

    public List<ScoreEntry> load(){
        File file = new File(path);
        try {
            if(!file.exists()){
                file.createNewFile();
                return new ArrayList<>();
            }
            if(file.length()==0){
                return new ArrayList<>();
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return (List<ScoreEntry>) ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void save(List<ScoreEntry> scores){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(scores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addScore(ScoreEntry score){
        List<ScoreEntry> scores = load();
        scores.add(score);
        scores.sort(Collections.reverseOrder());
        save(scores);
    }
}
