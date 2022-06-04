package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.Item;

import javafx.beans.property.SimpleIntegerProperty;

public class HealthPotionGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new HealthPotionGenerateItem();

        assertTrue(generateItem instanceof HealthPotionGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        HealthPotionGenerateItem generateItem = new HealthPotionGenerateItem(); 

        assertEquals("Health Potion", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        HealthPotionGenerateItem generateItem = new HealthPotionGenerateItem();

        assertEquals("Restores character health", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        HealthPotionGenerateItem generateItem = new HealthPotionGenerateItem(); 

        assertEquals(30, generateItem.price().get());
    }

    @Test
    public void testType() {
        HealthPotionGenerateItem generateItem = new HealthPotionGenerateItem();

        assertEquals(ItemType.NOT_EQUIPPABLE, generateItem.getType());
    }

    @Test
    public void testCreateItem() {
        HealthPotionGenerateItem generateItem = new HealthPotionGenerateItem(); 
        Item item = generateItem.createItem(new SimpleIntegerProperty(8), new SimpleIntegerProperty(6));

        assertTrue(item instanceof HealthPotion);
        assertEquals(8, item.getX());
        assertEquals(6, item.getY());
    }
}
