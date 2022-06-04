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
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Card;
import unsw.loopmania.Entity;
import unsw.loopmania.cards.TowerCard;
import unsw.loopmania.buildings.TowerBuilding;

public class TowerCardTest {
    @Test
    public void testConstructor() {
        Card card = new TowerCard(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

        assertTrue(card instanceof TowerCard);
        assertTrue(card instanceof Card);
        assertTrue(card instanceof StaticEntity);
        assertTrue(card instanceof Entity);

        assertEquals(2, card.getX());
        assertEquals(3, card.getY());
    }
    
    @Test
    public void testGenerateBuilding() {
        TowerCard card = new TowerCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        Building building = card.generateBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(7));

        assertTrue(building instanceof TowerBuilding);
        assertEquals(2, building.getX());
        assertEquals(7, building.getY());
    }

    @Test
    public void testValidPosition() {
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);
        List<Pair<Integer, Integer>> adjacentPath = TestHelper.createSquarePath(5, 1); // Valid Positions

        TowerCard card = new TowerCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all adjacent pairs are valid
        for (Pair<Integer, Integer> position: adjacentPath) {
            assertTrue(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }

    @Test
    public void testInvalidPosition() {
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0); // Valid Positions
        List<Pair<Integer, Integer>> nonAdjacentPath = TestHelper.createSquarePath(4, 2); // Valid Positions
        

        TowerCard card = new TowerCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all non adjacent pairs are invalid
        for (Pair<Integer, Integer> position: nonAdjacentPath) {
            assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }
}
