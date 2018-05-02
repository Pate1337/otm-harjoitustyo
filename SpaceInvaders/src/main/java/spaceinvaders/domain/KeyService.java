/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import java.util.ArrayList;
import java.util.HashMap;
import spaceinvaders.dao.KeyDao;

/**
 * Luokan välityksellä käyttöliittymästä voidaan muuttaa pelin näppäinasetuksia.
 */
public class KeyService {
    private KeyDao keyDao;
    
    /**
     * Konstruktori alustaa käytetyn tietokantarajapinnan.
     * @param keyDao tietokannan käsittelyyn käytetty luokka, joka
     * toteuttaa rajapinnan KeyDao.
     * 
     * @see spaceinvaders.dao.KeyDao
     */
    public KeyService(KeyDao keyDao) {
        this.keyDao = keyDao;
    }
    /**
     * Metodi palauttaa tietokannasta haetut näppäimet.
     * @return lista näppäimistä
     */
    public ArrayList<Key> getKeys() {
        return this.keyDao.getAll();
    }
    /**
     * Metodi hakee näppäinten nimet (Left, Right, Up, Down, Shoot, jne.).
     * @return lista näppäinten nimistä.
     */
    public ArrayList<String> getKeyNames() {
        ArrayList<Key> keys = this.keyDao.getAll();
        ArrayList<String> keyNames = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            keyNames.add(keys.get(i).getKeyName());
        }
        return keyNames;
    }
    /**
     * Metodi kutsuu tietokannan käsittelyyn tarkoitetun luokan metodia update().
     * Parametreja ei ole, sillä päivitettävät tiedot tulee muuttaa Enum-luokan
     * Key metodeja käyttäen, ennen tämän metodin kutsua.
     * 
     * @see spaceinvaders.domain.Key
     */
    public void updateFile() {
        this.keyDao.update();
    }
}
