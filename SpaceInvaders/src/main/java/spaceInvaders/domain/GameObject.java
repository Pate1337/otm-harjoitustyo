/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author paavo
 */
public class GameObject {
//    private Node image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    
    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = 50;
        this.height = 50;
//        this.image = new Rectangle(positionX, positionY, width, height);
    }
    
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(positionX, positionY, width, height);
    }
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    public boolean intersects(GameObject o) {
        return o.getBoundary().intersects(this.getBoundary());
    }
    public void addVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
   
}
