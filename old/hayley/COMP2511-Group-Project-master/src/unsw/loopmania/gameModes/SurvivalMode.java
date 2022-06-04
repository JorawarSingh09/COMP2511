package unsw.loopmania.gameModes;

import unsw.loopmania.GameMode;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.Character;

import java.util.List;
public class SurvivalMode implements GameMode {
    public SurvivalMode() {

    }
    
    public boolean limitPurchase(Character character, GenerateItem item) {
        List<GenerateItem> alreadyPurchased = character.getPurchased();

        for (GenerateItem purchased : alreadyPurchased) {
            if (purchased instanceof HealthPotionGenerateItem) {
                if (item instanceof HealthPotionGenerateItem) {
                    return false;
                }
            }
        }

        return true;
    }
}
