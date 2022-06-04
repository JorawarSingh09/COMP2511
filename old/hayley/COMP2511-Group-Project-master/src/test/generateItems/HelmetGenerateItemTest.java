package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.HelmetGenerateItem;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class HelmetGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new HelmetGenerateItem();

        assertTrue(generateItem instanceof HelmetGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        HelmetGenerateItem generateItem = new HelmetGenerateItem(); 

        assertEquals("Helmet", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        HelmetGenerateItem generateItem = new HelmetGenerateItem();

        assertEquals("Enemy damage is reduced, but character damage is also reduced.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        HelmetGenerateItem generateItem = new HelmetGenerateItem(); 

        assertEquals(15, generateItem.price().get());
    }

    @Test
    public void testType() {
        HelmetGenerateItem generateItem = new HelmetGenerateItem();

        assertEquals(ItemType.HELMET, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        HelmetGenerateItem generateItem = new HelmetGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Helmet);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
