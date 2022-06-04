package unsw.loopmania.gameModes;

import unsw.loopmania.GameMode;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.Character;

import java.util.List;

public class BerserkerMode implements GameMode {
    public BerserkerMode() {

    }
    
    public boolean limitPurchase(Character character, GenerateItem item) {
        List<GenerateItem> alreadyPurchased = character.getPurchased();

        for (GenerateItem purchased : alreadyPurchased) {
            List<ItemType> validTypes = List.of(ItemType.SHIELD, ItemType.ARMOUR, ItemType.HELMET);

            if (validTypes.contains(purchased.getType())) {
                if (validTypes.contains(item.getType())) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
