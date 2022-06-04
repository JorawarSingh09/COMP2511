package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterPositionObserver;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.NPC;
import unsw.loopmania.StaticEntity;

public class NPCTest {
    @Test
    public void testConstructor() {
        NPC npc = new NPC(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertTrue(npc instanceof NPC);
        assertTrue(npc instanceof StaticEntity);
        assertTrue(npc instanceof CharacterPositionObserver);
    }

    @Test
    public void testEncounter() {
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));
        character.attach(npc);

        character.moveDownPath();
        character.updateObservers();
        character.moveDownPath();
        character.updateObservers();

        assertTrue(npc.getEncountered());
    }

    @Test
    public void testNoEncounter() {
        Character character = TestHelper.createCharacter(TestHelper.createSquarePath(5, 0));
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));
        character.attach(npc);

        character.moveDownPath();
        character.updateObservers();

        assertFalse(npc.getEncountered());
    }

    @Test
    public void testGambleChance() {
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)); 
        Character character = TestHelper.createCharacter(TestHelper.createPath());
        character.addGold(5 * 1001);

        int wins = 0;
        for (int i = 0; i < 1000; i++) {
            GenerateItem gItem = npc.gamble(character);
            if (gItem != null) {
                wins++;
            }
        }

        assertTrue(wins > 350 && wins < 450);
    }

    @Test
    public void testGambleNotEnoughGold() {
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)); 
        Character character = TestHelper.createCharacter(TestHelper.createPath());
        character.addGold(4);

        for (int i = 0; i < 100; i++) {
            assertEquals(null, npc.gamble(character));
        }
        assertEquals(4, character.getGold());
    }

    @Test
    public void testCannotGamble() {
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)); 
        Character character = TestHelper.createCharacter(TestHelper.createPath());
        for (int i = 0; i < 4; i++) {
            assertFalse(npc.canGamble(character));
            character.addGold(1);
        }
    }

    @Test
    public void testCanGamble() {
        NPC npc = new NPC(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)); 
        Character character = TestHelper.createCharacter(TestHelper.createPath());
        character.addGold(5);
        assertTrue(npc.canGamble(character));
    }
}
