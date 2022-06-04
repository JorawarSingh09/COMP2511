package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import org.javatuples.Pair;

import unsw.loopmania.items.Helmet;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.generateItems.HelmetGenerateItem;

public class HelmetTest {
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
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(helmet instanceof Helmet);
        assertTrue(helmet instanceof EquippableItem);
        assertTrue(helmet instanceof Item);
        assertTrue(helmet instanceof StaticEntity);
        assertTrue(helmet instanceof Entity);
    }

    @Test
    public void testType() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        assertEquals(ItemType.HELMET, helmet.getType());
    }

    @Test
    public void testSellPrice() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  

        assertEquals(15, helmet.getSellPrice().get());
    }

    @Test
    public void testIsEquippable() {
        Character character = createCharacter();
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(helmet.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Character character = createCharacter();
        Helmet helmet1 = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        character.equipItem(helmet1);

        Helmet helmet2 = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        // can't equip second helmet if helmet is already equipped
        assertTrue(helmet2.isEquippable(items)); 
        assertFalse(helmet1.isEquippable(items));
    }

    @Test
    public void affectSlug() {
        // attack should not do anything
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Slug slug = new Slug(new PathPosition(0, createPath()));

        int initialDamage = slug.getDamage();

        helmet.affect(slug);

        assertEquals(initialDamage - 3, slug.getDamage());
    } 

    @Test
    public void testGetItemDetails() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));
        GenerateItem generateItem = helmet.getItemDetails();

        assertTrue(generateItem instanceof HelmetGenerateItem);
    }
}
