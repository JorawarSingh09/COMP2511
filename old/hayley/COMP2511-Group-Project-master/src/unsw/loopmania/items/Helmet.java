package unsw.loopmania.items;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Helmet extends EquippableItem {
    private int reducedEnemyDamage;
    private GenerateItem itemInfo = new HelmetGenerateItem();

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        reducedEnemyDamage = 3;
        setSellPrice(15);
        setType(ItemType.HELMET);
    }

    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    /**
     * Decreases the enemy's damage by a scalar value
     * @param enemy. The enemy to affect.
     */
    @Override
    public void affect(BasicEnemy enemy) {
        enemy.setDamage(enemy.getDamage() - reducedEnemyDamage);
    }

    @Override
    public String toString() {
        return "Helmet";
    }
}
