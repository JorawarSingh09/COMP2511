package test.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.buildings.VampireCastleBuilding;
import unsw.loopmania.enemies.Vampire;

public class VampireCastleBuildingTest {

    @Test
    public void testSpawnAfter5Cycles() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        VampireCastleBuilding castle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        Vampire vamp = castle.possiblySpawnEnemy(path, 5);
        assertTrue(vamp != null);
        assertTrue(vamp instanceof Vampire);

        Pair<Integer, Integer> vampPos = new Pair<Integer, Integer>(vamp.getX(), vamp.getY());
        // check that it's on a path tile
        assertTrue(path.contains(vampPos));
        // check that it's not at hero's castle
        assertNotEquals(0, path.indexOf(vampPos));
    }

    @Test
    public void testSpawnAfter15Cycles() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        VampireCastleBuilding castle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        Vampire vamp = castle.possiblySpawnEnemy(path, 15);
        assertTrue(vamp != null);
        assertTrue(vamp instanceof Vampire);

        Pair<Integer, Integer> vampPos = new Pair<Integer, Integer>(vamp.getX(), vamp.getY());
        // check that it's on a path tile
        assertTrue(path.contains(vampPos));
        // check that it's not at hero's castle
        assertNotEquals(0, path.indexOf(vampPos));
    }

    @Test
    public void testNonSpawningCycles() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        VampireCastleBuilding castle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        for (int i = 0; i < 17; i++) {
            if (i % 5 != 0) {
                assertEquals(null, castle.possiblySpawnEnemy(path, i));
            }
        }
    }
}
