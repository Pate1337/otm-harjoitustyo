/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import spaceInvaders.domain.Key;

/**
 *
 * @author paavo
 */
public class FileKeyDao implements KeyDao {
    private String file;
    private ArrayList<Key> keys;
    
    public FileKeyDao(String file) throws Exception {
        this.file = file;
        this.keys = new ArrayList<>();
        
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("=");
                if (parts[0].equals("left")) {
                    Key.LEFT.setKeyCode(parts[1]);
                    keys.add(Key.LEFT);
                } else if (parts[0].equals("up")) {
                    Key.UP.setKeyCode(parts[1]);
                    keys.add(Key.UP);
                } else if (parts[0].equals("right")) {
                    Key.RIGHT.setKeyCode(parts[1]);
                    keys.add(Key.RIGHT);
                } else if (parts[0].equals("down")) {
                    Key.DOWN.setKeyCode(parts[1]);
                    keys.add(Key.DOWN);
                }
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    
    @Override
    public ArrayList<Key> getAll() {
        return keys;
    }
}
