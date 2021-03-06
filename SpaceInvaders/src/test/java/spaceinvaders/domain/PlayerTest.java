package spaceinvaders.domain;

import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;
    double width;
    double height;
    GraphicsContext gc;
    
    @Before
    public void setUp() {
        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        player = new Player(400, 400);
        width = player.getBoundary().getWidth();
        height = player.getBoundary().getHeight();
    }
    @Test
    public void contructorWorks() {
        assertEquals(0, player.getMissiles().size());
        Rectangle2D playerRect = player.getBoundary();
        
        Rectangle2D rightRect = new Rectangle2D(446, 0, 354, 800);
        Rectangle2D leftRect = new Rectangle2D(0, 0, 399, 800);
        Rectangle2D upRect = new Rectangle2D(0, 0, 800, 399);
        Rectangle2D downRect = new Rectangle2D(0, 456, 800, 344);
        
        assertTrue(playerRect.contains(400, 400, width, height));
        assertTrue(!playerRect.intersects(rightRect));
        assertTrue(!playerRect.intersects(leftRect));
        assertTrue(!playerRect.intersects(upRect));
        assertTrue(!playerRect.intersects(downRect));
    }
    @Test
    public void ifVelocityNotAddedPositionDoesNotChangeOnUpdate() {
        player.update(0.15);
        Rectangle2D playerRect = player.getBoundary();
        assertTrue(playerRect.contains(400, 400, width, height));
    }
    @Test
    public void ifVelocityAddedPositionChangesOnUpdate() {
        player.addVelocity(50);
        player.update(0.15);
        Rectangle2D playerRect = player.getBoundary();
        assertTrue(!playerRect.contains(400, 400, width, height));
        assertTrue(playerRect.contains(407.5, 400, width, height));
    }
    @Test
    public void playerCanNotGoOutOfScreenFromLeft() {
        player = new Player(0, 700);
        player.addVelocity(-50);
        player.update(0.15);
        assertTrue(player.getBoundary().contains(0, 700, width, height));
        player.addVelocity(-50);
        player.update(0.15);
        assertTrue(player.getBoundary().contains(0, 700, width, height));
    }
    @Test
    public void playerCanNotGoOutOfScreenFromRight() {
        player = new Player(755, 700);
        player.addVelocity(50);
        player.update(0.15);
        assertTrue(player.getBoundary().contains(755, 700, width, height));
        player.addVelocity(50);
        player.update(0.15);
        assertTrue(player.getBoundary().contains(755, 700, width, height));
    }
    @Test
    public void missileIsAddedWhenPlayerShoots() {
        player.shoot();
        assertEquals(1, player.getMissiles().size());
        player.shoot();
        assertEquals(2, player.getMissiles().size());
    }
    @Test
    public void missileStartsFromPlayerPosition() {
        player.shoot();
        List<Missile> missiles = player.getMissiles();
        assertTrue(missiles.get(0).getBoundary().contains(400 + (width / 2) - 2.5, 400, 5, 30));
    }
    @Test
    public void missilesAreAlsoUpdatedWhenPlayerIsUpdated() {
        player.shoot();
        player.update(0.15);
        List<Missile> missiles = player.getMissiles();
        assertTrue(missiles.get(0).getBoundary().contains(400 + (width / 2) - 2.5, 175, 5, 30));
    }
    @Test
    public void whenPlayerShootsAndIsRenderedMissilesAreAlsoRendered() {
        player.shoot();
        assertEquals(1, player.getMissiles().size());
        player.render(gc);
        assertTrue(gc.getFill() == Color.GREEN);
        gc.setFill(Color.WHITE);
        assertTrue(gc.getFill() != Color.GREEN);
        player.shoot();
        assertEquals(2, player.getMissiles().size());
        player.render(gc);
        assertTrue(gc.getFill() == Color.GREEN);
    }
}
