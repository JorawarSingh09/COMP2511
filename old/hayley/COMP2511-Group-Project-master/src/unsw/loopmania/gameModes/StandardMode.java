package unsw.loopmania.gameModes;

import unsw.loopmania.GameMode;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Character;
public class StandardMode implements GameMode {
    public StandardMode() {

    }

    public boolean limitPurchase(Character character, GenerateItem item) {
        return true;
    }
}
