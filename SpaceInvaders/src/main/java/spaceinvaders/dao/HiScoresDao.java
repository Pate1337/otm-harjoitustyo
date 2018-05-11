package spaceinvaders.dao;

import java.util.ArrayList;

/**
 *
 * @author paavo
 */
public interface HiScoresDao {
    ArrayList<String> getAll();
    void update(String text);
    int getLimit();
}
