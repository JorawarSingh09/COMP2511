package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity implements EnemyPositionSubject {
    
    // Enemy stats
    private SimpleIntegerProperty health = new SimpleIntegerProperty();
    private SimpleIntegerProperty damage = new SimpleIntegerProperty();
    private SimpleIntegerProperty battleRadius = new SimpleIntegerProperty();
    private SimpleIntegerProperty supportRadius = new SimpleIntegerProperty();

    private List<EnemyPositionObserver> observers = new ArrayList<EnemyPositionObserver>();
    private List<Pair<GenerateItem, Double>> dropItemChances = new ArrayList<>();
    private List<Pair<GenerateCard, Double>> dropCardChances = new ArrayList<>();

    private SimpleIntegerProperty experienceGained = new SimpleIntegerProperty();
    private SimpleIntegerProperty maxGoldGained = new SimpleIntegerProperty();
    private Random chance = new Random(System.currentTimeMillis());

    public BasicEnemy(PathPosition position) {
        super(position);
    }

    /**
     * Move the enemy in random direction
     */
    public void move(int tick) {
        // this basic enemy moves in a random direction... 25% chance up or down
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    // Damage

    /**
     * Damage getter
     * @return int 
     */
    public int getDamage() {
        return damage.get();
    }

    /**
     * Basic enemy damage setter
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage.set(damage);
    }

    // health

    /**
     * Health setter
     * @param health
     */
    public void setHealth(int health) {
        this.health.set(health);
    }

    /**
     * health getter
     * @return int 
     */
    public int getHealth() {
        return health.get();
    }

    /**
     * Reduces health from current health
     * @param health
     */
    public void deductHealth(int health) {
        this.health.set(this.health.get() - health);
    }

    // battle radius

    /**
     * Battle radius setter
     * @param battleRadius
     */
    public void setBattleRadius(int battleRadius) {
        this.battleRadius.set(battleRadius);
    }

    /**
     * Battle radius getter
     * @return int 
     */
    public int getBattleRadius() {
        return battleRadius.get();
    }

    // support radius

    /**
     * Support radius setter
     * @param supportRadius
     */
    public void setSupportRadius(int supportRadius) {
        this.supportRadius.set(supportRadius);
    }

    /**
     * Support radius getter
     * @return int 
     */
    public int getSupportRadius() {
        return supportRadius.get();
    }

    // Enemy Position Subjet

    /**
     * Attaches the position observer to the enemy
     * @param observer Checks were the enemy position is 
     */
    public void attach(EnemyPositionObserver observer) {
        observers.add(observer);
    }

    /**
     * Detaches the position observer from the enemy
     * @param observer Checks were the enemy position is 
     */
    public void detach(EnemyPositionObserver observer) {
        observers.remove(observer);
    }

    /**
     * Updates the observers based on the updates of enemy positions
     */
    public void updateObservers() {
        for (EnemyPositionObserver observer : observers) {
            observer.encounter(this);
        }
    }

    /**
     * Experience gained setter
     * @param xp New xp value
     */
    public void setExperienceGained(int xp) {
        this.experienceGained.set(xp);
    }

    /**
     * Max gold gained setter
     * @param gold New gold value
     */
    public void setMaxGoldGained(int gold) {
        this.maxGoldGained.set(gold);
    }

    /**
     * Updates both the gold and xp for a character
     * @param character The current character
     */
    public void getXPAndGold(Character character) {
        int goldAmount = chance.nextInt(maxGoldGained.get());
        character.addGold(goldAmount);
        character.addXp(experienceGained.get());
    }

    /**
     * Enemy drop items setter
     * @param dropItemChances Contains different items and their value
     */
    public void setDroppableItems(List<Pair<GenerateItem, Double>> dropItemChances) {
        this.dropItemChances = dropItemChances;
    }

    /**
     * Droppable cards setter
     * @param dropCardChances Contains the differnt card types and their chances
     */
    public void setDroppableCards(List<Pair<GenerateCard, Double>> dropCardChances) {
        this.dropCardChances = dropCardChances;
    } 

    /**
     * Gets the items that the enemy has dropped
     * @return List<GenerateItem>
     */
    public List<GenerateItem> getItemDrops() {
        List<GenerateItem> droppedItems = new ArrayList<GenerateItem>();

        // For each possible drop generate a random number to see if it actually drops
        for (int i = 0; i < dropItemChances.size(); i++) {
            Double percentChance = chance.nextDouble();
            if (percentChance <= dropItemChances.get(i).getValue1()) {
                droppedItems.add(dropItemChances.get(i).getValue0());
            }
        }
        
        return droppedItems;
    }

    /**
     * Updates the character stats during a battle
     * @param character The current character which the enemy is battling
     * @pre character != null
     */
    public void attack(Character character) {
        character.setCurrentHealth(character.getCurrentHealth() - getDamage());
        List<AlliedSoldier> alliedSoldiers = character.getAlliedSoldiers();
        for (int i = character.getNumOfAlliedSoldiers() - 1; i >= 0; i--) {
            alliedSoldiers.get(i).loseHealth(getDamage());
        }
    }

    /**
     * Checks if a character is alive based on health
     * @return boolean
     */
    public boolean isAlive() {
        return (getHealth() > 0);
    }

    /**
     * Gets the cards that the enemy has dropped
     * @return List<GenerateCard> 
     */
    public List<GenerateCard> getCardDrops() {
        List<GenerateCard> droppedCards = new ArrayList<GenerateCard>();
        int drops = 0;

        // For each possible drop generate a random number to see if it actually drops
        for (int i = 0; i < dropCardChances.size(); i++) {
            Double percentChance = chance.nextDouble();
            if (percentChance <= dropCardChances.get(i).getValue1()) {
                droppedCards.add(dropCardChances.get(i).getValue0());
                drops += 1;
            }
            // maximum 2 drops
            if (drops >= 2) {
                break;
            }
        }
        
        return droppedCards;
    }
}
