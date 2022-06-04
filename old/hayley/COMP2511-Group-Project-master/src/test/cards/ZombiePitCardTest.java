package test.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.Test;
import org.javatuples.Pair;
import java.util.List;

import unsw.loopmania.Building;
import test.TestHelper;

import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.buildings.ZombiePitBuilding;

public class ZombiePitCardTest {
    
    @Test
    public void testGenerateBuilding(){
        ZombiePitCard card = new ZombiePitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Building building = card.generateBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        // Tests that the building is generated at the given coordinates.
        assertTrue(building instanceof ZombiePitBuilding);
        assertEquals(1, building.getX());
        assertEquals(1, building.getY());
    }

    @Test
    public void testValidPosition() {
        List<Pair<Integer, Integer>> adjacentPath = TestHelper.createSquarePath(5, 1); // Valid Positions
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);

        ZombiePitCard card = new ZombiePitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all adjacent pairs are valid
        for (Pair<Integer, Integer> position: adjacentPath) {
            assertTrue(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }

    @Test
    public void testInvalidPosition() {
        List<Pair<Integer, Integer>> nonAdjacentPath = TestHelper.createSquarePath(4, 2); // Valid Positions
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);

        ZombiePitCard card = new ZombiePitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all non adjacent pairs are invalid
        for (Pair<Integer, Integer> position: nonAdjacentPath) {
            assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }
}
