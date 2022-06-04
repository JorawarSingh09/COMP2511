package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Building;
import unsw.loopmania.CharacterPositionObserver;
import unsw.loopmania.Character;

public class BarracksBuilding extends Building implements CharacterPositionObserver {
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * When a character steps on the same tile, an allied soldier is spawned
     * @param character The current character
     * @pre the character is not null
     * @post the character has not been modified in any other way except with gaining a soldier
     */
    public void encounter(Character character) {
        if (character.getX() == getX() && character.getY() == getY()) {
            AlliedSoldier soldier = new AlliedSoldier(character, false);
            character.addSoldier(soldier);
        }
    }
}
