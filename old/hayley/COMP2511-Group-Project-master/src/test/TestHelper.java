package test;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class TestHelper {
   /**
     * Creates the path for testing
     * just a 5x5 square loop
     */
    public static List<Pair<Integer, Integer>> createPath() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();

        // add right side down
        for (int i = 0; i < 5; i++) {
            orderedPath.add(Pair.with(0, i));
        }
        // add bottom horizontal ->
        for (int i = 1; i < 5; i++) {
            orderedPath.add(Pair.with(i, 4));
        }
        // add left side up
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(4, i));
        }
        // add top horizontal <-
        for (int i = 4; i >= 0; i--) {
            orderedPath.add(Pair.with(i, 0));
        }

        return orderedPath;
    }

    public static List<Pair<Integer, Integer>> createSquarePath(int size, int start) {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();

        // add top horizontal
        for (int i = start; i < size; i++) {
            orderedPath.add(Pair.with(i, start));
        }
        // add right side down
        for (int i = start + 1; i < size; i++) {
            orderedPath.add(Pair.with(size - 1, i));
        }
        // add bottom horizontal
        for (int i = (size-2); i >= start; i--) {
            orderedPath.add(Pair.with(i, size-1));
        }
        // add left side up
        for (int i = (size-2); i > start; i--) {
            orderedPath.add(Pair.with(start, i));
        }
        return orderedPath;
    }

    public static Character createCharacter(List<Pair<Integer, Integer>> path) {
        return new Character(new PathPosition(0, path));
    }

    public static LoopManiaWorld createWorld(List<Pair<Integer, Integer>> path) {
        LoopManiaWorld world = new LoopManiaWorld(6, 6, path);
        world.setCharacter(createCharacter(path));
        return world;
    }

}
