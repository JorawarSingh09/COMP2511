package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.*;
import unsw.loopmania.Character;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquippableItem {
    private int damage;

    private GenerateItem itemInfo = new SwordGenerateItem();

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        damage = 10;
        setSellPrice(10);
        setType(ItemType.WEAPON);
    }
    
    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    /**
     * Increases character damage
     * @param character the character to affect
     */
    @Override
    public void affect(Character character) {
        character.setDamage(character.getDamage() + damage);
    }

    @Override
    public String toString() {
        return "Sword";
    }
}
