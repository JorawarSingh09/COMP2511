package test.items;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.UsableItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.GenerateItem;

public class HealthPotionTest {
    /**
     * Creates the path for testing
     * just a 5x5 square loop
     */
    public List<Pair<Integer, Integer>> createPath() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();

        // add top horizontal
        for (int i = 0; i < 5; i++) {
            orderedPath.add(Pair.with(0, i));
        }
        // add right side down
        for (int i = 1; i < 5; i++) {
            orderedPath.add(Pair.with(i, 4));
        }
        // add bottom horizontal
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(4, i));
        }
        // add left side up
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(i, 0));
        }

        return orderedPath;
    }

    public Character createCharacter() {
        return new Character(new PathPosition(0, createPath()));
    }

    @Test
    public void testConstructor() {
        HealthPotion potion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(potion instanceof HealthPotion);
        assertTrue(potion instanceof UsableItem);
        assertTrue(potion instanceof Item);
        assertTrue(potion instanceof StaticEntity);
        assertTrue(potion instanceof Entity);
    }
    
    @Test
    public void testApplyEffect() {
        HealthPotion potion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Character character = createCharacter();

        assertEquals(50, character.getCurrentHealth()); 

        character.setCurrentHealth(character.getCurrentHealth() - 40);

        assertEquals(10, character.getCurrentHealth()); 

         // character goes back up to 50 health points
         potion.affect(character);

        assertEquals(50, character.getCurrentHealth()); 
    }

    @Test
    public void testApplyEffectFullHealth() {
        HealthPotion potion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Character character = createCharacter();

        assertEquals(50, character.getCurrentHealth());

        // drinking potion at full health means character stays at full health
        potion.affect(character);

        assertEquals(50, character.getCurrentHealth());
    }
}
