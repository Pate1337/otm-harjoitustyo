/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author paavo
 */
public class Game {
    private int score;
    private Player player;
    private CountDown countDown;
    
    public Game() {
        this.score = 0;
        this.player = new Player(400, 700);
        this.countDown = new CountDown();
        Utils.playSound("utilities/sounds/alarm.wav");
        Utils.playSound("utilities/sounds/three.wav");
    }
    
    public void update(double time) {
        if (time != 0 && !countDown.ready()) {
            this.countDown.update(time);
            this.player.update(time);
        } else if (time != 0) {
            this.player.update(time);
            //Ja tähän sit mylös vihollisalukset
        }
    }
    public void addPlayerVelocity(double velocityX) {
        this.player.addVelocity(velocityX);
    }
    public void render(GraphicsContext gc) {
        this.player.render(gc);
        if (!countDown.ready()) {
            this.countDown.render(gc);
        }
    }
    public void playerShoot() {
        this.player.shoot();
    }
//    public int missileCount() {
//        return player.missileCount();
//    }
}
