package test;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import unsw.loopmania.Character;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.Item;
import unsw.loopmania.PathPosition;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.items.Sword;

public class CharacterTest {
    /**
     * Creates the path for testing
     * just a 5x5 square loop
     */
    public List<Pair<Integer, Integer>> createPath() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();

        // add top horizontal
        for (int i = 0; i < 5; i++) {
            orderedPath.add(Pair.with(0, i));
        }
        // add right side down
        for (int i = 1; i < 5; i++) {
            orderedPath.add(Pair.with(i, 4));
        }
        // add bottom horizontal
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(4, i));
        }
        // add left side up
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(i, 0));
        }

        return orderedPath;
    }

    public Character createCharacter() {
        return new Character(new PathPosition(0, createPath()));
    }
    

    @Test
    public void testConstructor() {
        PathPosition position = new PathPosition(0, createPath());
        Character character = new Character(position);

        assertTrue(character instanceof Character);
        assertTrue(character instanceof MovingEntity);
        assertTrue(character instanceof Entity);
    }

    @Test
    public void testSetHealth() {
        Character character = createCharacter();

        character.setCurrentHealth(20);
        assertEquals(20, character.getCurrentHealth());

        character.setCurrentHealth(25);
        assertEquals(25, character.getCurrentHealth());

        character.setCurrentHealth(character.getCurrentHealth() - 30);
        assertEquals(-5, character.getCurrentHealth());
    }

    @Test
    public void testIsAlive() {
        Character character = createCharacter();

        assertTrue(character.isAlive());

        character.setCurrentHealth(30);
        assertTrue(character.isAlive());
    }

    @Test
    public void testIsDead() {
        Character character = createCharacter();

        character.setCurrentHealth(0);
        assertFalse(character.isAlive());
    }

    @Test
    public void testBaseDamage() {
        Character character = createCharacter();

        assertEquals(1, character.getDamage());
    }

    @Test
    public void testModifyDamage() {
        Character character = createCharacter();

        character.setDamage(10);

        assertEquals(10, character.getDamage());
    }

    @Test
    public void testAttack() {
        Character character = createCharacter();
        Slug enemy = new Slug(new PathPosition(0, createPath()));

        int initialHealth = enemy.getHealth();

        character.attack(enemy);

        assertEquals(initialHealth - 1, enemy.getHealth());
    }

    @Test
    public void getInventory() {
        Character character = createCharacter();

        List<Item> expectedInventory = new ArrayList<>();

        assertTrue(expectedInventory.equals(character.getInventory()));
    }

    @Test
    public void testAddItemToInventory() {
        Character character = createCharacter();
        List<Item> expectedInventory = new ArrayList<>();

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        expectedInventory.add(sword);
        character.addItemToInventory(sword);
        assertTrue(expectedInventory.equals(character.getInventory()));

        HealthPotion potion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        expectedInventory.add(potion);
        character.addItemToInventory(potion);
        assertTrue(expectedInventory.equals(character.getInventory()));
    }

    @Test
    public void testRemoveItemFromInventory() {
        Character character = createCharacter();
        List<Item> expectedInventory = new ArrayList<>();

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        HealthPotion potion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.addItemToInventory(sword);
        character.addItemToInventory(potion);
        character.removeItemFromInventory(sword);
        expectedInventory.add(potion);

        assertTrue(expectedInventory.equals(character.getInventory()));
    }

    @Test
    public void testGetEquippedItems() {
        Character character = createCharacter();
        List<Item> expectedEquippedItems = new ArrayList<>();

        assertTrue(expectedEquippedItems.equals(character.getEquippedItems()));
    }

    @Test
    public void testEquipItem() {
        Character character = createCharacter();
        List<Item> expectedEquippedItems = new ArrayList<>();

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.equipItem(sword);
        expectedEquippedItems.add(sword);
        assertTrue(expectedEquippedItems.equals(character.getEquippedItems()));

        character.equipItem(armour);
        expectedEquippedItems.add(armour);
        assertTrue(expectedEquippedItems.equals(character.getEquippedItems()));
    }

    @Test
    public void testUnequipItem() {
        Character character = createCharacter();
        List<Item> expectedEquippedItems = new ArrayList<>();

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        character.equipItem(sword);
        character.equipItem(armour);

        character.unequipItem(sword);
        expectedEquippedItems.add(armour);
        expectedEquippedItems.equals(character.getEquippedItems());

        character.unequipItem(armour);
        expectedEquippedItems.remove(armour);
        expectedEquippedItems.equals(character.getEquippedItems());
    }

    @Test
    public void testReset() {
        Character character = createCharacter();

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        character.equipItem(sword);
        character.equipItem(armour);
        character.setCurrentHealth(1);

        // Resets character to initial stats
        character.reset();

        assertEquals(character.getCurrentHealth(), 50);
        assertEquals(character.getEquippedItems().size(), 0);



    }
}
