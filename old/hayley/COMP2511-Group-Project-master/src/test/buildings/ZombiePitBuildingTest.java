package test.buildings;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.buildings.ZombiePitBuilding;
import unsw.loopmania.enemies.Zombie;

public class ZombiePitBuildingTest {
    @Test
    public void testSpawnEveryCycle() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        ZombiePitBuilding pit = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        for (int i = 0; i < 10; i++) {
            Zombie zombie = pit.possiblySpawnEnemy(path, i);
            assertTrue(zombie instanceof Zombie);

            Pair<Integer, Integer> zombiePos = new Pair<Integer, Integer>(zombie.getX(), zombie.getY());
            assertTrue(path.contains(zombiePos));
            assertNotEquals(0, path.indexOf(zombiePos));
        }
    }
}
