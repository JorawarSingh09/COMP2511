package unsw.loopmania.cards;

import java.util.List;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.buildings.CampfireBuilding;
import unsw.loopmania.Building;

public class CampfireCard extends Card {
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    /**
     * Position is valid if it is not a path tile
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @param path the current path on the board
     * @return whether the card can be placed at the given position
     */
    public boolean isValidPosition(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Pair<Integer, Integer>> path) {
        Pair<Integer, Integer> position = Pair.with(x.get(), y.get());
        return (!path.contains(position));
    }

    /**
     * Generates a Campfire building from this card at the supplied position
     * @param x x coordinate of spawn position
     * @param y y coordinate of spawn position
     * @pre the supplied position is a valid spawn position
     * @return the generated building
     */
    public Building generateBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireBuilding(x, y);
    }   
}
