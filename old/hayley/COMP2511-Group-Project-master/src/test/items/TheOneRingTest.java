package test.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.RareItem;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.items.TheOneRing;

public class TheOneRingTest {
    @Test
    public void testConstructor() {
        Item item = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));

        assertTrue(item instanceof TheOneRing);
        assertTrue(item instanceof EquippableItem);
        assertTrue(item instanceof RareItem);
    }

    @Test
    public void testSellPrice() {
        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        assertEquals(50, ring.getSellPrice().get());
    }

    @Test
    public void testType() {
        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2)); 
        assertEquals(ItemType.ACCESSORY, ring.getType());
    }

    @Test
    public void testGetItemDetails() {
        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));  
        GenerateItem item = ring.getItemDetails();

        assertTrue(item instanceof TheOneRingGenerateItem);
    }
}
