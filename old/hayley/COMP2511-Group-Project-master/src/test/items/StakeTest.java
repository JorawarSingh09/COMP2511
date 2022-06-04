package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.items.Stake;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Entity;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.generateItems.StakeGenerateItem;

public class StakeTest {
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
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertTrue(stake instanceof Stake);
        assertTrue(stake instanceof EquippableItem);
        assertTrue(stake instanceof Item);
        assertTrue(stake instanceof StaticEntity);
        assertTrue(stake instanceof Entity);
    }

    @Test
    public void testType() {
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 

        assertEquals(ItemType.WEAPON, stake.getType());
    }

    @Test
    public void testSellPrice() {
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  

        assertEquals(15, stake.getSellPrice().get());
    }

    @Test
    public void testIsEquippable() {
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = createCharacter();
        
        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(stake.isEquippable(items));
    }

    @Test
    public void testDuplicateIsEquippable() {
        Stake stake1 = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  
        Character character = createCharacter(); 

        character.equipItem(stake1);

        Stake stake2 = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));  
        
        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        // can't equip stake if another stake is equipped
        assertTrue(stake2.isEquippable(items));
        assertFalse(stake1.isEquippable(items));
    }

    @Test
    public void testAttackRegularEnemy() {
        // damage on slugs and zombies is 5
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Character character = createCharacter();

        Slug slug = new Slug(new PathPosition(0, createPath()));
        Zombie zombie = new Zombie(new PathPosition(0, createPath()));

        int initialSlugHealth = slug.getHealth();
        int initialZombieHealth = zombie.getHealth();

        stake.attack(slug, character);
        stake.attack(zombie, character);

        assertEquals(initialSlugHealth - 5, slug.getHealth());
        assertEquals(initialZombieHealth - 5, zombie.getHealth());
    }

    @Test
    public void testAttackVampire() {
        // 10 points damage on vampires
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Vampire vampire = new Vampire(new PathPosition(0, createPath()));
        Character character = createCharacter();

        int initialVampireHealth = vampire.getHealth();

        stake.attack(vampire, character);
        System.err.println(vampire.getHealth());
        assertEquals(initialVampireHealth - 10, vampire.getHealth());
    }

    @Test
    public void testGetModifiedDamage() {
        // TODO: this test might become redundant
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Zombie zombie = new Zombie(new PathPosition(0, TestHelper.createPath()));

        assertEquals(10, stake.getModifiedDamage(zombie, 5));
        assertEquals(12, stake.getModifiedDamage(zombie, 7));
    }

    @Test
    public void testGetItemDetails() {
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        GenerateItem generateItem = stake.getItemDetails();

        assertTrue(generateItem instanceof StakeGenerateItem);
    }
}
