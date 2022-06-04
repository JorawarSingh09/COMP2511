package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.UsableItem;

public class ReversePathPotion extends UsableItem {
    public ReversePathPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(40);
    }

    /**
     * reverses direction
     * @param character The character which whose path it will reverse
     * @pre character != null
     */
    public void affect(Character character) {
        character.switchDirection();
    }

    @Override
    public String toString() {
        return "Reverse Path Potion";
    }
}
