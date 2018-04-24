/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author paavo
 */
public class EnemyTest {
    Enemy enemyNormal;
    Enemy enemyBonus;
    double width;
    double height;
    GraphicsContext gc;
    
    @Before
    public void setUp() {
        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
    }
    
    @Test
    public void constructorWorksOnNormalEnemy() {
        Rectangle2D startRectangle = new Rectangle2D(0, 0, 800, 40);
        
        //Kokeillaan 1000 vihollisella
        for (int i = 0; i < 1000; i++) {
            enemyNormal = new Enemy("normal");
            //Alkusijainti on satunnainen välillä [0, 750]
            assertTrue(startRectangle.contains(enemyNormal.getBoundary()));
        }
        assertTrue(enemyNormal.getPositionY() == 0);
        assertTrue(!enemyNormal.destroyed());
        assertEquals("normal", enemyNormal.getType());
    }
    
    @Test
    public void consructorWorksOnBonusEnemy() {
        for (int i = 0; i < 1000; i++) {
            enemyBonus = new Enemy("bonus");
            //Alkusijainti on joko (-70, 150) tai (800, 150)
            assertTrue(enemyBonus.getBoundary().contains(-70, 150, 70, 40) || enemyBonus.getBoundary().contains(800, 150, 70, 40));
        }
        assertTrue(!enemyBonus.destroyed());
        assertEquals("bonus", enemyBonus.getType());
    }
    
    @Test
    public void whenNormalEnemyIsUpdatedItsPositionYChanges() {
        enemyNormal = new Enemy("normal");
        double positionY = enemyNormal.getPositionY();
        
        enemyNormal.update(0.15);
        //velocityY = 200, 200 * 0.15 = 30
        assertTrue(enemyNormal.getPositionY() == positionY + 30);
    }
    @Test
    public void whenNormalEnemyGoesTooLowItsPositionWillStaySame() {
        enemyNormal = new Enemy("normal");
        while (true) {
            enemyNormal.update(0.15);
            if (enemyNormal.getPositionY() >= 760) {
                break;
            }
        }
        //PositionY is set to 800 and positionX is set to 0.
        assertTrue(enemyNormal.getBoundary().contains(0, 800, 50, 40));
        enemyNormal.update(0.15);
        assertTrue(enemyNormal.getBoundary().contains(0, 800, 50, 40));
    }
    @Test
    public void whenBonusEnemyGoesOutOfScreenItIsSetToDetroyed() {
        enemyBonus = new Enemy("bonus");
        Rectangle2D outOfBounds = null;
        //Jos lähtö vasemmalta
        if (enemyBonus.getBoundary().contains(-70, 150, 70, 40)) {
            outOfBounds = new Rectangle2D(801, 150, 200, 40);
            while (true) {
                enemyBonus.update(0.15);
                if (outOfBounds.contains(enemyBonus.getBoundary())) {
                    break;
                }
            }
        } else {
            outOfBounds = new Rectangle2D(-300, 150, 229, 40);
            while (true) {
                enemyBonus.update(0.15);
                if (outOfBounds.contains(enemyBonus.getBoundary())) {
                    break;
                }
            }
        }
        assertTrue(enemyBonus.destroyed());
    }
    @Test
    public void whenEnemyIsSetToExploadItsPositionIsSetCorrectly() {
        enemyNormal = new Enemy("normal");
        enemyNormal.explode();
        assertTrue(enemyNormal.getBoundary().contains(0, 850, 50, 40));
        
        enemyBonus = new Enemy("bonus");
        enemyBonus.explode();
        assertTrue(enemyBonus.getBoundary().contains(0, 850, 50, 40));
    }
}
