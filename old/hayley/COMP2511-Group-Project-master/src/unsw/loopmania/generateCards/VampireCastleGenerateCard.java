package unsw.loopmania.generateCards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.Card;
import unsw.loopmania.cards.VampireCastleCard;

public class VampireCastleGenerateCard extends GenerateCard {
    public VampireCastleGenerateCard() {
        super();
    }

    /**
     * Creates a new barracks card on the board
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public Card createCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleCard(x, y);
    }
}
