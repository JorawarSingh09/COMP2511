package test;

import java.util.List;

import java.util.ArrayList;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import unsw.loopmania.Character;
import unsw.loopmania.CharacterEffect;
import unsw.loopmania.Gold;
import unsw.loopmania.HerosCastleMenu;
import unsw.loopmania.PathPosition;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Battle;
import unsw.loopmania.Building;
import unsw.loopmania.Item;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.DoggieCoin;
import unsw.loopmania.items.Stake;
import unsw.loopmania.enemies.Doggie;
import unsw.loopmania.enemies.ElanMuske;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.NPC;
import unsw.loopmania.Goals.Goal;
import unsw.loopmania.Goals.CycleLeaf;
import unsw.loopmania.Goals.XpLeaf;
import unsw.loopmania.buildings.VampireCastleBuilding;
import unsw.loopmania.Goals.GoldLeaf;
import unsw.loopmania.Goals.GoalAND;
import unsw.loopmania.Entity;
import unsw.loopmania.Card;
import unsw.loopmania.cards.BarracksCard;
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.cards.VillageCard;
import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.cards.TowerCard;
import unsw.loopmania.cards.VampireCastleCard;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoopManiaWorldTest {
    private SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);
    List<Pair<Integer, Integer>> path = TestHelper.createPath();

    @Test
    public void testConstructor() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        assertTrue(world instanceof LoopManiaWorld);
    }

    @Test
    public void testGetHerosCastleMenu() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        assertTrue(world.getHerosCastleMenu() instanceof HerosCastleMenu);
    }

    @Test
    public void testGetWidthHeight() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        assertEquals(6, world.getWidth());
        assertEquals(6, world.getHeight());
    }

    @Test
    public void testSetandGetGameGoal() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Goal goal1 = new GoldLeaf(20);
        Goal goal2 = new CycleLeaf(20);
        Goal goal3 = new XpLeaf(20);

        JSONObject goal1JSON = new JSONObject(goal1);
        JSONObject goal2JSON = new JSONObject(goal2);
        JSONObject goal3JSON = new JSONObject(goal3);

        JSONArray goals = new JSONArray();
        goals.put(goal1JSON);
        goals.put(goal2JSON);

        GoalAND cyclesAndGold = new GoalAND(goals);

        JSONObject cyclesAndGoldJSON = new JSONObject(cyclesAndGold);
        JSONArray allGoalsJSON = new JSONArray();

        goals.put(cyclesAndGoldJSON);
        goals.put(goal3JSON);

        GoalAND allGoals = new GoalAND(allGoalsJSON);

        world.setGameGoal(allGoals);

        assertEquals(world.getGameGoal(), allGoals);
    }

    @Test
    public void testSetandGetCharacter() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        PathPosition newPosition = new PathPosition(0, path);

        Character newCharacter = new Character(newPosition);

        world.setCharacter(newCharacter);
        assertEquals(newCharacter, world.getCharacter());
    }

    @Test
    public void testAddEntity() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Entity newEntity = new Sword(x, y);
        world.addEntity(newEntity);
        // no getter to test this atm
    }

    @Test
    public void testSpawnRandomEnemies() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        List<BasicEnemy> spawnedEnemies = world.possiblySpawnEnemies();
        assertTrue(spawnedEnemies.size() == 1);
    }

    @Test
    public void testSpawnBuildingEnemies() {
        LoopManiaWorld world = TestHelper.createWorld(path);

    }

    @Test
    public void testSpawnGoldFrequency() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        int goldSpawns = 0;

        for (int i = 0; i < 1000; i++) {
            List<Gold> spawned = world.possiblySpawnGold();
            goldSpawns += spawned.size();

            // clean up
            for (Gold g : spawned) {
                g.destroy();
            }
            world.removeDestroyedEntities();
        }
        assertTrue(goldSpawns > 85 && goldSpawns < 115);
    }

    @Test
    public void testSellItem() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        HerosCastleMenu shop = world.getHerosCastleMenu();
        Character character = world.getCharacter();

        Item item = new Sword(x, y);

        character.addItemToInventory(item);

        world.sellItem(item);

        assertEquals(10, character.getGold());
        assertFalse(character.getInventory().contains(item));
    }

    @Test 
    public void testRemoveItem() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        Item item = new Sword(x, y);

        character.addItemToInventory(item);

        world.removeItemWhenUsed(item);
        assertFalse(character.getInventory().contains(item));
    }

    @Test
    public void testGetEnemyItemDrops() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        PathPosition newPosition = new PathPosition(0, path);
        BasicEnemy slug = new Slug(newPosition);
        BasicEnemy vampire = new Vampire(newPosition);
        BasicEnemy zombie = new Zombie(newPosition);

        List<Item> slugDrops = world.defeatedEnemyItemDrops(slug);
        List<Item> vampireDrops = world.defeatedEnemyItemDrops(vampire);
        List<Item> zombieDrops = world.defeatedEnemyItemDrops(zombie);

        assertTrue(world.getCharacter().getInventory().containsAll(slugDrops));
        assertTrue(world.getCharacter().getInventory().containsAll(vampireDrops));
        assertTrue(world.getCharacter().getInventory().containsAll(zombieDrops));
    }

    @Test
    public void testGetGoldAndXp() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        PathPosition newPosition = new PathPosition(0, path);
        BasicEnemy slug = new Slug(newPosition);
        BasicEnemy vampire = new Vampire(newPosition);
        BasicEnemy zombie = new Zombie(newPosition);

        world.getGoldAndXpDrops(slug);
        assertEquals(5, world.getCharacter().getXpProperty().get());
        assertTrue(world.getCharacter().getGold() <= 2);

        world.getGoldAndXpDrops(vampire);
        assertEquals(25, world.getCharacter().getXpProperty().get());
        assertTrue(world.getCharacter().getGold() <= 8);

        world.getGoldAndXpDrops(zombie);
        assertEquals(35, world.getCharacter().getXpProperty().get());
        assertTrue(world.getCharacter().getGold() <= 4);
    }

    @Test 
    public void testEnemyCardDrops() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        PathPosition newPosition = new PathPosition(0, path);
        BasicEnemy slug = new Slug(newPosition);
        BasicEnemy vampire = new Vampire(newPosition);
        BasicEnemy zombie = new Zombie(newPosition);

        int slugDropped = 0;
        int vampireDropped = 0;
        int zombieDropped = 0;

        for (int i = 0; i < 100; i++) {
            List<Card> slugCards = world.defeatedEnemyCardDrops(slug);
            List<Card> vampireCards = world.defeatedEnemyCardDrops(vampire);
            List<Card> zombieCards = world.defeatedEnemyCardDrops(zombie);

            slugDropped += slugCards.size();
            vampireDropped += vampireCards.size();
            zombieDropped += zombieCards.size();
    
            for (Card c : vampireCards) {
                assertFalse(c instanceof ZombiePitCard);
            }
    
            for (Card c : zombieCards) {
                assertFalse(c instanceof VampireCastleCard);
            }
        }

        assertTrue(slugDropped > 0);
        assertTrue(vampireDropped > 0);
        assertTrue(zombieDropped > 0);
    }

    @Test
    public void testRunBattles() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();
        character.moveDownPath();

        // Checks battle is run against elon, and won.
        ElanMuske musk = new ElanMuske(new PathPosition(2, path));
        musk.setHealth(0);
        world.addEnemy(musk);
        assertEquals(world.runBattles().size(), 1);
        character.moveDownPath();

        // Checks battle is run against slug, and won.
        Slug slug = new Slug(new PathPosition(3, path));
        world.addEnemy(slug);
        assertEquals(world.runBattles().size(), 1);
    }

    @Test
    public void testSetCurrentBattle() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();
        PathPosition newPosition = new PathPosition(0, path);

        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        BasicEnemy slug = new Slug(newPosition);
        enemies.add(slug);

        List<CharacterEffect> battleBuildings = new ArrayList<>();

        Battle newBattle = new Battle(character, enemies, battleBuildings, null);
        world.setCurrentBattle(newBattle);
        
        assertEquals(world.getCurrentBattle(), newBattle);
    }

    @Test
    public void testRemoveItemByCoordinates() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        Item sword = new Sword(x, y);
        character.addItemToInventory(sword);

        world.removeUnequippedInventoryItemByCoordinates(x.get(), y.get());
        assertFalse(character.getInventory().contains(sword));
    }

    @Test
    public void testEquipItemByCoordinates() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        Item sword = new Sword(x, y);
        character.addItemToInventory(sword);

        world.equipInventoryItemByCoordinates(x.get(), y.get());
        assertTrue(character.getEquippedItems().contains(sword));
        assertFalse(character.getInventory().contains(sword));
    }

    @Test
    public void testRunTickMoves() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        PathPosition expectedPosition = new PathPosition(1, path);

        world.runTickMoves();
        assertEquals(expectedPosition.getXValue(), character.getPosition().getXValue());
        assertEquals(expectedPosition.getYValue(), character.getPosition().getYValue());
    }

    @Test
    public void testFirstAvailableSlotForItem() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        Item sword = new Sword(x, y);
        Item stake = new Stake(new SimpleIntegerProperty(x.get() + 1), y);

        Pair<Integer, Integer> coords = world.getFirstAvailableSlotForItem();

        assertEquals(0, coords.getValue0());
        assertEquals(0, coords.getValue1());

        character.addItemToInventory(sword);

        Pair<Integer, Integer> coords2 = world.getFirstAvailableSlotForItem();

        assertEquals(1, coords2.getValue0());
        assertEquals(0, coords2.getValue1());

        character.addItemToInventory(stake);

        Pair<Integer, Integer> coords3 = world.getFirstAvailableSlotForItem();

        assertEquals(2, coords3.getValue0());
        assertEquals(0, coords3.getValue1());
    }

    @Test
    public void testHerosCastleEncounter() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        Character character = world.getCharacter();

        PathPosition initialPosition = character.getPosition();
        assertTrue(world.characterAtHerosCastle());

        while (character.getPosition().getXValue() != initialPosition.getXValue() ||
        character.getPosition().getYValue() != initialPosition.getYValue()) {
            world.runTickMoves();
        }

        assertTrue(world.characterAtHerosCastle());

        world.encounter(character);
        assertEquals(0, world.getGameCycle());
    }

    @Test
    public void testGameCycle() {
        LoopManiaWorld world = TestHelper.createWorld(path);
        assertEquals(0, world.getGameCycle());

        for (int i = 0; i < 5; i++) {
            world.iterateGamecycle();
        }
        assertEquals(5, world.getGameCycle());

        assertTrue(world.getGameCycleProperty() instanceof SimpleIntegerProperty);
    }

    @Test
    public void testCanBuildByCoordinatesValid() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        
        VampireCastleCard cardToAdd = new VampireCastleCard(new SimpleIntegerProperty(3), new SimpleIntegerProperty(3));
        world.addCard(cardToAdd);
        assertTrue(world.canBuildByCoordinates(3, 3, 1, 2));
    }

    @Test
    public void testCanBuildByCoordinatesNoCard() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0)); 
        assertFalse(world.canBuildByCoordinates(2, 2, 3, 3));
    }

    @Test
    public void TestCanBuildByCoordinatesDuplicate() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        
        VampireCastleBuilding castle1 = new VampireCastleBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        world.addBuilding(castle1);

        VampireCastleCard cardToAdd = new VampireCastleCard(new SimpleIntegerProperty(3), new SimpleIntegerProperty(3));
        world.addCard(cardToAdd);
        assertFalse(world.canBuildByCoordinates(3, 3, 2, 2));
    }

    @Test
    public void testNPCSpawnFrequency() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        int npcs = 0;
        for (int i = 0; i < 1000; i++) {
            List<NPC> spawned = world.possiblySpawnNPC();
            npcs += spawned.size();

            // clean up
            for (NPC npc : spawned) {
                npc.destroy();
            }
            world.removeDestroyedEntities();
        }
        assertTrue(npcs > 1 && npcs < 15);
    }

    public void testSpawnDoggie() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        Character character = world.getCharacter();

        for (int i = 0; i < 20; i++) {
            world.iterateGamecycle();
        }
        
        assertTrue(world.spawnBossEnemy() instanceof Doggie);
        assertTrue(world.spawnBossEnemy() == null);
    }

    @Test
    public void testSpawnElanMuske() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        Character character = world.getCharacter();
        character.addXp(10001);
        for (int i = 0; i < 40; i++) {
            world.iterateGamecycle();
        }
        assertTrue(world.spawnBossEnemy() instanceof ElanMuske);
        assertTrue(world.spawnBossEnemy() == null);
    }

    @Test
    public void testElanMuskeDoggieAffect() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        Character character = world.getCharacter();
        character.addXp(10001);
        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        character.addItemToInventory(doggieCoin);
        for (int i = 0; i < 40; i++) {
            world.iterateGamecycle();
        }
        world.spawnBossEnemy();
        System.out.println(doggieCoin.getSellPrice());
    }

    @Test
    public void resetGame() {
        // Tests that the world properly resets
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createSquarePath(6, 0));
        Character character = world.getCharacter();
        character.setCurrentHealth(1);
        character.moveDownPath();
        world.resetGame();
        assertEquals(character.getCurrentHealth(), 50);
        assertTrue(character.isAtHerosCastle());
    }
}
