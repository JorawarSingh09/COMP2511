package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;
import unsw.loopmania.items.TreeStump;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStumpGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new TreeStumpGenerateItem();

        assertTrue(generateItem instanceof TreeStumpGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        TreeStumpGenerateItem generateItem = new TreeStumpGenerateItem(); 

        assertEquals("Tree Stump", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        TreeStumpGenerateItem generateItem = new TreeStumpGenerateItem();

        assertEquals("An especially powerful shield, which provides higher defence against bosses.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        TreeStumpGenerateItem generateItem = new TreeStumpGenerateItem(); 

        assertEquals(50, generateItem.price().get());
    }

    @Test
    public void testType() {
        TreeStumpGenerateItem generateItem = new TreeStumpGenerateItem();

        assertEquals(ItemType.SHIELD, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        TreeStumpGenerateItem generateItem = new TreeStumpGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof TreeStump);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
