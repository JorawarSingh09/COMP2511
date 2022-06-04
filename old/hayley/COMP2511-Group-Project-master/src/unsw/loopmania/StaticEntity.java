package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import org.javatuples.Pair;
import java.util.ArrayList;

/**
 * represents a non-moving entity
 * unlike the moving entities, this can be placed anywhere on the game map
 */
public abstract class StaticEntity extends Entity {
    /**
     * x and y coordinates represented by IntegerProperty, so ChangeListeners can be added
     */
    private IntegerProperty x, y;

    public StaticEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x
     * @return IntegerProperty
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * Gets y
     * @return IntegerProperty
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * Gets the value of x
     * @return IntegerProperty
     */
    public int getX() {
        return x().get();
    }

    /**
     * Gets the value of y
     * @return IntegerProperty
     */
    public int getY() {
        return y().get();
    }
    
    /**
     * Gets the squares adjacent to the given position, whether or not they're actually within bounds
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return list of adjacent square
     */
    public List<Pair<Integer, Integer>> getAdjacentSquares(int x, int y) {
        List<Pair<Integer, Integer>> adjacentSquares = new ArrayList<>();

        // having out of bounds squares doesn't matter
        adjacentSquares.add(new Pair<Integer, Integer>(x - 1, y));
        adjacentSquares.add(new Pair<Integer, Integer>(x, y - 1));
        adjacentSquares.add(new Pair<Integer, Integer>(x, y + 1));
        adjacentSquares.add(new Pair<Integer, Integer>(x + 1, y));

        return adjacentSquares;
    }
}
