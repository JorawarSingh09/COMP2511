package test.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import test.TestHelper;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Entity;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.enemies.Vampire;

public class VampireTest {
    @Test
    public void testConstructor() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        BasicEnemy enemy = new Vampire(new PathPosition(0, path));
        assertTrue(enemy instanceof Vampire);
        assertTrue(enemy instanceof BasicEnemy);
        assertTrue(enemy instanceof MovingEntity);
        assertTrue(enemy instanceof Entity);
    }

    @Test
    public void testDamage() {
        Vampire vampire = new Vampire(new PathPosition(0, TestHelper.createPath()));
        assertEquals(3, vampire.getDamage());
    }

    @Test
    public void testBattleRadius() {
        Vampire vampire = new Vampire(new PathPosition(0, TestHelper.createPath())); 
        assertEquals(2, vampire.getBattleRadius());
    }

    @Test
    public void testHealth() {
        Vampire vampire = new Vampire(new PathPosition(0, TestHelper.createPath()));  
        assertEquals(15, vampire.getHealth());
    }

    @Test
    public void testMoveInitial() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Vampire vampire = new Vampire(new PathPosition(2, path));
        PathPosition position = vampire.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        vampire.move(0);
        
        // vampire must have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }

    @Test
    public void testMoveSecond() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Vampire vampire = new Vampire(new PathPosition(2, path));
        PathPosition position = vampire.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        vampire.move(1);
        
        // on the second tick, vampire should still have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }
}
