package test.generateItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.AndurilGenerateItem;

public class AndurilGenerateItemTest {
    @Test
    public void testConstructor() {
        GenerateItem generateItem = new AndurilGenerateItem();

        assertTrue(generateItem instanceof AndurilGenerateItem);
        assertTrue(generateItem instanceof GenerateItem);
    }

    @Test
    public void testName() {
        AndurilGenerateItem generateItem = new AndurilGenerateItem(); 

        assertEquals("Anduril, Flame of the West", generateItem.name().get());
    }

    @Test
    public void testDescription() {
        AndurilGenerateItem generateItem = new AndurilGenerateItem();

        assertEquals("A very high damage sword which causes triple damage against bosses.", generateItem.description().get());
    }

    @Test
    public void testPrice() {
        AndurilGenerateItem generateItem = new AndurilGenerateItem(); 

        assertEquals(50, generateItem.price().get());
    }

    @Test
    public void testType() {
        AndurilGenerateItem generateItem = new AndurilGenerateItem();

        assertEquals(ItemType.WEAPON, generateItem.getType());
    }
}
