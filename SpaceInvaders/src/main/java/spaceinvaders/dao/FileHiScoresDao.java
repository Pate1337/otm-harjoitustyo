package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author paavo
 */
public class FileHiScoresDao implements HiScoresDao {
    private String file;
    private ArrayList<String> scores;
    
    public FileHiScoresDao(String file) {
        this.file = file;
        this.scores = new ArrayList<>();
        try {
            File scoreFile = new File(file);
            if (!scoreFile.exists()) {
                scoreFile.createNewFile();
            }
            Scanner reader = new Scanner(scoreFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                scores.add(line);
            }
        } catch (Exception e) {
            System.out.println("virhe");
        }
        sortScores();
        
    }

    @Override
    public ArrayList<String> getAll() {
        return scores;
    }
    
    public void sortScores() {
        scores.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String score1 = s1.split(";")[1];
                String score2 = s2.split(";")[1];
                if (Integer.parseInt(score1) == Integer.parseInt(score2)) {
                    return -1;
                }
                if (Integer.parseInt(score1) > Integer.parseInt(score2)) {
                    return -1;
                }
                return 1;
            }
        });
    }
    
    @Override
    public int getLimit() {
        if (scores.size() < 10) {
            return -1;
        }
        String line = scores.get(9);
        int score = Integer.parseInt(line.split(";")[1]);
        return score;
    }

    @Override
    public void update(String text) {
        scores.add(text);
        sortScores();
        File originalFile = new File(file);
        File tempFile = new File("temphiscores.txt");
        try {
            FileWriter writer = new FileWriter(tempFile);
            for (int i = 0; i < scores.size(); i++) {
                writer.write(scores.get(i) + "\n");
                if (i == 9) {
                    break;
                }
            }
            writer.close();
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            if (!tempFile.renameTo(originalFile)) {
                System.out.println("Could not rename file");
            }
        } catch (Exception e) {
            System.out.println("Can't write to file");
        }
    }
}
