/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author paavo
 */
public class Enemy implements GameObject {
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean destroyed;
    
    public Enemy() {
        this.width = 50;
        this.height = 30;
        this.positionY = 0;
        Random random = new Random();
        this.positionX = random.nextInt(801 - (int)width);
        this.velocityY = 200;
        this.velocityX = 0;
        this.destroyed = false;
    }

    @Override
    public void update(double time) {
        positionY += velocityY * time;
        if (positionY <= 0) {
            destroyed = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(positionX, positionY, width, height);
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    @Override
    public boolean intersects(GameObject o) {
        return o.getBoundary().intersects(this.getBoundary());
    }
    
    public boolean destroyed() {
        return destroyed;
    }
    
}
