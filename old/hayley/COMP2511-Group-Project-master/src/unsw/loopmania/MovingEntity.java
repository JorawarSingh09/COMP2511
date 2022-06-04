package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    /**
     * object holding position in the path
     */
    private PathPosition position;

    /**
     * Create a moving entity which moves up and down the path in position
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position) {
        super();
        this.position = position;
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    /**
     * x display getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty x() {
        return position.getX();
    }

    /**
     * y display getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty y() {
        return position.getY();
    }

    /**
     * Gets the value of x
     * @return int
     */
    public int getX() {
        return x().get();
    }

    /**
     * Gets the value of y
     * @return int 
     */
    public int getY() {
        return y().get();
    }

    /**
     * Gets the path position
     * @return PathPosition
     */
    public PathPosition getPosition() { 
        return this.position;
    }

    /**
     * Gets the ordered path
     * @return ordered path
     */
    public List<Pair<Integer, Integer>> getOrderedPath() { 
        return this.position.getOrderedPath();
    }

    /**
     * Gets position in the ordered path
     * @return int
     */
    public int getPositionInPath() { 
        return this.position.getPositionInPath();
    }

}
