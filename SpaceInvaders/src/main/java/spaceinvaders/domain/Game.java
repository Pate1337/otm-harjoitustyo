/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author paavo
 */
public class Game {
    private int points;
    private Player player;
    private CountDown countDown;
    private Timer levelTimer;
    private Timer enemyTimer;
    private int rate = 3000;
    private ArrayList<Enemy> enemies;
    private int lifes;
    
    public Game() {
        this.points = 0;
        this.player = new Player(400, 700);
        this.countDown = new CountDown();
        this.enemies = new ArrayList<>();
//        Utils.playSound("utilities/sounds/alarm.wav");
//        Utils.playSound("utilities/sounds/three.wav");
        Utils.playAlarm();
        Utils.playThree();
        this.levelTimer = new Timer();
        levelTimer.schedule(levelTimerTask(), 20000, 20000);
        this.enemyTimer = new Timer();
        enemyTimer.schedule(enemyTimerTask(), 0, this.rate);
        this.lifes = 3;
    }
    
    private TimerTask levelTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("Muutetaan leveliä");
                rate = (2 * rate) / 3;
                enemyTimer.cancel();
                
                enemyTimer = new Timer();
                enemyTimer.schedule(enemyTimerTask(), 0, rate);
            }
        };
    }
    
    private TimerTask enemyTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if (countDown.ready()) {
                    enemies.add(new Enemy());
                }
            }
        };
    }
    
    public void update(double time) {
        if (time != 0 && !countDown.ready()) {
            this.countDown.update(time);
            this.player.update(time);
        } else if (time != 0) {
            this.player.update(time);
            //Ja tähän sit mylös vihollisalukset
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update(time);
            }
        }
    }
    //Tätä pitää vielä korjata. Aiheuttaa java.util.ConcurrentModificationException
    public void collisions() {
        Iterator<Missile> missileIterator = player.getMissiles().iterator();
        //Tässä ongelma. Jos missileitä ei ole, niin ei päästä poistamaan tuhottua vihollisalusta(fixed)
        if (missileIterator.hasNext()) {
            while (missileIterator.hasNext()) {
                Missile missile = missileIterator.next();
                if (missile.destroyed()) {
                    missileIterator.remove();
                    continue;
                }
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (enemy.destroyed()) {
                        if (enemy.getPositionY() == 800) {
                            lifes--;
                            if (lifes == 0) {
                                return;
                            }
                        }
                        enemyIterator.remove();
                        continue;
                    }
                    if (missile.intersects(enemy)) {
                        missileIterator.remove();
//                      enemyIterator.remove();
                        enemy.explode();
                        points++;
                        //Ei katsota enää muita vihollisia. Kyrvähtää muuten.
                        break;
                    }
                }
            }
        } else {
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (enemy.destroyed()) {
                    if (enemy.getPositionY() == 800) {
                        lifes--;
                        if (lifes == 0) {
                            return;
                        }
                    }
                    enemyIterator.remove();
                }
            }
        }
    }
    public void addPlayerVelocity(double velocityX) {
        this.player.addVelocity(velocityX);
    }
    public void render(GraphicsContext gc) {
        this.player.render(gc);
        if (!countDown.ready()) {
            this.countDown.render(gc);
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).render(gc);
            }
//            Iterator<Enemy> enemyIterator = enemies.iterator();
//            while (enemyIterator.hasNext()) {
//                Enemy enemy = enemyIterator.next();
//                if (enemy.destroyed()) {
//                    enemyIterator.remove();
//                } else {
//                    enemy.render(gc);
//                }
//            }
        }
    }
    public void playerShoot() {
        this.player.shoot();
    }
    public int enemyCount() {
        return enemies.size();
    }
    public int getPoints() {
        return points;
    }
    public int getLifes() {
        return lifes;
    }
    public void endGame() {
        System.out.println("Peli päättyi");
        levelTimer.cancel();
        enemyTimer.cancel();
    }
//    public int missileCount() {
//        return player.missileCount();
//    }
}
