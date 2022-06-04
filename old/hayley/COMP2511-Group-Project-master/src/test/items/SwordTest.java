package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.items.Sword;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.Entity;

public class SwordTest {
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
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(sword instanceof Sword);
        assertTrue(sword instanceof EquippableItem);
        assertTrue(sword instanceof Item);
        assertTrue(sword instanceof StaticEntity);
        assertTrue(sword instanceof Entity);
    }

    @Test
    public void testType() {
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        assertEquals(ItemType.WEAPON, sword.getType());
    }

    @Test
    public void testSellPrice() {
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(10, sword.getSellPrice().get());
    }

    @Test
    public void testIsEquippable() {
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = createCharacter();

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(sword.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Sword sword1 = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  
        Character character = createCharacter(); 

        character.equipItem(sword1);

        Sword sword2 = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));   

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        // can't equip sword if another sword is equipped
        assertTrue(sword2.isEquippable(items));
        assertFalse(sword1.isEquippable(items));
    }

    @Test
    public void testGetModifiedCharacterDamage() {
        // sets damage to 10
        Character character = createCharacter();
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        int initialDamage = character.getDamage();

        sword.affect(character);

        assertEquals(initialDamage + 10, character.getDamage());
    }

    @Test
    public void testGetItemDetails() {
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        GenerateItem generateItem = sword.getItemDetails();

        assertTrue(generateItem instanceof SwordGenerateItem);
    }
}
