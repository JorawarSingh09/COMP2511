package test.buildings;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.Test;

import java.util.List;
import org.javatuples.Pair;

import test.TestHelper;
import unsw.loopmania.buildings.TowerBuilding;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.Building;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EnemyPositionObserver;

public class TowerBuildingTest {
    @Test
    public void testConstructor() {
        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(4), new SimpleIntegerProperty(0));

        assertTrue(tower instanceof TowerBuilding);
        assertTrue(tower instanceof EnemyPositionObserver);
        assertTrue(tower instanceof Building);
        assertTrue(tower instanceof StaticEntity);
        assertTrue(tower instanceof Entity);

        assertEquals(4, tower.getX());
        assertEquals(0, tower.getY());
    }
    
    @Test
    public void testEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        Zombie zombie = new Zombie(new PathPosition(1, path));

        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));

        int initialHealth = zombie.getHealth();

        zombie.moveDownPath();
        // zombie is now adjacent to tower
        tower.encounter(zombie);
        
        assertEquals(initialHealth - 2, zombie.getHealth());
    }

    @Test
    public void testNoEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        Zombie zombie = new Zombie(new PathPosition(1, path));

        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(3));

        int initialHealth = zombie.getHealth();

        zombie.moveDownPath();
        // zombie is not adjacent to tower
        tower.encounter(zombie);
        
        assertEquals(initialHealth, zombie.getHealth());
    }
}
