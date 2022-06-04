package unsw.loopmania;

import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.generateItems.AndurilGenerateItem;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.HelmetGenerateItem;
import unsw.loopmania.generateItems.ReversePathPotionGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;

public class NPC extends StaticEntity implements CharacterPositionObserver {
    private boolean encountered;

    public NPC(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        encountered = false;
    }

    public boolean getEncountered() {
        return encountered;
    }

    /**
     * NPC dialog is triggered when character passes next to the NPC
     */
    public void encounter(Character character) {
        if (!encountered && this.shouldExist().get()) {
            List<Pair<Integer, Integer>> adjacentSquares = getAdjacentSquares(getX(), getY());
            if (adjacentSquares.contains(Pair.with(character.getX(), character.getY()))) {
                encountered = true;
                this.destroy();
            }
        }
    }

    /**
     * When called, returns a random item or returns nothing at all
     * @return a GenerateItem object corresponding to the item won
     */
    public GenerateItem gamble(Character character) {
        if (!canGamble(character)) {
            return null;
        }

        character.deductGold(5);

        // 40% chance success, 60% chance failure
        Random random = new Random();
        if (random.nextInt(100) < 60) {
            return null;
        }

        List<GenerateItem> possibleItems = List.of(
            new AndurilGenerateItem(),
            new ArmourGenerateItem(),
            new HealthPotionGenerateItem(),
            new HelmetGenerateItem(),
            new ReversePathPotionGenerateItem(),
            new ShieldGenerateItem(),
            new StaffGenerateItem(),
            new StakeGenerateItem(),
            new SwordGenerateItem(),
            new TreeStumpGenerateItem()
        );

        return possibleItems.get(random.nextInt(possibleItems.size()));
    }

    /**
     * Returns whether the character has enough gold to gamble
     * @param character
     */
    public boolean canGamble(Character character) {
        return character.getGold() >= 5;
    }
}
