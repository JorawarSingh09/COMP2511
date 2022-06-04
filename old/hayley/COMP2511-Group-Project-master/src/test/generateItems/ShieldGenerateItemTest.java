package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.items.Shield;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class ShieldGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new ShieldGenerateItem();

        assertTrue(generateItem instanceof ShieldGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        ShieldGenerateItem generateItem = new ShieldGenerateItem(); 

        assertEquals("Shield", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        ShieldGenerateItem generateItem = new ShieldGenerateItem();

        assertEquals("Critical Vampire attacks have a 60% lower chance of occuring.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        ShieldGenerateItem generateItem = new ShieldGenerateItem(); 

        assertEquals(10, generateItem.price().get());
    }

    @Test
    public void testType() {
        ShieldGenerateItem generateItem = new ShieldGenerateItem();

        assertEquals(ItemType.SHIELD, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        ShieldGenerateItem generateItem = new ShieldGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Shield);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
