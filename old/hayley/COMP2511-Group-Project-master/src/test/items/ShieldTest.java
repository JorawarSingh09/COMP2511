package test.items;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Shield;
import unsw.loopmania.Character;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.Entity;

public class ShieldTest {
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
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(shield instanceof Shield);
        assertTrue(shield instanceof EquippableItem);
        assertTrue(shield instanceof Item);
        assertTrue(shield instanceof StaticEntity);
        assertTrue(shield instanceof Entity);
    }

    @Test
    public void testType() {
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        assertEquals(ItemType.SHIELD, shield.getType());
    }

    @Test
    public void testIsEquippable() {
        Character character = createCharacter();
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(shield.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Character character = createCharacter();
        Shield shield1 = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        character.equipItem(shield1);

        Shield shield2 = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        // can't equip second shield if shield is already equipped
        assertTrue(shield2.isEquippable(items)); 
        assertFalse(shield1.isEquippable(items));
    }

    @Test
    public void testGetModifiedDamage() {
        // shouldn't modify character's own damage
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Slug slug = new Slug(new PathPosition(0, createPath()));

        int initialDamage = slug.getDamage();

        shield.affect(slug);

        assertEquals(initialDamage - 4, slug.getDamage());
    }

    @Test
    public void affectVampireCritical() {
        // attack should not do anything
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Vampire vampire = new Vampire(new PathPosition(0, createPath()));

        double initialCrit = vampire.getCriticalHitChance();

        shield.affect(vampire);

        assertEquals(0.04, vampire.getCriticalHitChance());
    } 

    @Test
    public void testGetItemDetails() {
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        GenerateItem generateItem = shield.getItemDetails();

        assertTrue(generateItem instanceof ShieldGenerateItem);
    }
}
