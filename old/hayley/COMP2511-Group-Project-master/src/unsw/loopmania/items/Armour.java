package unsw.loopmania.items;

import unsw.loopmania.EquippableItem;
import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.generateItems.*;
import unsw.loopmania.BasicEnemy;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Armour extends EquippableItem {
    private GenerateItem itemInfo = new ArmourGenerateItem();

    // TODO write armour
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(20);
        setType(ItemType.ARMOUR);
    }

    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    /**
     * Can't equip if armour is already equipped
     * @param equipped items a list of all already equipped items
     */
    @Override
    public boolean isEquippable(List<Item> equippedItems) {
        return super.isEquippable(equippedItems);
    }

    /**
     * Halves the enemy attack damage,
     * @param enemy the enemy to affect
     */
    @Override
    public void affect(BasicEnemy enemy) {
        enemy.setDamage(enemy.getDamage() / 2);
    }

    @Override
    public String toString() {
        return "Armour";
    }
}
