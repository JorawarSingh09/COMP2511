package unsw.loopmania;

import org.javatuples.Pair;

public class Helper {
    /**
     * Returns whether the two objects are within the given radius of each other
     * @param e1
     * @param e2
     * @param radius
     * @return true if objects are within the radius of each other, false otherwise
     */
    public static boolean withinRadius(Entity e1, Entity e2, int radius) {
        return Math.pow((e1.getX() - e2.getX()), 2) + Math.pow((e1.getY() - e2.getY()), 2) <= Math.pow(radius, 2);
    }

    /**
     * Returns the distance between two coordinates
     * @param c1
     * @param c2
     * @return distance between the two coordinates
     */
    public static double distanceBetween(Pair<Integer, Integer> c1, Pair<Integer, Integer> c2) {
        return Math.sqrt(Math.pow((c1.getValue0() - c2.getValue0()), 2) + Math.pow((c1.getValue1() - c2.getValue1()), 2));
    }
}
