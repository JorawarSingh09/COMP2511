package unsw.loopmania;

import java.util.List;
import org.javatuples.Pair;

public interface SpawnEnemyStrategy {
    public BasicEnemy possiblySpawnEnemy(List<Pair<Integer, Integer>> orderedPath, int gameCycle);
}
