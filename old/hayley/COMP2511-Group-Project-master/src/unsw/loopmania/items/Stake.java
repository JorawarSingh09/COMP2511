package unsw.loopmania.items;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.CustomAttackStrategy;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.*;
import unsw.loopmania.MovingEntity;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;

public class Stake extends EquippableItem implements CustomAttackStrategy {
    private int baseDamage;

    private GenerateItem itemInfo = new StakeGenerateItem();

    // TODO write stake
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(15);
        // has low normal stats
        baseDamage = 5;
        setType(ItemType.WEAPON);
    }

    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    /**
     * @param target The moving entity to effect
     * @param base damage The current damage 
     * @return int the current damage plus the new damage
     */
    // TODO: possibly remove? this isn't being used
    public int getModifiedDamage(MovingEntity target, int baseDamage) {
        return this.baseDamage + baseDamage;
    }

    /**
     * Adds damage to an enemy
     * If a vampire has high damage else just base damage
     * @param enemy the enemy to attack
     * @pre enemy != null
     */
    public void attack(BasicEnemy enemy, Character character) {
        if (enemy instanceof Vampire) {
            int vampireDamage = 10;
            Vampire vampire = (Vampire) enemy;
            vampire.deductHealth(vampireDamage);
        } else {
            enemy.deductHealth(baseDamage);
        }
    }    

    @Override
    public String toString() {
        return "Stake";
    }
}
