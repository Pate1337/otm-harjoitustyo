/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.dao;

import java.io.File;
import java.util.ArrayList;
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
        
    }

    @Override
    public ArrayList<String> getAll() {
        return scores;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
