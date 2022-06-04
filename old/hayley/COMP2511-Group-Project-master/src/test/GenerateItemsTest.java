package test;


import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.generateItems.HelmetGenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.GenerateItem;

public class GenerateItemsTest {
    private SwordGenerateItem swordItem = new SwordGenerateItem();
    private StaffGenerateItem staffItem = new StaffGenerateItem();
    private StakeGenerateItem stakeItem = new StakeGenerateItem();
    private ShieldGenerateItem shieldItem = new ShieldGenerateItem();
    private HelmetGenerateItem helmetItem = new HelmetGenerateItem();
    private ArmourGenerateItem armourItem = new ArmourGenerateItem();
    private TheOneRingGenerateItem theOneRingItem = new TheOneRingGenerateItem();
    private HealthPotionGenerateItem healthPotionItem = new HealthPotionGenerateItem();

    @Test
    public void testConstructor() {
        assertTrue(swordItem instanceof GenerateItem);
        assertTrue(staffItem instanceof GenerateItem);
        assertTrue(stakeItem instanceof GenerateItem);
        assertTrue(shieldItem instanceof GenerateItem);
        assertTrue(helmetItem instanceof GenerateItem);
        assertTrue(armourItem instanceof GenerateItem);
        assertTrue(theOneRingItem instanceof GenerateItem);
        assertTrue(healthPotionItem instanceof GenerateItem);
    }

    @Test
    public void testCreateItem() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);

        Item sword = swordItem.createItem(x, y);
        Item staff = staffItem.createItem(x, y);
        Item shield = shieldItem.createItem(x, y);
        Item stake = stakeItem.createItem(x, y);
        Item armour = armourItem.createItem(x, y);
        Item helmet = helmetItem.createItem(x, y);
        Item theOneRing = theOneRingItem.createItem(x, y);
        Item healthPotion = healthPotionItem.createItem(x, y);

        assertTrue(sword instanceof Sword);
        assertTrue(staff instanceof Staff);
        assertTrue(shield instanceof Shield);
        assertTrue(stake instanceof Stake);
        assertTrue(armour instanceof Armour);
        assertTrue(helmet instanceof Helmet);
        assertTrue(theOneRing instanceof TheOneRing);
        assertTrue(healthPotion instanceof HealthPotion);
    }

    @Test
    public void testGetNames() {
        assertEquals("Sword", swordItem.name().get());
        assertEquals("Staff", staffItem.name().get());
        assertEquals("Shield", shieldItem.name().get());
        assertEquals("Stake", stakeItem.name().get());
        assertEquals("Helmet", helmetItem.name().get());
        assertEquals("Armour", armourItem.name().get());
        assertEquals("The One Ring", theOneRingItem.name().get());
        assertEquals("Health Potion", healthPotionItem.name().get());
    }

    @Test
    public void testGetPrice() {
        assertTrue(swordItem.price() instanceof SimpleIntegerProperty);
        assertTrue(staffItem.price() instanceof SimpleIntegerProperty);
        assertTrue(shieldItem.price() instanceof SimpleIntegerProperty);
        assertTrue(stakeItem.price() instanceof SimpleIntegerProperty);
        assertTrue(armourItem.price() instanceof SimpleIntegerProperty);
        assertTrue(helmetItem.price() instanceof SimpleIntegerProperty);
        assertTrue(theOneRingItem.price() instanceof SimpleIntegerProperty);
        assertTrue(healthPotionItem.price() instanceof SimpleIntegerProperty);
    }
}
