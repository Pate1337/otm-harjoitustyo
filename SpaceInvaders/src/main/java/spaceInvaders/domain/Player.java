/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author paavo
 */
public class Player implements GameObject {
//    private Node image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private ArrayList<Missile> missiles;
    private Image ship;
    
    public Player(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = 50;
        this.height = 50;
        this.missiles = new ArrayList<>();
        ship = new Image(new File("utilities/images/ship.png").toURI().toString());
//        this.image = new Rectangle(positionX, positionY, width, height);
    }
    
    @Override
    public void update(double time) {
        positionX += velocityX * time;
        if (positionX <= 0) {
            positionX = 0;
        } else if (positionX >= 800 - this.width) {
            positionX = 800 - this.width;
        }
        positionY += velocityY * time;
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).update(time);
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(ship, positionX, positionY, width, height);
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).render(gc);
        }
//        Iterator<Missile> missileIterator = missiles.iterator();
//        while (missileIterator.hasNext()) {
//            Missile missile = missileIterator.next();
//            if (missile.destroyed()) {
//                missileIterator.remove();
//            } else {
//                missile.render(gc);
//            }
//        }
    }
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    @Override
    public boolean intersects(GameObject o) {
        return o.getBoundary().intersects(this.getBoundary());
    }
    public void addVelocity(double velocityX) {
        this.velocityX = velocityX;
    }
    public void shoot() {
        double startX = positionX + (width / 2) - 5;// 5 on ammuksen leveys / 2
        missiles.add(new Missile(startX, positionY));
    }
    public ArrayList<Missile> getMissiles() {
        return this.missiles;
    }
//    public int missileCount() {
//        return missiles.size();
//    }
   
}
