package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.UsableItem;

public class HealthPotion extends UsableItem {
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(30);
    }

    /**
     * Increases health
     * @param character The character which its going increase the health for
     * @pre character != null
     */
    public void affect(Character character) {
        character.setCurrentHealth(character.getBaseHealth());
    }

    @Override
    public String toString() {
        return "Health Potion";
    }
}
