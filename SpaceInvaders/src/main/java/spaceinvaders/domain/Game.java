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
    private int prevPoints; //Nämä vaan timerin takia
    private Player player;
    private CountDown countDown;
    private Timer enemyTimer;
    private int rate = 3000;
    private ArrayList<Enemy> enemies;
    private int lifes;
    private boolean paused;
    private int score;
    
    public Game() {
        this.points = 0;
        this.prevPoints = 0;
        this.score = 0;
        this.paused = false;
        this.player = new Player(400, 700);
        this.countDown = new CountDown();
        this.enemies = new ArrayList<>();
        Utils.playAlarm();
        Utils.playThree();
        this.enemyTimer = new Timer();
        enemyTimer.schedule(enemyTimerTask(), 0, this.rate);
        this.lifes = 3;
    }
    
    private TimerTask enemyTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    if (countDown.ready()) {
                     enemies.add(new Enemy("normal"));
                    }
                    //Ei haluta luoda uutta Timeria ennen kuin pisteet muuttuneet
                    if ((prevPoints != points) && (points % 10 == 0) && points != 0) {
                        enemies.add(new Enemy("bonus"));
//                        System.out.println("Leveli vaihtuu NYT");
                        prevPoints = points;
                        rate = (4 * rate) / 5;
                        enemyTimer.cancel();
                        enemyTimer = new Timer();
                        enemyTimer.schedule(enemyTimerTask(), rate, rate);
                    }
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
                        Utils.playExplosion();
                        if (enemy.getType().equals("bonus")) {
                            score += 300;
                        } else {
                            points++;
                            score += 50;
                        }
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
        }
    }
    public void playerShoot() {
        Utils.playLaser();
        this.player.shoot();
    }
    public int enemyCount() {
        return enemies.size();
    }
    public int getScore() {
        return score;
    }
    public int getLifes() {
        return lifes;
    }
    public void endGame() {
        System.out.println("Peli päättyi");
        System.out.println("Pisteet: " + score);
//        levelTimer.cancel();
        enemyTimer.cancel();
    }
    public void pause() {
        this.paused = true;
    }
    public void unPause() {
        this.paused = false;
    }
}
