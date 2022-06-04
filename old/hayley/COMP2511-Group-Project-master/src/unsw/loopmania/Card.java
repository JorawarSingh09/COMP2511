package unsw.loopmania;

import java.util.List;
import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** 
     * Determines whether or not, a location is valid, for a building.
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @param path the current path on the board
     * @return boolean
     */
    abstract public boolean isValidPosition(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Pair<Integer, Integer>> path);

    /** 
     * Generates a building.
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Building a new building
     */
    abstract public Building generateBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y);
}
