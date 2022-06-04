package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.Random;
import org.javatuples.Pair;
import unsw.loopmania.SpawnEnemyStrategy;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Building;

public class ZombiePitBuilding extends Building implements SpawnEnemyStrategy {
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Generates a zombie every cycle
     * @pre the character is currently at hero's castle
     * @param ordered path
     * @param game cycle The current game cycle
     * @return a new Zombie 
     */
    public Zombie possiblySpawnEnemy(List<Pair<Integer, Integer>> orderedPath, int gameCycle) {
        List<Pair<Integer, Integer>> adjacentPathTiles = getAdjacentSquares(getX(), getY());
        adjacentPathTiles.removeIf(p -> !orderedPath.contains(p));
        // remove hero's castle tile
        adjacentPathTiles.removeIf(p -> orderedPath.indexOf(p) == 0);

        Random random = new Random();
        Pair<Integer, Integer> spawnTile = adjacentPathTiles.get(random.nextInt(adjacentPathTiles.size()));

        return new Zombie(new PathPosition(orderedPath.indexOf(spawnTile), orderedPath));
    }
}
