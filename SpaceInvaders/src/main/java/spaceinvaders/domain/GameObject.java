package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
    void update(double time);
    void render(GraphicsContext gc);
    Rectangle2D getBoundary();
    boolean intersects(GameObject o);
}
