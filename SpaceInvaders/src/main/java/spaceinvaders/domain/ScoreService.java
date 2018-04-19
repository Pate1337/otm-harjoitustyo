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
        ArrayList<String> scores = this.hiScoresDao.getAll();
//        return this.hiScoresDao.getAll();
        if (scores.size() <= 10) {
            return scores;
        } else {
            //Siltä varalta jos hiscores-tiedostoa muokataan käsin.
            ArrayList<String> scoresToReturn = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                scoresToReturn.add(scores.get(i));
            }
            return scoresToReturn;
        }
    }
    //Tähän 10.pisteet
    public int getLimit() {
        int limit = this.hiScoresDao.getLimit();
        return limit;
    }
    
    public void update(String toAdd) {
        this.hiScoresDao.update(toAdd);
    }
}
