package spaceinvaders.domain;

import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player implements GameObject {
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
        this.width = 45;
        this.height = 55;
        this.missiles = new ArrayList<>();
        
        try {
            ship = new Image(this.getClass().getResource("/resources/images/spaceship.png").toString());
        } catch (Exception e) {
            System.out.println("Image not loaded");
        }
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
        double startX = positionX + (width / 2) - 2.5;
        missiles.add(new Missile(startX, positionY));
    }
    public ArrayList<Missile> getMissiles() {
        return this.missiles;
    }
}
