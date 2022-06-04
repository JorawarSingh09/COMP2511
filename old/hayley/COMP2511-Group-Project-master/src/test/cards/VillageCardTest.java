package test.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import org.junit.jupiter.api.Test;
import org.javatuples.Pair;
import java.util.List;


import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Entity;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.cards.VillageCard;
import unsw.loopmania.buildings.VillageBuilding;

public class VillageCardTest {
    @Test
    public void testConstructor() {
        Card card = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)); 

        assertTrue(card instanceof VillageCard);
        assertTrue(card instanceof Card);
        assertTrue(card instanceof StaticEntity);
        assertTrue(card instanceof Entity);

        assertEquals(0, card.getX());
        assertEquals(1, card.getY());
    }
    
    @Test
    public void testGenerateBuilding() {
        VillageCard card = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        Building building = card.generateBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));

        // correct type and coordinates
        assertTrue(building instanceof VillageBuilding);
        assertEquals(2, building.getX());
        assertEquals(1, building.getY());
    }

    @Test
    public void testInvalidPosition() {
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);
        List<Pair<Integer, Integer>> adjacentPath = TestHelper.createSquarePath(5, 1); // Invalid Positions

        VillageCard card = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all non path tiles are invalid
        for (Pair<Integer, Integer> position: adjacentPath) {
            assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }

    @Test
    public void testPathPositions() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0);
        VillageCard card = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all path tiles except hero's castle are valid
        for (Pair<Integer, Integer> position: path) {
            if (path.indexOf(position) == 0) {
                assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), path));
            } else {
                assertTrue(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), path));
            }
        }
    }
}
