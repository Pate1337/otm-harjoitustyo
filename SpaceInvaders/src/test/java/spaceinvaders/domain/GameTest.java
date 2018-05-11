package spaceinvaders.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
    Game game;
    
    @Before
    public void setUp() {
        game = new Game();
    }
    
    @Test
    public void whenNewGameIsCreatedPlayerPositionIsCorrect() {
        Player player = game.getPlayer();
        assertTrue(player.getBoundary().contains(400, 700, player.getBoundary().getWidth(), player.getBoundary().getHeight()));
    }
    
    @Test
    public void whenNewGameIsCreatedScoreIsZero() {
        assertEquals(0, game.getScore());
    }
    
    @Test
    public void whenNewGameIsCreatedCountdownWillBePlayed() {
        CountDown cd = game.getCountDown();
        assertTrue(cd != null);
        
        assertTrue(!cd.ready());
    }
    
    @Test
    public void whenNewGameIsCreatedEnemyCountIsZero() {
        int enemies = game.enemyCount();
        assertEquals(0, enemies);
    }
    
    @Test
    public void whenNewGameIsCreatedLifesIsThree() {
        int lifes = game.getLifes();
        assertEquals(3, lifes);
    }
}
