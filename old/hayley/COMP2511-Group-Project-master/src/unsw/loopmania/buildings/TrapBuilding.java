package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.EnemyPositionObserver;

public class TrapBuilding extends Building implements EnemyPositionObserver {
    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Deducts 3 health from any enemy that steps on this trap
     * @param enemy The current enemy that the building has encounted
     * @pre enemy != NULL and enemy does exist
     */
    public void encounter(BasicEnemy enemy) {
        if (enemy.getX() == getX() && enemy.getY() == getY() && this.shouldExist().get()) {
            enemy.deductHealth(3);
            this.destroy();
        }
    }
}
