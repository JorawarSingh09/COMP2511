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
import unsw.loopmania.enemies.Slug;

public class SlugTest {
    @Test
    public void testConstructor() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        BasicEnemy enemy = new Slug(new PathPosition(0, path));
        assertTrue(enemy instanceof Slug);
        assertTrue(enemy instanceof BasicEnemy);
        assertTrue(enemy instanceof MovingEntity);
        assertTrue(enemy instanceof Entity);
    }

    @Test
    public void testDamage() {
        Slug slug = new Slug(new PathPosition(0, TestHelper.createPath()));
        assertEquals(1, slug.getDamage());
    }

    @Test
    public void testBattleRadius() {
        Slug slug = new Slug(new PathPosition(0, TestHelper.createPath())); 
        assertEquals(1, slug.getBattleRadius());
    }

    @Test
    public void testHealth() {
        Slug slug = new Slug(new PathPosition(0, TestHelper.createPath()));  
        assertEquals(4, slug.getHealth());
    }

    @Test
    public void testMoveInitial() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Slug slug = new Slug(new PathPosition(2, path));
        PathPosition position = slug.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        slug.move(0);
        
        // slug must have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }

    @Test
    public void testMoveSecond() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Slug slug = new Slug(new PathPosition(2, path));
        PathPosition position = slug.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        slug.move(1);
        
        // on the second tick, slug should still have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }
}
