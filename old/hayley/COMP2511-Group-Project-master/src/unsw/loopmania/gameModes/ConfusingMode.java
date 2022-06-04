package unsw.loopmania.gameModes;

import unsw.loopmania.GameMode;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.Character;
public class ConfusingMode implements GameMode {
    public ConfusingMode() {

    }

    public boolean limitPurchase(Character character, GenerateItem item) {
        return true;
    }

}
