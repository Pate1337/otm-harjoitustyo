/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import spaceInvaders.domain.Key;

/**
 *
 * @author paavo
 */
public class FileKeyDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
  
    File keyFile;  
    KeyDao dao;    
    
    @Before
    public void setUp() throws Exception {
        keyFile = testFolder.newFile("testfile_keys.txt");  
        
        try (FileWriter file = new FileWriter(keyFile.getAbsolutePath())) {
            file.write("left=D\n");
            file.write("right=G\n");
            file.write("up=R\n");
            file.write("down=F\n");
        }
        
        dao = new FileKeyDao(keyFile.getAbsolutePath());        
    }
    @Test
    public void keysAreReadCorrectlyFromFile() {
        List<Key> keys = dao.getAll();
        assertEquals(4, keys.size());
        Key key = keys.get(0);
        assertEquals(Key.LEFT, key);
        key = keys.get(1);
        assertEquals(Key.RIGHT, key);
        key = keys.get(2);
        assertEquals(Key.UP, key);
        key = keys.get(3);
        assertEquals(Key.DOWN, key);
    }
    @Test
    public void keysAreSetRightFromFile() {
        List<Key> keys = dao.getAll();
        Key key = keys.get(0);
        assertEquals(key.getKeyName(), "Left");
        assertEquals(key.getKeyCode(), "D");
        key = keys.get(1);
        assertEquals(key.getKeyName(), "Right");
        assertEquals(key.getKeyCode(), "G");
        key = keys.get(2);
        assertEquals(key.getKeyName(), "Up");
        assertEquals(key.getKeyCode(), "R");
        key = keys.get(3);
        assertEquals(key.getKeyName(), "Down");
        assertEquals(key.getKeyCode(), "F");
    }
}
