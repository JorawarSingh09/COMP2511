package test.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.Test;
import org.javatuples.Pair;
import java.util.List;

import test.TestHelper;
import unsw.loopmania.cards.TrapCard;
import unsw.loopmania.buildings.TrapBuilding;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;

public class TrapCardTest {
    @Test
    public void testConstructor() {
        Card card = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)); 

        assertTrue(card instanceof TrapCard);
        assertTrue(card instanceof Card);
        assertTrue(card instanceof StaticEntity);
        assertTrue(card instanceof Entity);

        assertEquals(0, card.getX());
        assertEquals(1, card.getY());
    }
    
    @Test
    public void testGenerateBuilding(){
        TrapCard card = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        Building building = card.generateBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));

        // correct type and coordinates
        assertTrue(building instanceof TrapBuilding);
        assertEquals(2, building.getX());
        assertEquals(1, building.getY());
    }

    @Test
    public void testInvalidPosition() {
        List<Pair<Integer, Integer>> worldPath = TestHelper.createSquarePath(6, 0);
        List<Pair<Integer, Integer>> adjacentPath = TestHelper.createSquarePath(5, 1); // Invalid Positions

        TrapCard card = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // Tests that all non path tiles are invalid
        for (Pair<Integer, Integer> position: adjacentPath) {
            assertFalse(card.isValidPosition(new SimpleIntegerProperty(position.getValue0()), new SimpleIntegerProperty(position.getValue1()), worldPath));
        }
    }

    @Test
    public void testPathPositions() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0);
        TrapCard card = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

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
