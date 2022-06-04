package test.buildings;


import javafx.beans.property.SimpleIntegerProperty;
import test.TestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Battle;
import unsw.loopmania.Building;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterEffect;
import unsw.loopmania.EnemyPositionObserver;
import unsw.loopmania.Entity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.buildings.CampfireBuilding;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.enemies.Vampire;

public class CampfireBuildingTest {
    @Test
    public void testConstructor() {
        Building building = new CampfireBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

        assertTrue(building instanceof CampfireBuilding);
        assertTrue(building instanceof Building);
        assertTrue(building instanceof StaticEntity);
        assertTrue(building instanceof Entity);
        assertTrue(building instanceof EnemyPositionObserver);

        assertEquals(2, building.getX());
        assertEquals(3, building.getY());
    }

    @Test
    public void testCharacterDamage() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        List<CharacterEffect> battleBuildings = new ArrayList<>();
        battleBuildings.add(campfire);

        // damage = 1, health = 50
        Character character = new Character(new PathPosition(2, path));
        // damage = 1, health = 4
        Slug slug = new Slug(new PathPosition(2, path));
        List<BasicEnemy> enemies = new ArrayList<>();
        enemies.add(slug);

        Battle battle = new Battle(character, enemies, battleBuildings, null);
        battle.runBattle();

        // character should have killed slug in two hits, meaning slug would have done 3 damage on the character
        assertFalse(slug.isAlive());
        assertEquals(47, character.getCurrentHealth());
    }

    @Test
    public void testOutsideRadius() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0);
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(4));
        List<CharacterEffect> battleBuildings = new ArrayList<>();
        battleBuildings.add(campfire);

        // damage = 1, health = 50
        Character character = new Character(new PathPosition(1, path));
        // damage = 2, health = 4
        Slug slug = new Slug(new PathPosition(1, path));
        List<BasicEnemy> enemies = new ArrayList<>();
        enemies.add(slug);

        Battle battle = new Battle(character, enemies, battleBuildings, null);
        battle.runBattle();

        // without campfire effect
        // character should have killed slug in 4 hits, meaning slug would have done 9 damage on the character
        assertFalse(slug.isAlive());
        assertEquals(41, character.getCurrentHealth()); 
    }

    @Test
    public void testEffectDisappears() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(5, 0);
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        List<CharacterEffect> battleBuildings = new ArrayList<>();
        battleBuildings.add(campfire);

        // damage = 1, health = 50
        Character character = new Character(new PathPosition(2, path));
        // damage = 1, health = 4
        Slug slug = new Slug(new PathPosition(2, path));
        List<BasicEnemy> enemies = new ArrayList<>();
        enemies.add(slug);

        Battle battle = new Battle(character, enemies, battleBuildings, null);
        battle.runBattle();
        battle.resetCharacter();

        // character should have killed slug in two hits, meaning slug would have done 3 damage on the character
        assertFalse(slug.isAlive());
        assertEquals(47, character.getCurrentHealth()); 

        // move character down and battle again - now out of effect zone
        character.moveDownPath();
        Slug slug2 = new Slug(new PathPosition(3, path));
        List<BasicEnemy> enemies2 = new ArrayList<>();
        enemies2.add(slug2);

        Battle battle2 = new Battle(character, enemies, battleBuildings, null);
        battle2.runBattle();
        battle.resetCharacter();

        // character should have killed slug in 4 hits
        // the slug would have done 9 damage on the character
        assertFalse(slug2.isAlive());
        assertEquals(38, character.getCurrentHealth());
    }

    @Test
    public void testVampireEncounter() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0); 

        // campfire has effect radius = 2
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));
        Vampire vampire = new Vampire(new PathPosition(4, path));
        vampire.attach(campfire);
        // update observers as soon as you spawn
        vampire.updateObservers();

        vampire.moveUpPath();
        vampire.updateObservers();

        // vampire should be sent the other way
        assertEquals(5, vampire.getX());
        assertEquals(0, vampire.getY());
    }

    @Test
    public void testVampireEncounterOtherSide() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0); 

        // campfire has effect radius = 2
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(5), new SimpleIntegerProperty(1));
        Vampire vampire = new Vampire(new PathPosition(3, path));
        vampire.attach(campfire);
        vampire.updateObservers();

        vampire.moveDownPath();
        vampire.updateObservers();

        // vampire should be sent the other way
        assertEquals(2, vampire.getX());
        assertEquals(0, vampire.getY());
    }

    @Test
    public void testVampireSpawnInRadius() {
        List<Pair<Integer, Integer>> path = TestHelper.createSquarePath(6, 0); 

        // campfire has effect radius = 2
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(5), new SimpleIntegerProperty(1));
        // vampire spawns within the effect radius
        Vampire vampire = new Vampire(new PathPosition(4, path));
        vampire.attach(campfire);
        vampire.updateObservers();

        // vampire should be sent the other way
        assertEquals(3, vampire.getX());
        assertEquals(0, vampire.getY());
    }
}
