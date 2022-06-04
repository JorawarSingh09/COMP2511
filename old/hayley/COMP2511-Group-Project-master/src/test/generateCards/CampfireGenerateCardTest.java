package test.generateCards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.generateCards.CampfireGenerateCard;


public class CampfireGenerateCardTest {
    @Test
    public void testConstructor() {
        GenerateCard generator = new CampfireGenerateCard();

        assertTrue(generator instanceof CampfireGenerateCard);
        assertTrue(generator instanceof GenerateCard);
    }   

    @Test
    public void testCreateCard() {
        GenerateCard generator = new CampfireGenerateCard(); 
        Card card = generator.createCard(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));

        assertTrue(card instanceof CampfireCard);
        assertEquals(2, card.getX());
        assertEquals(1, card.getY());
    }
}
