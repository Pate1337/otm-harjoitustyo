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
    private GameObject player;
    
    public Game() {
        this.score = 0;
        this.player = new GameObject(400, 700);
    }
    
    public void update(double time) {
        this.player.update(time);
    }
    public void addPlayerVelocity(double velocityX) {
        this.player.addVelocity(velocityX, 0);
    }
    public void render(GraphicsContext gc) {
        this.player.render(gc);
    }
}
