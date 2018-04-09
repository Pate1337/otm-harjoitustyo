/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import java.util.ArrayList;
import java.util.HashMap;
import spaceinvaders.dao.KeyDao;

/**
 *
 * @author paavo
 */
public class KeyService {
    private KeyDao keyDao;
    
    public KeyService(KeyDao keyDao) {
        this.keyDao = keyDao;
    }
    
    public ArrayList<Key> getKeys() {
        return this.keyDao.getAll();
    }
    
    public ArrayList<String> getKeyNames() {
        ArrayList<Key> keys = this.keyDao.getAll();
        ArrayList<String> keyNames = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            keyNames.add(keys.get(i).getKeyName());
        }
        return keyNames;
    }
}
