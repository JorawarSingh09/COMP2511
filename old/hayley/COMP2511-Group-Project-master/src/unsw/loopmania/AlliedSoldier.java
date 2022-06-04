package unsw.loopmania;
import java.lang.Math;

import javafx.beans.property.SimpleIntegerProperty;

public class AlliedSoldier extends MovingEntity implements CharacterPositionObserver {
    // TODO: write allied solder
    private SimpleIntegerProperty health;
    private SimpleIntegerProperty baseDamage;
    private Character character;
    private boolean tranced;


    public AlliedSoldier(Character character, boolean isTranced) {
        super(new PathPosition(character.getPositionInPath() - 1, character.getOrderedPath()));
        health = new SimpleIntegerProperty(10);
        baseDamage = new SimpleIntegerProperty(1);
        this.character = character;
        this.tranced = isTranced;
        character.attach(this);
    }

    /**
     * Health getter
     * @return int
     */
    public int getHealth() {
        return health.get();
    }

    /**
     * Reduces the health 
     * subtracts the damage from the existing health
     * @param damage
     */
    public void loseHealth(double damage) {
        this.health.set((int) (getHealth() - damage));
        if (!isAlive()) {
            character.loseSoldier(this);
            destroy();
        }
    }

    /**
     * Checks if the allied soldier is alive based on its health
     * @return boolean
     */
    public boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * Damage getter
     * @return int 
     */
    public int getDamage() {
        return baseDamage.get();
    }

    /**
     * Attacks enemy
     * @param enemy
     */
    public void attack(BasicEnemy enemy) {
        enemy.deductHealth(getDamage());
    }

    /**
     * Returns whether or not the allied soldier is tranced (and therefore dies at the end of a battle).
     * @return
     */
    public boolean isTranced() {
        return tranced;
    }

    @Override
    public void encounter(Character character) {
        while (Math.abs(getPositionInPath() - character.getPositionInPath()) > 1) {
            switch (character.getDirection()) {
                case 0:
                    moveDownPath();
                break;
                case 1:
                    moveUpPath();
                break;
            }
        }
    }
}
