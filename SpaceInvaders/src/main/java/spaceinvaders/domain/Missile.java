package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
        this.velocityY = -1500;
        this.width = 5;
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
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!explosion) {
            gc.setFill(Color.GREEN);
            gc.fillRoundRect(positionX, positionY, width, height, 2, 5);
        } else {
            count++;
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
