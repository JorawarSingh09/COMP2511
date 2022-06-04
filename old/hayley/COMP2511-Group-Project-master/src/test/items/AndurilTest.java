package test.items;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.items.Anduril;
import unsw.loopmania.Character;
import unsw.loopmania.CustomAttackStrategy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.RareItem;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.enemies.Doggie;
import unsw.loopmania.enemies.ElanMuske;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.generateItems.AndurilGenerateItem;

public class AndurilTest {
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
        Item item = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertTrue(item instanceof Anduril);
        assertTrue(item instanceof EquippableItem);
        assertTrue(item instanceof RareItem);
        assertTrue(item instanceof CustomAttackStrategy);
    }

    @Test
    public void testType() {
        Anduril anduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)); 

        assertEquals(ItemType.WEAPON, anduril.getType());
    }

    @Test
    public void testSellPrice() {
        Anduril anduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)); 

        assertEquals(50, anduril.getSellPrice().get());
    }

    @Test
    public void testIsEquippable() {
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Character character = createCharacter();

        List<EquippableItem> equippedItems = character.getEquippedItems();
        List<Item> items = new ArrayList<Item>();
        for (Item item : equippedItems) {
            items.add(item);
        }

        assertTrue(anduril.isEquippable(items));
    }

    @Test
    public void testGetModifiedCharacterDamage() {
        // sets damage to 10
        Character character = createCharacter();
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        int initialDamage = character.getDamage();

        anduril.affect(character);

        assertEquals(initialDamage + 20, character.getDamage());
    }

    @Test
    public void testDamageAgainstBosses() {
        // sets damage to 10
        Character character = createCharacter();
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.equipItem(anduril);

        PathPosition pathPosition = new PathPosition(0, TestHelper.createPath());

        Doggie doggie = new Doggie(pathPosition);
        ElanMuske elanMuske = new ElanMuske(pathPosition);

        int initialDoggieHealth = doggie.getHealth();

        character.attack(doggie);

        assertEquals(initialDoggieHealth - 60, doggie.getHealth());


        int initialElanHealth = elanMuske.getHealth();

        character.attack(elanMuske);

        assertEquals(initialElanHealth - 60, elanMuske.getHealth());
    }

    @Test
    public void testDamageAgainstNormalEnemy() {
        // sets damage to 10
        Character character = createCharacter();
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.equipItem(anduril);

        PathPosition pathPosition = new PathPosition(0, TestHelper.createPath());

        Vampire vampire = new Vampire(pathPosition);

        int initialVampireHealth = vampire.getHealth();

        character.attack(vampire);

        assertEquals(initialVampireHealth - 20, vampire.getHealth());
    }

    @Test
    public void testGetItemDetails() {
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        GenerateItem generateItem = anduril.getItemDetails();

        assertTrue(generateItem instanceof AndurilGenerateItem);
    }
}