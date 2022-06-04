package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterPositionObserver;
import unsw.loopmania.GenerateItem;

import unsw.loopmania.GameMode;
import unsw.loopmania.gameModes.BerserkerMode;
import unsw.loopmania.gameModes.ConfusingMode;
import unsw.loopmania.gameModes.SurvivalMode;
import unsw.loopmania.gameModes.StandardMode;

import unsw.loopmania.GenerateItem;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.HelmetGenerateItem;

public class GameModeTest {
    @Test
    public void testStandardModeConstructor() {
        GameMode standardMode = new StandardMode();

        assertTrue(standardMode instanceof StandardMode);
        assertTrue(standardMode instanceof GameMode);
    }

    @Test
    public void testSurvivalModeConstructor() {
        GameMode survivalMode = new SurvivalMode();

        assertTrue(survivalMode instanceof SurvivalMode);
        assertTrue(survivalMode instanceof GameMode);
    }

    @Test
    public void testBerserkerModeConstructor() {
        GameMode berserkerMode = new BerserkerMode();

        assertTrue(berserkerMode instanceof BerserkerMode);
        assertTrue(berserkerMode instanceof GameMode);
    }

    @Test
    public void testConfusingModeConstructor() {
        GameMode confusingMode = new ConfusingMode();

        assertTrue(confusingMode instanceof ConfusingMode);
        assertTrue(confusingMode instanceof GameMode);
    }

    @Test 
    public void testLimitPurchaseStandardMode() {
        GameMode standardMode = new StandardMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem secondArmourPurchase = new ArmourGenerateItem();

        character.addPurchase(armour);

        assertTrue(standardMode.limitPurchase(character, secondArmourPurchase));
    }

    @Test 
    public void testLimitPurchaseSurvivalMode() {
        GameMode survivalMode = new SurvivalMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem healthPotion = new HealthPotionGenerateItem();
        GenerateItem secondHealthPotionPurchase = new HealthPotionGenerateItem();

        character.addPurchase(healthPotion);

        assertFalse(survivalMode.limitPurchase(character, secondHealthPotionPurchase));
    }

    @Test 
    public void testLimitPurchaseArmourSurvivalMode() {
        GameMode survivalMode = new SurvivalMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem healthPotion = new HealthPotionGenerateItem();
        GenerateItem armourPurchase = new ArmourGenerateItem();

        character.addPurchase(healthPotion);

        assertTrue(survivalMode.limitPurchase(character, armourPurchase));
    }

    @Test 
    public void testLimitPurchaseArmourBerserkerMode() {
        GameMode berserkerMode = new BerserkerMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem secondArmourPurchase = new ArmourGenerateItem();

        character.addPurchase(armour);

        assertFalse(berserkerMode.limitPurchase(character, secondArmourPurchase));
    }

    @Test 
    public void testLimitPurchaseHelmetBerserkerMode() {
        GameMode berserkerMode = new BerserkerMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem helmetPurchase = new HelmetGenerateItem();

        character.addPurchase(armour);

        assertFalse(berserkerMode.limitPurchase(character, helmetPurchase));
    }

    @Test 
    public void testLimitPurchaseShieldBerserkerMode() {
        GameMode berserkerMode = new BerserkerMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem shieldPurchase = new ShieldGenerateItem();

        character.addPurchase(armour);

        assertFalse(berserkerMode.limitPurchase(character, shieldPurchase));
    }

    @Test 
    public void testLimitPurchaseHealthPotionBerserkerMode() {
        GameMode berserkerMode = new BerserkerMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem healthPotionPurchase = new HealthPotionGenerateItem();

        character.addPurchase(armour);

        assertTrue(berserkerMode.limitPurchase(character, healthPotionPurchase));
    }

    @Test 
    public void testLimitPurchaseConfusingMode() {
        GameMode confusingMode = new ConfusingMode();
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        GenerateItem armour = new ArmourGenerateItem();
        GenerateItem secondArmourPurchase = new ArmourGenerateItem();

        character.addPurchase(armour);

        assertTrue(confusingMode.limitPurchase(character, secondArmourPurchase));
    }
}
