/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author paavo
 */
public interface GameObject {
    void update(double time);
    void render(GraphicsContext gc);
    Rectangle2D getBoundary();
    boolean intersects(GameObject o);
}
