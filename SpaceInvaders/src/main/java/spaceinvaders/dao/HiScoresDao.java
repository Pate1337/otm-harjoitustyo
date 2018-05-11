package spaceinvaders.dao;

import java.util.ArrayList;

public interface HiScoresDao {
    ArrayList<String> getAll();
    void update(String text);
    int getLimit();
}
