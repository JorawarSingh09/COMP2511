package test.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.RareItem;
import unsw.loopmania.generateItems.DoggieCoinGenerateItem;
import unsw.loopmania.items.DoggieCoin;

public class DoggieCoinTest {
    @Test
    public void testConstructor() {
        Item item = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));

        assertTrue(item instanceof DoggieCoin);
        assertTrue(item instanceof RareItem);
    }

    @Test
    public void testType() {
        DoggieCoin ring = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2)); 
        assertEquals(ItemType.NOT_EQUIPPABLE, ring.getType());
    }

    @Test
    public void testGetItemDetails() {
        DoggieCoin ring = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));  
        GenerateItem item = ring.getItemDetails();

        assertTrue(item instanceof DoggieCoinGenerateItem);
    }
}
