package test.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Building;
import unsw.loopmania.EnemyPositionObserver;
import unsw.loopmania.buildings.TrapBuilding;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.PathPosition;

public class TrapBuildingTest {
    @Test
    public void testConstructor() {
        Building trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));

        assertTrue(trap instanceof TrapBuilding);
        assertTrue(trap instanceof EnemyPositionObserver);
        assertTrue(trap instanceof Building);
        assertTrue(trap instanceof StaticEntity);
        assertTrue(trap instanceof Entity);

        assertEquals(0, trap.getX());
        assertEquals(3, trap.getY());
    }

    @Test
    public void testEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        Vampire vampire = new Vampire(new PathPosition(1, path));

        int initialHealth = vampire.getHealth();

        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2)); 

        vampire.moveDownPath();
        // vampire is now on same tile as trap
        trap.encounter(vampire);

        // vampire loses health and trap ceases to exist
        assertEquals(initialHealth - 3, vampire.getHealth());
        assertFalse(trap.shouldExist().get());
    }

    @Test
    public void testNoEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        Vampire vampire = new Vampire(new PathPosition(1, path));

        int initialHealth = vampire.getHealth();

        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3)); 

        vampire.moveDownPath();
        // vampire is now on same tile as trap
        trap.encounter(vampire);

        // nothing should have changed
        assertEquals(initialHealth, vampire.getHealth());
        assertTrue(trap.shouldExist().get()); 
    }
}

