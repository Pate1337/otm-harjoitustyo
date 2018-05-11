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

public class FileHiScoresDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder(); 
    
    File scoreFile;  
    HiScoresDao dao;
    
    @Before
    public void setUp() throws Exception {
        scoreFile = testFolder.newFile("testfile_scores.txt");
        
        try (FileWriter file = new FileWriter(scoreFile.getAbsolutePath())) {
            file.write("HAXX;10020\n");
            file.write("gamer;1337\n");
            file.write("hiscore;55555\n");
        }
        
        dao = new FileHiScoresDao(scoreFile.getAbsolutePath());
    }
    
    @Test
    public void fileIsCreatedIfNotExist() {
        String file = "emptyhiscorefile.txt";
        File newFile = new File(file);
        assertTrue(!newFile.exists());
        HiScoresDao newDao = new FileHiScoresDao(file);
        assertTrue(newFile.exists());
        assertEquals(0, newDao.getAll().size());
        newFile.delete();
    }
    @Test
    public void scoresAreCorrectlyReadFromFile() {
        List<String> scores = dao.getAll();
        assertEquals(3, scores.size());
    }
    @Test
    public void scoresAreSortedCorrectly() {
        List<String> scores = dao.getAll();
        assertEquals("hiscore;55555", scores.get(0));
        assertEquals("HAXX;10020", scores.get(1));
        assertEquals("gamer;1337", scores.get(2));
    }
    @Test
    public void topLimitIsRetunedCorrectlyIfLessThanTenScores() {
        assertEquals(-1, dao.getLimit());
    }
    @Test
    public void topLimitIsReturnedCorrectlyIfTenOrMoreScores() throws Exception {
        String full = "fullhiscorefile.txt";
        File fullFile = new File(full);
        try (FileWriter file = new FileWriter(fullFile)) {
            file.write("HAXX;10020\n");
            file.write("gamer;1337\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("last;20\n");
        }
        HiScoresDao newDao = new FileHiScoresDao(full);
        assertEquals(20, newDao.getLimit());
        fullFile.delete();
    }
    @Test
    public void newTopScoreIsWrittenIntoFile() {
        String newTop = "new;20000";
        dao.update(newTop);
        assertEquals(4, dao.getAll().size());
    }
    @Test
    public void scoresAreInOrderAfterUpdate() {
        String newTop = "new;20000";
        dao.update(newTop);
        List<String> scores = dao.getAll();
        assertEquals(4, scores.size());
        assertEquals("hiscore;55555", scores.get(0));
        assertEquals("new;20000", scores.get(1));
        assertEquals("HAXX;10020", scores.get(2));
        assertEquals("gamer;1337", scores.get(3));
    }
    @Test
    public void sameScoreIsSetHigher() {
        String sameScore = "samescore;10020";
        dao.update(sameScore);
        List<String> scores = dao.getAll();
        assertEquals(4, scores.size());
        assertEquals("hiscore;55555", scores.get(0));
        assertEquals("samescore;10020", scores.get(1));
        assertEquals("HAXX;10020", scores.get(2));
        assertEquals("gamer;1337", scores.get(3));
    }
    @Test
    public void onlyTenScoresAreWrittenIntoFile() throws Exception {
        String full = "fullhiscorefilenew.txt";
        File fullFile = new File(full);
        try (FileWriter file = new FileWriter(fullFile)) {
            file.write("HAXX;10020\n");
            file.write("gamer;1337\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("hiscore;55555\n");
            file.write("last;20\n");
        }
        HiScoresDao newDao = new FileHiScoresDao(full);
        String newTop = "new;20000";
        newDao.update(newTop);
        assertEquals(11, newDao.getAll().size());
        newDao = new FileHiScoresDao(full);
        assertEquals(10, newDao.getAll().size());
        fullFile.delete();
    }
}
