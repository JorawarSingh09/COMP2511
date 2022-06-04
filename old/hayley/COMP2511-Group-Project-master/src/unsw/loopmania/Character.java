package unsw.loopmania;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.binding.BooleanBinding;
import org.javatuples.Pair;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity implements CharacterPositionSubject {
    // inventory > list of items
    private List<Item> inventory;
    private List<EquippableItem> equippedItems;

    // this is for keeping track in different game modes
    private List<GenerateItem> purchasedItems;

    // Position Observers
    private List<CharacterPositionObserver> observers = new ArrayList<CharacterPositionObserver>();

    // Base & Battle Damage & Health
    private SimpleIntegerProperty damage;
    private SimpleIntegerProperty baseHealth;
    private SimpleIntegerProperty currentHealth;

    // if the character is moving down direction = 0
    // if the character is moving up direction = 1
    private int direction;

    // Initial position
    private PathPosition initialPosition;
    // gold
    private SimpleIntegerProperty gold;
    // XP
    private SimpleIntegerProperty xp;

    // allied soldiers
    private List<AlliedSoldier> alliedSoldiers;


    public Character(PathPosition position) {
        super(position);

        this.initialPosition = new PathPosition(position.getPositionInPath(), position.getOrderedPath());
        this.gold = new SimpleIntegerProperty(0);
        this.xp = new SimpleIntegerProperty(0);
        this.baseHealth = new SimpleIntegerProperty(50);
        this.inventory = new ArrayList<Item>();
        this.equippedItems = new ArrayList<EquippableItem>();
        this.alliedSoldiers = new ArrayList<AlliedSoldier>();
        this.currentHealth = new SimpleIntegerProperty(50);
        this.damage = new SimpleIntegerProperty(1);
        this.inventory = new ArrayList<Item>();
        this.purchasedItems = new ArrayList<GenerateItem>();
        this.direction = 0;
    }

    /**
     * Experience property getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty getXpProperty() {
        return xp;
    }

    /**
     * Increases health by given amount, but without exceeding baseHealth
     * @param increase
     * @post health <= baseHealth
     */
    public void gainHealth(int increase) {
        this.currentHealth.set(Math.min(baseHealth.get(), currentHealth.get() + increase));
    }

    /**
     * Base health getter
     * @return int 
     */
    public int getBaseHealth() {
        return baseHealth.get();
    }

    /**
     * Current health getter
     * @return int 
     */
    public int getCurrentHealth() {
        return currentHealth.get();
    }

    public BooleanBinding canPurchase(GenerateItem item) {
        return this.getGoldProperty().lessThan(item.price());
    }

    public void addPurchase(GenerateItem item) {
        purchasedItems.add(item);
    }

    public List<GenerateItem> getPurchased() {
        return purchasedItems;
    }

    public void resetPurchases() {
        purchasedItems.clear();
    }

    /**
     * Base Health Property Getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty getBaseHealthProperty() {
        return baseHealth;
    }

    /**
     * Current Health Property Getter
     * @return SimpleIntegerProperty the current health
     */
    public SimpleIntegerProperty getCurrentHealthProperty() {
        return currentHealth;
    }

    /**
     * Returns whether or not the character is alive
     * @return boolean
     */
    public boolean isAlive() {
        return getCurrentHealth() > 0;
    }

    /**
     * Adds new experience points to current experience points
     * @param experiencePoints
     */
    public void addXp(int experiencePoints) {
        xp.set(this.xp.get() + experiencePoints);
    }

    /**
     * Current Health Setter
     * @param damage
     */
    public void setCurrentHealth(int health) {
        currentHealth.set(health);
    }

    /**
     * Base Damage Getter
     * @return int 
     */
    public int getDamage() {
        return damage.get();
    }

    /**
     * Base Damage Setter
     * @param damage int 
     */
    public void setDamage(int damage) {
        this.damage.set(damage);
    }

    /**
     * Adds an item to the character's inventory
     * @param item A new item to addd inventory
     * @pre item is not in inventory
     */
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Returns the items in the inventory
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Equips item from the inventory
     * @param item An item to equip 
     * @pre Item is not equipped
     */
    public void equipItem(EquippableItem item) {
        inventory.remove(item);
        equippedItems.add(item);
    }

    /**
     * Unequips item - moves it to inventory
     * @param item An equipped item to unequip
     * @pre Item is equipped
     */
    public void unequipItem(EquippableItem item) {
        equippedItems.remove(item);
        inventory.add(item);
    }

    /**
     * Equipped items list getter
     * @return List<EquippableItem>
     */
    public List<EquippableItem> getEquippedItems() {
        return equippedItems;
    }

    /**
     * Remove an existing item from the inventory
     * @param item An item in inventory to remove
     * @param Item is in inventory
     */
    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    /**
     * Removes and item based on its index value
     * @param index 
     * @param index < array size
     */
    public void removeItemByIndex(int index) {
        inventory.remove(index);
    }

    /**
     * Attach an observer to the character
     * @param observer The observer to attach.
     */
    public void attach(CharacterPositionObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Detach an observer to the character
     * @param observer The observer to attach.
     */
    public void detach(CharacterPositionObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Updates observers of character position
     */
    public void updateObservers() {
        for (CharacterPositionObserver observer : observers) {
            observer.encounter(this);
        }
    }

    /**
     * Determines whether or not the character is at the heros castle.
     * @return boolean indicating whether or not the character is at the castle.
     */
    public boolean isAtHerosCastle() {
        return (getPosition().getPositionPair().equals(initialPosition.getPositionPair()));
    }
    
    /**
     * Gold getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty getGoldProperty() {
        return gold;
    }

    /**
     * gets the gold directly 
     * @return int 
     */
    public int getGold() {
        return gold.get();
    }

    /**
     * Deducts the cost of something from the total amount of gold this character has
     * @param cost
     */
    public void deductGold(int cost) {
        this.gold.set(this.gold.get() - cost);
    }

    /**
     * Adds the amount of gold to the total amount of gold this character has
     * @param amount
     */
    public void addGold(int amount) {
        this.gold.set(this.gold.get() + amount);
    }
 
    /**
     * Adds an allied soldiier to the character
     * @param newSoldier
     * @pre newSoldier != NULL and is not currently in the list
     */
    public void addSoldier(AlliedSoldier newSoldier) {
        alliedSoldiers.add(newSoldier);
    }

    /**
     * Removes an ally soldier due to death / vampire attack
     * @param solider
     * @pre newSoldier != NULL and is in the list
     */
    public void loseSoldier(AlliedSoldier soldier) {
        alliedSoldiers.remove(soldier);
    }

    /**
     * Allied soldier list getter
     * @return List<AlliedSoldier> 
     */
    public List<AlliedSoldier> getAlliedSoldiers() {
        return alliedSoldiers;
    }

    /**
     * Allied soldier list getter
     * @return List<AlliedSoldier> 
     */
    public void cleanAlliedSoldiers() {
        alliedSoldiers.removeIf(alliedSoldier -> alliedSoldier.isTranced());
    }

    /**
     * Allied soldier list size getter
     * @return integer
     */
    public int getNumOfAlliedSoldiers() {
        return alliedSoldiers.size();
    }

    /**
     * Attacks an enemy 
     * Goes through and equips item 
     * Applys custom attacks if nesissary
     * Updates the enemy health
     * @param enemy A current enemy that the character is attacking
     */
    public void attack(BasicEnemy enemy) {
        for (AlliedSoldier alliedSoldier : alliedSoldiers) {
            alliedSoldier.attack(enemy);
        }
        for (EquippableItem item : getEquippedItems()) {
            if (item instanceof CustomAttackStrategy) {
                CustomAttackStrategy customAttackStrategy = (CustomAttackStrategy) item;
                customAttackStrategy.attack(enemy, this);
                return;
            }
        }
        enemy.setHealth(enemy.getHealth() - getDamage());
    }

    /**
     * Resets character to initial state.
     * @return void
     */
    public void reset() {
        while (!isAtHerosCastle()) {
            moveDownPath();
        }
        this.gold.set(0);
        this.xp.set(0);
        this.baseHealth.set(50);
        this.inventory = new ArrayList<Item>();
        this.equippedItems = new ArrayList<EquippableItem>();
        this.alliedSoldiers = new ArrayList<AlliedSoldier>();
        this.currentHealth.set(50);
        this.damage.set(1);
        this.inventory = new ArrayList<Item>();
        this.equippedItems = new ArrayList<EquippableItem>();
    }

    public void moveInDirection() {
        switch (direction) {
            case 0:
                moveDownPath();
            break;
            case 1:
                moveUpPath();
            break;
        }
    }

    public void switchDirection() {
        if (direction == 1) direction = 0;
        else direction = 1;
    }

    /**
     * Gets direction
     * @return direction int
     */
    public int getDirection() {
        return this.direction;
    }

}
