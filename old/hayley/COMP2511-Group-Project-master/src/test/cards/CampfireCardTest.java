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
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.buildings.CampfireBuilding;

public class CampfireCardTest {
    @Test
    public void testConstructor() {
        Card card = new CampfireCard(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

        assertTrue(card instanceof CampfireCard);
        assertTrue(card instanceof Card);
        assertTrue(card instanceof StaticEntity);
        assertTrue(card instanceof Entity);

        assertEquals(2, card.getX());
        assertEquals(3, card.getY());
    }

    
    @Test
    public void testGenerateBuilding() {
        CampfireCard card = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        Building building = card.generateBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(4));

        assertTrue(building instanceof CampfireBuilding);
        assertEquals(3, building.getX());
        assertEquals(4, building.getY());
    }

    @Test
    public void testNonPathTiles() {
        List<Pair<Integer, Integer>> adjacentPath = TestHelper.createSquarePath(3, 1); // Valid Positions
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);

        CampfireCard card = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all non path tiles are valid
        for (Pair<Integer, Integer> position: adjacentPath) {
            assertTrue(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }

    @Test
    public void testPathTiles() {
        CampfireCard card = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);

        // Tests that all path tiles are invalid
        for (Pair<Integer, Integer> position: worldPath) {
            assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }
}
