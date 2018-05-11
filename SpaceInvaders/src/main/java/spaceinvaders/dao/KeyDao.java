package spaceinvaders.dao;

import java.util.ArrayList;
import spaceinvaders.domain.Key;

/**
 *
 * @author paavo
 */
public interface KeyDao {
    ArrayList<Key> getAll();
    void update();
}
