package test.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.items.Armour;
import unsw.loopmania.Character;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.Entity;

public class ArmourTest {
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
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(armour instanceof Armour);
        assertTrue(armour instanceof EquippableItem);
        assertTrue(armour instanceof Item);
        assertTrue(armour instanceof StaticEntity);
        assertTrue(armour instanceof Entity);
    }

    @Test
    public void testType() {
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        assertEquals(ItemType.ARMOUR, armour.getType());
    }

    @Test
    public void testIsEquippable() {
        Character character = createCharacter();
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(armour.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Character character = createCharacter();
        Armour armour1 = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        character.equipItem(armour1);

        Armour armour2 = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        // can't equip second armour if armour is already equipped
        assertTrue(armour2.isEquippable(items)); 
        assertFalse(armour1.isEquippable(items));
    }

    @Test
    public void affectSlug() {
        // attack should not do anything
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Slug slug = new Slug(new PathPosition(0, createPath()));

        int initialDamage = slug.getDamage();

        armour.affect(slug);

        assertEquals(initialDamage / 2, slug.getDamage());
    }

    @Test
    public void testGetItemDetails() {
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        GenerateItem generateItem = armour.getItemDetails();

        assertTrue(generateItem instanceof ArmourGenerateItem);
    }
}
