/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author paavo
 */
public class Game {
    private int score;
    private Player player;
    
    public Game() {
        this.score = 0;
        this.player = new Player(400, 700);
    }
    
    public void update(double time) {
        if (time != 0) {
            this.player.update(time);
        }
    }
    public void addPlayerVelocity(double velocityX) {
        this.player.addVelocity(velocityX);
    }
    public void render(GraphicsContext gc) {
        this.player.render(gc);
    }
    public void playerShoot() {
        this.player.shoot();
    }
//    public int missileCount() {
//        return player.missileCount();
//    }
}
