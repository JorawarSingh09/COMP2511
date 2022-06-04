package unsw.loopmania.items;

import unsw.loopmania.EquippableItem;
import unsw.loopmania.RareItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.*;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.BattleBehaviourContext;

public class TreeStump extends EquippableItem implements RareItem {
    private GenerateItem itemInfo = new TreeStumpGenerateItem();
    private int reduceDamageBy;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(50);
        this.reduceDamageBy = 3;
        setType(ItemType.SHIELD);
    }

    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    /**
     * Reduces chance of critical vampire attacks by 60%
     * @param enemy The enemy to affect
     * @pre enemy != null
     */
    public void affect(BasicEnemy enemy) {
        if (enemy instanceof BattleBehaviourContext) {
            enemy.setDamage(enemy.getDamage() / this.reduceDamageBy + 1);
        } else {
            enemy.setDamage(enemy.getDamage() / this.reduceDamageBy);
        }
    }

    @Override
    public String toString() {
        return "Tree Stump";
    }
}
