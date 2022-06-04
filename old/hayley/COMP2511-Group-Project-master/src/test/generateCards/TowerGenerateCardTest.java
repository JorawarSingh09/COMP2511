package test.generateCards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.cards.TowerCard;
import unsw.loopmania.generateCards.TowerGenerateCard;


public class TowerGenerateCardTest {
    @Test
    public void testConstructor() {
        GenerateCard generator = new TowerGenerateCard();

        assertTrue(generator instanceof TowerGenerateCard);
        assertTrue(generator instanceof GenerateCard);
    }   

    @Test
    public void testCreateCard() {
        GenerateCard generator = new TowerGenerateCard(); 
        Card card = generator.createCard(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));

        assertTrue(card instanceof TowerCard);
        assertEquals(2, card.getX());
        assertEquals(0, card.getY());
    }
}
