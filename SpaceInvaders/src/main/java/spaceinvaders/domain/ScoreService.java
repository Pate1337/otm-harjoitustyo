/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import java.util.ArrayList;
import spaceinvaders.dao.HiScoresDao;

/**
 *
 * @author paavo
 */
public class ScoreService {
    private HiScoresDao hiScoresDao;
    
    public ScoreService(HiScoresDao hiScoresDao) {
        this.hiScoresDao = hiScoresDao;
    }
    
    public ArrayList<String> getScores() {
        return this.hiScoresDao.getAll();
    }
}
