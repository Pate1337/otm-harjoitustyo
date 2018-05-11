package spaceinvaders.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import spaceinvaders.domain.Key;

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
            file.write("shoot=ENTER\n");
        }
        
        dao = new FileKeyDao(keyFile.getAbsolutePath());
    }
    @Test
    public void keysAreReadCorrectlyFromFile() {
        List<Key> keys = dao.getAll();
        assertEquals(5, keys.size());
        Key key = keys.get(0);
        assertEquals(Key.LEFT, key);
        key = keys.get(1);
        assertEquals(Key.RIGHT, key);
        key = keys.get(2);
        assertEquals(Key.UP, key);
        key = keys.get(3);
        assertEquals(Key.DOWN, key);
        key = keys.get(4);
        assertEquals(Key.SHOOT, key);
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
        key = keys.get(4);
        assertEquals(key.getKeyName(), "Shoot");
        assertEquals(key.getKeyCode(), "ENTER");
    }
    @Test
    public void ifFileNotExistsANewFileIsCreatedAndFilled() throws Exception {
        String fileNotExist = "newfile.txt";
        File notExist = new File(fileNotExist);
        assertTrue(!notExist.exists());
        KeyDao newDao = new FileKeyDao(fileNotExist);
        assertTrue(notExist.exists());
        
        List<Key> keys = newDao.getAll();
        assertEquals(5, keys.size());
        notExist.delete();
    }
    
    @Test
    public void existingEmptyFileIsFilled() throws Exception {
        String empty = "emptyfile.txt";
        File emptyFile = new File(empty);
        KeyDao newDao = new FileKeyDao(empty);
        List<Key> keys = newDao.getAll();
        assertEquals(5, keys.size());
        emptyFile.delete();
    }
    @Test
    public void changedKeyCodeIsWrittenIntoFile() throws Exception {
        Key.LEFT.setKeyCode("LEFT");
        Key.RIGHT.setKeyCode("RIGHT");
        Key.UP.setKeyCode("UP");
        Key.DOWN.setKeyCode("DOWN");
        Key.SHOOT.setKeyCode("SPACE");
        dao.update();
        dao = new FileKeyDao(keyFile.getAbsolutePath());
        List<Key> keys = dao.getAll();
        assertEquals(5, keys.size());
        assertTrue(keys.get(0).getKeyCode().equals("LEFT"));
        assertTrue(keys.get(1).getKeyCode().equals("RIGHT"));
        assertTrue(keys.get(2).getKeyCode().equals("UP"));
        assertTrue(keys.get(3).getKeyCode().equals("DOWN"));
        assertTrue(keys.get(4).getKeyCode().equals("SPACE"));
    }
}