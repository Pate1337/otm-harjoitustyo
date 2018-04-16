/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author paavo
 */
public class Missile implements GameObject {
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean destroyed;
    private boolean explosion;
    private int count;
    
    public Missile(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = 0;
        this.velocityY = -1000;
        this.width = 10;
        this.height = 30;
        this.destroyed = false;
        this.explosion = false;
        this.count = 0;
    }

    @Override
    public void update(double time) {
        positionY += velocityY * time;
        if (positionY <= 0) {
            explosion = true;
//          destroyed = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!explosion) {
            gc.setFill(Color.RED);
            gc.fillRect(positionX, positionY, width, height);
        } else {
            count++;
            gc.setFill(Color.ORANGE);
            gc.fillRect(positionX - 10, 0, 30, height);
            if (count == 10) {
                destroyed = true;
            }
        }
        
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
