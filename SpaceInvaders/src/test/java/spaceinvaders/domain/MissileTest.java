package spaceinvaders.domain;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MissileTest {
    Missile missile;
    double width;
    double height;
    GraphicsContext gc;
    
    @Before
    public void setUp() {
        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        missile = new Missile(400, 700);
        width = missile.getBoundary().getWidth();
        height = missile.getBoundary().getHeight();
    }
    @Test
    public void methodCallDestoyedWorks() {
        assertTrue(!missile.destroyed());
    }
    @Test
    public void missileWillGoOutOfScreenFromTheTop() {
        missile = new Missile(400, 0);
        missile.update(0.15);
        assertTrue(missile.getBoundary().contains(400, -225, width, height));
        missile.update(0.15);
        assertTrue(missile.getBoundary().contains(400, -450, width, height));
    }
    @Test
    public void greenRectIsDrawnWhenMissileInScreen() {
        missile = new Missile(400, 25);
        missile.render(gc);
        assertTrue(gc.getFill() == Color.GREEN);
    }
    @Test
    public void missileIsSetToDetroyedAfterTenRendersWhenOutOfScreen() {
        missile = new Missile(400, 25);
        assertTrue(!missile.destroyed());
        missile.update(0.15);
        assertTrue(!missile.destroyed());
        for (int i = 0; i < 10; i++) {
            assertTrue(!missile.destroyed());
            missile.render(gc);
            missile.update(0.15);
        }
        assertTrue(missile.destroyed());
    }
}
