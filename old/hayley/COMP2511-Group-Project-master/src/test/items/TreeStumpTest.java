package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import test.TestHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.RareItem;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.items.TreeStump;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;
import unsw.loopmania.enemies.Doggie;

public class TreeStumpTest {
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
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

        assertTrue(treeStump instanceof TreeStump);
        assertTrue(treeStump instanceof EquippableItem);
        assertTrue(treeStump instanceof RareItem);
    }

    @Test
    public void testSellPrice() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3)); 

        assertEquals(50, treeStump.getSellPrice().get());
    }

    @Test
    public void testType() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

        assertEquals(ItemType.SHIELD, treeStump.getType());
    }

    @Test
    public void testIsEquippable() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = createCharacter();

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(treeStump.isEquippable(items));
    }

    @Test
    public void testDefendAgainstBosses() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        PathPosition pathPosition = new PathPosition(0, TestHelper.createPath());

        Doggie doggie = new Doggie(pathPosition);

        int doggieDamage = doggie.getDamage();

        treeStump.affect(doggie);

        assertEquals(doggieDamage / 4, doggie.getDamage());

    }

    @Test
    public void testDefendAgainstNormalEnemy() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        PathPosition pathPosition = new PathPosition(0, TestHelper.createPath());

        Vampire vampire = new Vampire(pathPosition);

        int vampireDamage = vampire.getDamage();

        treeStump.affect(vampire);

        assertEquals(vampireDamage / 3, vampire.getDamage());
    }

    @Test
    public void testGetItemDetails() {
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        GenerateItem generateItem = treeStump.getItemDetails();

        assertTrue(generateItem instanceof TreeStumpGenerateItem);
    }
}
