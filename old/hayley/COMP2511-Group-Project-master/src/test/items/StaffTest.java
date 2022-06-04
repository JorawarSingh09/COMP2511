package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import unsw.loopmania.items.Staff;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.Entity;

public class StaffTest {
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
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(staff instanceof Staff);
        assertTrue(staff instanceof EquippableItem);
        assertTrue(staff instanceof Item);
        assertTrue(staff instanceof StaticEntity);
        assertTrue(staff instanceof Entity);
    }

    @Test
    public void testIsEquippable() {
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = createCharacter();

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(staff.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Staff staff1 = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  
        Character character = createCharacter(); 

        character.equipItem(staff1);

        Staff staff2 = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  
        
        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertFalse(staff1.isEquippable(items));
        assertTrue(staff2.isEquippable(items));
    }

    @Test
    public void testInflictTrance() {
        // doesn't modify critical chance
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

    }

    @Test
    public void testGetItemDetails() {
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        GenerateItem generateItem = staff.getItemDetails();

        assertTrue(generateItem instanceof StaffGenerateItem);
    }

    @Test
    public void testAttack() {
        // TODO: this test is failing
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = TestHelper.createCharacter(TestHelper.createPath());
        
        for (int i = 0; i < 20; i++) {
            Zombie zombie = new Zombie(new PathPosition(1, TestHelper.createPath()));
            int zombieHealth = zombie.getHealth();
            staff.attack(zombie, character);

            if (zombie.getHealth() == 0) {
                assertEquals(1, character.getAlliedSoldiers().size());
            } else {
                assertEquals(zombieHealth - 1, zombie.getHealth());
            }
            character.cleanAlliedSoldiers();
        }
    }
}
