package unsw.loopmania.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.buildings.VampireCastleBuilding;

import java.util.List;
import org.javatuples.Pair;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        
    }

    /**
     * Position is valid if it is adjacent to a path tile and is not a path tile itself
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @param path the current path on the board
     * @return whether the card can be placed at the given position
     */
    public boolean isValidPosition(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Pair<Integer, Integer>> path) {
        Pair<Integer, Integer> position = new Pair<Integer, Integer>(x.get(), y.get());
        // can't place on a path tile
        if (path.contains(position)) {
            return false;
        }
        // check that there is an adjacent path tile
        List<Pair<Integer, Integer>> adjacentPathTiles = getAdjacentSquares(x.get(), y.get());
        adjacentPathTiles.removeIf(p -> !path.contains(p));
        return adjacentPathTiles.size() > 0;
    }

    /**
     * Generates a vampire castle building from this card at the supplied position
     * @param x x coordinate of spawn position
     * @param y y coordinate of spawn position
     * @pre the supplied position is a valid spawn position
     * @return the generated building
     */
    public Building generateBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleBuilding(x, y);
    }   
}
