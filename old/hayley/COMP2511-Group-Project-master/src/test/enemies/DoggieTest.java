package test.enemies;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Battle;
import unsw.loopmania.CharacterEffect;
import unsw.loopmania.Entity;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.enemies.Doggie;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.items.Stake;

public class DoggieTest {
    @Test
    public void testConstructor() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        BasicEnemy enemy = new Doggie(new PathPosition(0, path));
        assertTrue(enemy instanceof Doggie);
        assertTrue(enemy instanceof BasicEnemy);
        assertTrue(enemy instanceof MovingEntity);
        assertTrue(enemy instanceof Entity);

        // check stats
        assertEquals(5, enemy.getDamage());
        assertEquals(1, enemy.getBattleRadius());
        assertEquals(60, enemy.getHealth());
        assertEquals(2, enemy.getSupportRadius());
    }

    @Test
    public void testMoveInitial() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Doggie Doggie = new Doggie(new PathPosition(2, path));
        PathPosition position = Doggie.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        Doggie.move(0);
        
        // Doggie must have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }

    @Test
    public void testMoveSecond() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        Doggie Doggie = new Doggie(new PathPosition(2, path));
        PathPosition position = Doggie.getPosition();
        int initialX = position.getXValue();
        int initialY = position.getYValue();

        Doggie.move(1);
        
        // on the second tick, Doggie should still have moved either left or right
        assertEquals(initialY, position.getYValue());
        assertTrue(Math.abs(initialX - position.getXValue()) == 1);
    }

    @Test
    public void testLostBattle() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        BasicEnemy Doggie = new Doggie(new PathPosition(2, path));
        Slug slug = new Slug(new PathPosition(2, path));
        slug.setHealth(2);
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        List<CharacterEffect> buildings = new ArrayList<>();
        Character character = new Character(new PathPosition(2, path));
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        character.equipItem(stake);
        Battle battle = new Battle(character, enemies, buildings, Doggie);

        battle.runBattle();

        assertFalse(battle.wonBattle());
        
    }

    @Test
    public void testWonBattle() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        BasicEnemy Doggie = new Doggie(new PathPosition(2, path));
        Doggie.setHealth(0);
        Slug slug = new Slug(new PathPosition(2, path));
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        List<CharacterEffect> buildings = new ArrayList<>();
        Character character = new Character(new PathPosition(2, path));
        character.setCurrentHealth(100);
        Battle battle = new Battle(character, enemies, buildings, Doggie);

        battle.runBattle();

        assertTrue(battle.wonBattle());
        
    }
}
