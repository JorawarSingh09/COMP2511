package unsw.crown;

import java.util.List;
import javafx.scene.Node;

/**
 * @author Braedon Wooding, and @Hayley Gayfer
 */
public interface CheckerStrategy {
    // what about rendering?
    // maybe some sort of 'renderSpecial' to add the crowns/duck icons.
    public Node renderSpecial(CheckerColor color);

    // this is more of an aggregation idea...
    // public void addValidPositions(...);

    // what about something like?
    // public void isValid(...);
    public Position isValid(Checkerboard board, Position position, CheckerColor color, String direction);

    // is one better?  up to you how you wish to approach this...
}
