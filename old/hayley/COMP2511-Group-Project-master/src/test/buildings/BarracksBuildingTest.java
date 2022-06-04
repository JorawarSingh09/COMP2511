package test.buildings;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;

import test.TestHelper;
import unsw.loopmania.buildings.BarracksBuilding;
import unsw.loopmania.Building;
import unsw.loopmania.Character;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.LoopManiaWorld;

public class BarracksBuildingTest {
    @Test
    public void testConstructor() {
        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));

        assertTrue(barracks instanceof BarracksBuilding);
        assertTrue(barracks instanceof Building);
        assertTrue(barracks instanceof StaticEntity);
        assertTrue(barracks instanceof Entity);

        assertEquals(2, barracks.getX());
        assertEquals(0, barracks.getY());
    }
    
    @Test
    public void testEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        character.moveDownPath();
        // character is now on same tile as barracks
        barracks.encounter(character);
        
        // there should now be a new allied solider
        assertEquals(1, character.getAlliedSoldiers().size());
    }

    @Test
    public void testNoEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        // character is not on same tile as barracks
        barracks.encounter(character);
        
        // no new soldier
        assertEquals(0, character.getAlliedSoldiers().size());
    }
}
