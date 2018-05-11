package spaceinvaders.domain;

import java.util.ArrayList;
import spaceinvaders.dao.HiScoresDao;

/**
 * Luokan välityksellä käyttöliittymästä päästään käsiksi tietokantaan,
 * johon huipputulokset on talletettu.
 */
public class ScoreService {
    private HiScoresDao hiScoresDao;
    
    /**
     * Konstruktorissa otetaan luokan käyttöön parametrina saatu,
     * tietokannan käsittelyyn suunniteltu luokka.
     * @param hiScoresDao Tietokannan käsittelyyn suunniteltu luokka (toteuttaa rajapinnan
     * HiScoresDao)
     * 
     * @see spaceinvaders.dao.HiScoresDao
     */
    public ScoreService(HiScoresDao hiScoresDao) {
        this.hiScoresDao = hiScoresDao;
    }
    
    /**
     * Metodi kutsuu HiScoresDao:n toteuttaman luokan metodia getAll()
     * ja tarkistaa, ettei listalla ole enempää kuin 10 huipputulosta.
     * @return lista, jossa korkeintaan 10 huipputulosta.
     * 
     * @see spaceinvaders.dao.FileHiScoresDao#getAll() 
     */
    public ArrayList<String> getScores() {
        ArrayList<String> scores = this.hiScoresDao.getAll();
        if (scores.size() <= 10) {
            return scores;
        } else {
            ArrayList<String> scoresToReturn = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                scoresToReturn.add(scores.get(i));
            }
            return scoresToReturn;
        }
    }
    
    /**
     * Metodi hakee rajatuloksen, jota suuremmalla scorella päästään huipputulos tietokantaan.
     * @return top10 rajapyykki
     */
    public int getLimit() {
        int limit = this.hiScoresDao.getLimit();
        return limit;
    }
    
    /**
     * Metodi kutsuu tietokannan käsittelyyn tarkoitetun luokan metodia update(String toAdd).
     * @param toAdd Tietokantaan kirjoitettava rivi
     * 
     * @see spaceinvaders.dao.FileHiScoresDao#update(java.lang.String) 
     */
    public void update(String toAdd) {
        this.hiScoresDao.update(toAdd);
    }
}
