package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class EquippableItem extends Item implements CharacterEffect {

    public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Checks whether an item can be equipped
     * @param equippedItems All the current equipped items
     * @return boolean 
     */
    public boolean isEquippable(List<Item> equippedItems) {
        return !equippedItems.contains(this);
    }

    /**
     * Given a character's base damage, returns the modified
     * damage based on the properties of this item
     * @param target The moving entity to modify the damage to 
     * @param based damage of the equippable item
     * @return int 
     */
    public int getModifiedDamage(MovingEntity target, int baseDamage) {
        return baseDamage;
    }

    /**
     * Given the enemy's base damage, returns the
     * modified damage based on the properties of
     * this item
     * @param base damage the enemy's base damage
     * @return int 
     */
    public int getModifiedEnemyDamage(int baseDamage) {
        return baseDamage;
    }


    /**
     * Given a critical hit chance, returns the modified
     * critical hit chance based on the properties of this item
     * @param baseCriticalChance Chance of a critical hit
     * @return double
     */
    public double getModifiedCriticalChance(double baseCriticalChance) {
        return baseCriticalChance;
    }

    /**
     * Given a target enemy, applies the affect to that enemy
     * @param enemy The enemy to affect
     */
    public void affect(BasicEnemy enemy) {
        return;
    }

    /**
     * Given a character, applies the affect to the character
     * @param character The character to affect.
     */
    public void affect(Character character) {
        return;
    }
}
