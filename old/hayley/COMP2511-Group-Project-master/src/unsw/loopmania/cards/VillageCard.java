package unsw.loopmania.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.buildings.VillageBuilding;
import unsw.loopmania.Building;
import java.util.List;
import org.javatuples.Pair;

public class VillageCard extends Card {

    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Position is valid if it is a path tile, and not hero's castle
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @param path the curret path on the board
     * @return whether the card can be placed at the given position
     */
    public boolean isValidPosition(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Pair<Integer, Integer>> path) {
        // can't be at hero's castle
        if (path.indexOf(Pair.with(x.get(), y.get())) == 0) {
            return false;
        }
        //  only path tiles are valid
        return path.contains(Pair.with(x.get(), y.get()));
    }

    /**
     * Generates a village building from this card at the supplied position
     * @param x x coordinate of spawn position
     * @param y y coordinate of spawn position
     * @pre the supplied position is a valid spawn position
     * @return the generated building
     */
    public Building generateBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageBuilding(x, y);
    }   
}
