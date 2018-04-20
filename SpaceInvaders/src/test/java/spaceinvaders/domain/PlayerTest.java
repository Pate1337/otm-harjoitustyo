/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author paavo
 */
public class PlayerTest {
    Player player;
    
    @Before
    public void setUp() {
        player = new Player(400, 400);
    }
    @Test
    public void contructorWorks() {
        assertEquals(0, player.getMissiles().size());
        Rectangle2D playerRect = player.getBoundary();
        Rectangle2D rightRect = new Rectangle2D(446, 0, 354, 800);
        Rectangle2D leftRect = new Rectangle2D(0, 0, 399, 800);
        Rectangle2D upRect = new Rectangle2D(0, 0, 800, 399);
        Rectangle2D downRect = new Rectangle2D(0, 456, 800, 344);
        
        assertTrue(!playerRect.intersects(rightRect));
        assertTrue(!playerRect.intersects(leftRect));
        assertTrue(!playerRect.intersects(upRect));
        assertTrue(!playerRect.intersects(downRect));
    }
}
