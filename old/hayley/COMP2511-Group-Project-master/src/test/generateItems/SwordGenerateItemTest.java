package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.items.Sword;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class SwordGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new SwordGenerateItem();

        assertTrue(generateItem instanceof SwordGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        SwordGenerateItem generateItem = new SwordGenerateItem(); 

        assertEquals("Sword", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        SwordGenerateItem generateItem = new SwordGenerateItem();

        assertEquals("Increases damage dealt by the character.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        SwordGenerateItem generateItem = new SwordGenerateItem(); 

        assertEquals(10, generateItem.price().get());
    }

    @Test
    public void testType() {
        SwordGenerateItem generateItem = new SwordGenerateItem();

        assertEquals(ItemType.WEAPON, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        SwordGenerateItem generateItem = new SwordGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Sword);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
