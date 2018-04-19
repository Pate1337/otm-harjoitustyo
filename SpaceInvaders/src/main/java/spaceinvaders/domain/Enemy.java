/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private boolean explosion;
    private double explosionX;
    private double explosionY;
    private int count;
    private String type;
    private Image enemy;
    private ArrayList<Image> explosionImages;
    
    
    public Enemy(String type) {
        this.type = type;
        this.height = 40;
        Random random = new Random();
        this.destroyed = false;
        this.explosion = false;
        this.count = 0;
        if (type.equals("bonus")) {
            enemy = new Image(this.getClass().getResource("/resources/images/ufo.png").toString());
            this.width = 70;
            this.positionY = 150;
            this.velocityY = 0;
            if (random.nextInt(2) == 0) {
                this.positionX = 0 - width;
                this.velocityX = 200;
            } else {
                this.positionX = 800;
                this.velocityX = -200;
            }
        } else if (type.equals("normal")) {
            enemy = new Image(this.getClass().getResource("/resources/images/enemy.png").toString());
            this.width = 50;
            this.positionY = 0;
            this.positionX = random.nextInt(801 - (int) width);
            this.velocityY = 200;
            this.velocityX = 0;
        }
        explosionImages = Utils.getExplosionImages();
    }
    

    @Override
    public void update(double time) {
        if (!explosion) {
            positionY += velocityY * time;
            positionX += velocityX * time;
            if (positionY >= 800 - height) {
                explosion = true;
                explosionX = positionX;
                explosionY = 800 - width; //Jälkimmäinen on räjähdyksen koko
                positionX = 0;
                positionY = 800;
                Utils.playExplosion();
            }  else if (positionX < 0 - width) {
                destroyed = true;
            } else if (positionX > 800) {
                destroyed = true;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!explosion) {
            gc.drawImage(enemy, positionX, positionY, width, height);
        } else {
            gc.drawImage(explosionImages.get(count), explosionX, explosionY, width, width);
            if (count == 15) {
                destroyed = true;
            } else {
                count++;
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
    public void explode() {
        explosion = true;
        explosionX = positionX;
        explosionY = positionY;
        positionX = 0;
        positionY = 850;
    }
    public double getPositionY() {
        return positionY;
    }
    public String getType() {
        return type;
    }
    
}
