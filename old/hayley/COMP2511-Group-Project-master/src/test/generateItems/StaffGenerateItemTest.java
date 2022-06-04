package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.items.Staff;

import javafx.beans.property.SimpleIntegerProperty;

public class StaffGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new StaffGenerateItem();

        assertTrue(generateItem instanceof StaffGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        StaffGenerateItem generateItem = new StaffGenerateItem(); 

        assertEquals("Staff", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        StaffGenerateItem generateItem = new StaffGenerateItem();

        assertEquals("Very low damage, but can randomly inflict a trance on Enemies to turn them into Allied Soldiers", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        StaffGenerateItem generateItem = new StaffGenerateItem(); 

        assertEquals(20, generateItem.price().get());
    }

    @Test
    public void testType() {
        StaffGenerateItem generateItem = new StaffGenerateItem();

        assertEquals(ItemType.WEAPON, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        StaffGenerateItem generateItem = new StaffGenerateItem();
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Staff);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
