/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.dao;

import java.util.ArrayList;

/**
 *
 * @author paavo
 */
public interface HiScoresDao {
    ArrayList<String> getAll();
    void update();
}
