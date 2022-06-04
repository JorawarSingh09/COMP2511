package random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {
    @Test
    public void testSeed1() {
        Game game = new Game(4);

        assertTrue(game.battle());
        assertTrue(game.battle());
        assertFalse(game.battle());
        assertTrue(game.battle());
        assertTrue(game.battle());
        assertFalse(game.battle());
    }

    @Test 
    public void testSeed2() {
        Game game = new Game(-4);

        assertFalse(game.battle());
        assertFalse(game.battle());
        assertTrue(game.battle());
        assertFalse(game.battle());
        assertFalse(game.battle());
        assertTrue(game.battle());
    }

    @Test 
    public void testSeed3() {
        Game game = new Game(0);

        assertTrue(game.battle());
        assertFalse(game.battle());
        assertFalse(game.battle());
        assertFalse(game.battle());
        assertFalse(game.battle());
        assertTrue(game.battle());
    }
}