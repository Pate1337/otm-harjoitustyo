/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import spaceInvaders.domain.Key;

/**
 *
 * @author paavo
 */
public class FileKeyDao {
    private String file;
    
    public FileKeyDao(String file) throws Exception {
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("=");
                if (parts[0].equals("left")) {
                    Key.LEFT.setKeyCode(parts[1]);
                } else if (parts[0].equals("up")) {
                    Key.UP.setKeyCode(parts[1]);
                } else if (parts[0].equals("right")) {
                    Key.RIGHT.setKeyCode(parts[1]);
                } else if (parts[0].equals("down")) {
                    Key.DOWN.setKeyCode(parts[1]);
                }
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
}
