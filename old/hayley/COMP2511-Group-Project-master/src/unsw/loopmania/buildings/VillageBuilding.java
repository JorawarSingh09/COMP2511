package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.CharacterPositionObserver;
import unsw.loopmania.Character;

public class VillageBuilding extends Building implements CharacterPositionObserver {
    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    /**
     * When a character steps on the same tile, they gain 10 health points
     * @param character The current character
     * @pre the character is not null
     * @post the character has not been modified in any other way except its health points
     */
    public void encounter(Character character) {
        if (character.getX() == getX() && character.getY() == getY()) {
            character.gainHealth(10);
        }
    }
}
