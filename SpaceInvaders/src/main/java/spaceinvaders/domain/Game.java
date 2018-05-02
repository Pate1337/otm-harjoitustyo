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
 * Luokka pitää huolen pelin toimintalogiikasta.
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
    
    /**
     * Konstruktorissa alustetaan peli ja luodaan uusi countDown-olio.
     * 
     * @see spaceinvaders.domain.CountDown
     */
    public Game() {
        this.points = 0;
        this.prevPoints = 0;
        this.score = 0;
        this.paused = false;
        this.player = new Player(400, 700);
        this.countDown = new CountDown();
        this.enemies = new ArrayList<>();
        try {
            Utils.playAlarm();
            Utils.playThree();
        } catch (Exception e) {
            System.out.println("Could not play sounds");
        }
        this.enemyTimer = new Timer();
        enemyTimer.schedule(enemyTimerTask(), 0, this.rate);
        this.lifes = 3;
    }
    
    /**
     * Metodi luo uuden vihollisaluksen tietyn aikajakson välein.
     * 
     * Aina kun 10 vihollisalusta on tuhottu, metodi luo bonusaluksen
     * ja samalla nopeuttaa tahtia, jolla vihollisaluksia luodaan.
     *  
     * @return uusi ajastin nopeammalla tahdilla
     */
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
    
    /**
     * Metodi päivittää jokaisen pelissä olevan komponentin.
     * @param time aika joka viimeisimmästä päivityksestä on kulunut
     * 
     * @see spaceinvaders.domain.CountDown#update(double) 
     * @see spaceinvaders.domain.Player#update(double) 
     * @see spaceinvaders.domain.Enemy#update(double) 
     */
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
    /**
     * Metodi poistaa kaikki tuhoutuneet pelin komponentit.
     * 
     * Metodi pitää myös kirjaa pelaajan jäljellä olevista elämistä.
     */
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
    /**
     * Metodi määrittää pelaajan nopeuden.
     * @param velocityX pelaajan horisontaalinen nopeus (oletus 700 tai -700).
     * 
     * @see spaceinvaders.domain.Player#addVelocity(double)
     */
    public void addPlayerVelocity(double velocityX) {
        this.player.addVelocity(velocityX);
    }
    /**
     * Metodi kutsuu pelin komponenttien render-metodeja.
     * @param gc käyttöliittymän piirtoalusta
     * 
     * @see spaceinvaders.domain.Player#render(javafx.scene.canvas.GraphicsContext) 
     * @see spaceinvaders.domain.CountDown#render(javafx.scene.canvas.GraphicsContext) 
     * @see spaceinvaders.domain.Enemy#render(javafx.scene.canvas.GraphicsContext)
     */
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
    /**
     * Metodi kutsuu Player-olion shoot() metodia sekä Utils-luokan playLaser() metodia.
     * 
     * @see spaceinvaders.domain.Player#shoot()
     * @see com.mycompany.spaceinvaders.Utils#playLaser()
     */
    public void playerShoot() {
        Utils.playLaser();
        this.player.shoot();
    }
    /**
     * Metodi palauttaa kuvaruudussa näkyvien vihollisalusten määrän.
     * @return vihollisalusten määrä
     */
    public int enemyCount() {
        return enemies.size();
    }
    /**
     * Metodi palauttaa pelaajan scoren.
     * @return pelaajan score
     */
    public int getScore() {
        return score;
    }
    /**
     * Metodi palauttaa pelaajan elämät.
     * @return pelaajan elämät
     */
    public int getLifes() {
        return lifes;
    }
    /**
     * Metodi pysäyttää pelin ajastimen.
     * 
     * @see spaceinvaders.domain.Game#enemyTimerTask() 
     */
    public void endGame() {
//        levelTimer.cancel();
        enemyTimer.cancel();
    }
    /**
     * Metodi asettaa muuttujan paused = true.
     */
    public void pause() {
        this.paused = true;
    }
    /**
     * Metodi asettaa muuttujan paused = false.
     */
    public void unPause() {
        this.paused = false;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public CountDown getCountDown() {
        return this.countDown;
    }
}
