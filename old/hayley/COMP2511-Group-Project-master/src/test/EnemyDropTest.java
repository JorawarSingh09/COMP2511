package test;

import java.util.List;

import org.javatuples.Pair;


import org.junit.jupiter.api.Test;


import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.LoopManiaWorld;


import unsw.loopmania.enemies.Vampire;

public class EnemyDropTest {
    @Test
    public void testVampireDrop() {
        List<Pair<Integer, Integer>> path = TestHelper.createPath();
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        Character character = world.getCharacter();

        Vampire testVampire = new Vampire(new PathPosition(0, path));
    }
}
