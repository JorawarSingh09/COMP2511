package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.EnemyPositionObserver;
import unsw.loopmania.Helper;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterEffect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

public class CampfireBuilding extends Building implements EnemyPositionObserver, CharacterEffect {
    private Map<BasicEnemy, Pair<Integer, Integer>> prevPositions;

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        prevPositions = new HashMap<>();
    }

    /**
     * Doubles the damage of the character
     */
    public void affect(Character character) {
        if (Helper.withinRadius(this, character, 2)) {
            character.setDamage(character.getDamage() * 2);
        }
    }
    
    /**
     * If a vampire moves within radius 2 of the campfire, they are moved away from the campfire
     * @param enemy
     */
    public void encounter(BasicEnemy enemy) {
        if (Helper.withinRadius(this, enemy, 2)) {
            Pair<Integer, Integer> prevPosition = prevPositions.get(enemy);

            // figure out whether the enemy moved up to get here or moved down
            PathPosition enemyPosition = enemy.getPosition();
            int positionInPath = enemyPosition.getPositionInPath();
            List<Pair<Integer, Integer>> path = enemyPosition.getOrderedPath();

            Pair<Integer, Integer> positionUp = path.get((positionInPath - 1) % path.size());
            Pair<Integer, Integer> positionDown = path.get((positionInPath + 1) % path.size());

            if (positionUp.equals(prevPosition)) {
                // since the enemy was previously up the path
                // we send them back in that direction
                enemy.moveUpPath();
                enemy.moveUpPath();
            } else if (positionDown.equals(prevPosition)) {
                // since the enemy was previously down the path
                // we send them back in that direction
                enemy.moveDownPath();
                enemy.moveDownPath();
            } else {
                // prevPosition did not exist - send the enemy to which direction will be furthest from the campfire
                Pair<Integer, Integer> campfirePosition = Pair.with(this.getX(), this.getY());
                if (Helper.distanceBetween(campfirePosition, positionUp) > Helper.distanceBetween(campfirePosition, positionDown)) {
                    enemy.moveUpPath();
                } else {
                    enemy.moveDownPath();
                }
            }
        }

        prevPositions.put(enemy, enemy.getPosition().getPositionPair());
    }
}
