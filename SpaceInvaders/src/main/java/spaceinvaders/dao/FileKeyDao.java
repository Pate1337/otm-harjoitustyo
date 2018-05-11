package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import spaceinvaders.domain.Key;

public class FileKeyDao implements KeyDao {
    private String file;
    private ArrayList<Key> keys;
    
    public FileKeyDao(String file) throws Exception {
        this.file = file;
        this.keys = new ArrayList<>();
        
        try {
            File keyFile = new File(file);
            if (!keyFile.exists()) {
                keyFile.createNewFile();
                fillFile(keyFile);
            }
            Scanner reader = new Scanner(keyFile);
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
                } else if (parts[0].equals("shoot")) {
                    Key.SHOOT.setKeyCode(parts[1]);
                    keys.add(Key.SHOOT);
                }
            }
            if (keys.size() == 0) {
                fillFile(keyFile);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            System.out.println("VIRHE");
            writer.close();
        }
    }
    
    public void fillFile(File keyFile) {
        try {
            FileWriter writer = new FileWriter(keyFile);
            writer.write("left=A\n");
            writer.write("up=W\n");
            writer.write("right=D\n");
            writer.write("down=S\n");
            writer.write("shoot=ENTER\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Can't write to file");
        }
        
    }
    
    @Override
    public ArrayList<Key> getAll() {
        return keys;
    }
    
    @Override
    public void update() {
        File originalFile = new File(this.file);
        File tempFile = new File("tempfile.txt");
        String line = null;
        try {
            Scanner reader = new Scanner(originalFile);
            FileWriter writer = new FileWriter(tempFile);
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("=");
                line = parts[0] + "=" + parts[1] + "\n";
                if (parts[0].equals("left")) {
                    if (!parts[1].equals(Key.LEFT.getKeyCode())) {
                        line = parts[0] + "=" + Key.LEFT.getKeyCode() + "\n";
                    }
                } else if (parts[0].equals("up")) {
                    if (!parts[1].equals(Key.UP.getKeyCode())) {
                        line = parts[0] + "=" + Key.UP.getKeyCode() + "\n";
                    }
                } else if (parts[0].equals("right")) {
                    if (!parts[1].equals(Key.RIGHT.getKeyCode())) {
                        line = parts[0] + "=" + Key.RIGHT.getKeyCode() + "\n";
                    }
                } else if (parts[0].equals("down")) {
                    if (!parts[1].equals(Key.DOWN.getKeyCode())) {
                        line = parts[0] + "=" + Key.DOWN.getKeyCode() + "\n";
                    }
                } else if (parts[0].equals("shoot")) {
                    if (!parts[1].equals(Key.SHOOT.getKeyCode())) {
                        line = parts[0] + "=" + Key.SHOOT.getKeyCode() + "\n";
                    }
                }
                writer.write(line);
            }
            reader.close();
            writer.close();
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            if (!tempFile.renameTo(originalFile)) {
                System.out.println("Could not rename file");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
