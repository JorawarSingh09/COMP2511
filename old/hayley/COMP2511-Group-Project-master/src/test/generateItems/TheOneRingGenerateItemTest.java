package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class TheOneRingGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new TheOneRingGenerateItem();

        assertTrue(generateItem instanceof TheOneRingGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        TheOneRingGenerateItem generateItem = new TheOneRingGenerateItem(); 

        assertEquals("The One Ring", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        TheOneRingGenerateItem generateItem = new TheOneRingGenerateItem();

        assertEquals("Player Respawns with full health one time.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        TheOneRingGenerateItem generateItem = new TheOneRingGenerateItem(); 

        assertEquals(50, generateItem.price().get());
    }

    @Test
    public void testType() {
        TheOneRingGenerateItem generateItem = new TheOneRingGenerateItem();

        assertEquals(ItemType.ACCESSORY, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        TheOneRingGenerateItem generateItem = new TheOneRingGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(2));

        assertTrue(item instanceof TheOneRing);
        assertEquals(8, item.getX());
        assertEquals(2, item.getY());
    }
}
