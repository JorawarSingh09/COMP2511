package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.items.Armour;

public class ArmourGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new ArmourGenerateItem();

        assertTrue(generateItem instanceof ArmourGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        ArmourGenerateItem generateItem = new ArmourGenerateItem(); 

        assertEquals("Armour", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        ArmourGenerateItem generateItem = new ArmourGenerateItem();

        assertEquals("Enemy Damage is halved.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        ArmourGenerateItem generateItem = new ArmourGenerateItem(); 

        assertEquals(20, generateItem.price().get());
    }

    @Test
    public void testType() {
        ArmourGenerateItem generateItem = new ArmourGenerateItem();

        assertEquals(ItemType.ARMOUR, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        ArmourGenerateItem generateItem = new ArmourGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(4));

        assertTrue(item instanceof Armour);
        assertEquals(8, item.getX());
        assertEquals(4, item.getY());
    }
}
