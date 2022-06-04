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
import unsw.loopmania.enemies.Zombie;

public class ZombieTest {
    @Test
    public void testConstructor() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        BasicEnemy enemy = new Zombie(new PathPosition(0, path));
        assertTrue(enemy instanceof Zombie);
        assertTrue(enemy instanceof BasicEnemy);
        assertTrue(enemy instanceof MovingEntity);
        assertTrue(enemy instanceof Entity);
    }

    @Test
    public void testDamage() {
        Zombie zombie = new Zombie(new PathPosition(0, TestHelper.createPath()));
        assertEquals(2, zombie.getDamage());
    }

    @Test
    public void testBattleRadius() {
        Zombie zombie = new Zombie(new PathPosition(0, TestHelper.createPath())); 
        assertEquals(2, zombie.getBattleRadius());
    }

    @Test
    public void testHealth() {
        Zombie zombie = new Zombie(new PathPosition(0, TestHelper.createPath()));  
        assertEquals(10, zombie.getHealth());
    }

    @Test
    public void testMoveInitial() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Zombie zombie = new Zombie(new PathPosition(2, path));
        PathPosition position = zombie.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        zombie.move(0);
        
        // zombie must have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }

    @Test
    public void testMoveSecond() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Zombie zombie = new Zombie(new PathPosition(2, path));
        PathPosition position = zombie.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        zombie.move(1);
        
        // on the second tick, zombie should not have moved
        assertEquals(initialY, position.getYValue());
        assertEquals(initialX, position.getXValue());
    }
}
