package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.items.Stake;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class StakeGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new StakeGenerateItem();

        assertTrue(generateItem instanceof StakeGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        StakeGenerateItem generateItem = new StakeGenerateItem(); 

        assertEquals("Stake", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        StakeGenerateItem generateItem = new StakeGenerateItem();

        assertEquals("Lower Damage, but very high damage against Vampires.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        StakeGenerateItem generateItem = new StakeGenerateItem(); 

        assertEquals(15, generateItem.price().get());
    }

    @Test
    public void testType() {
        StakeGenerateItem generateItem = new StakeGenerateItem();

        assertEquals(ItemType.WEAPON, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        StakeGenerateItem generateItem = new StakeGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Stake);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
