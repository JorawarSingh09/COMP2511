package unsw.crown;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * @author Braedon Wooding, and @your name
 */
public class Checker {
    private CheckerColor color;

    // strategy
    private CheckerStrategy strategy;

    // you can modify the constructor in your solution.
    public Checker(CheckerColor color, boolean mad) {
        this.color = color;
        if (mad) this.strategy = new MadStrategy();
        else this.strategy = new BasicStrategy();
    }

    public void setCrowned() {
        this.strategy = new CrownedStrategy();
    }

    public void setMad() {
        this.strategy = new MadStrategy();
    }

    public boolean isMad() {
        if (this.strategy.getClass().equals(MadStrategy.class)) {
            return true;
        } else {
            return false;
        }
    }

    public CheckerColor getColor() {
        return color;
    }

    public Node getNode(int row, int col) {
        // a 'drawing' strategy could be used here too...
        // to figure out what duck image to draw.
        return strategy.renderSpecial(color);
    }

    public List<Position> validPositions(Checkerboard board, Position position) {
        List<Position> positions = new ArrayList<>();

        // Hint: You can have multiple strategies... how would you store multiple of
        // an object? Look at CheckerStrategy to see what a single strategy
        // may look like.

        Position upLeft = strategy.isValid(board, position.upLeft(), color, "upLeft");
        if (upLeft != null) positions.add(upLeft);
        
        Position upRight = strategy.isValid(board, position.upRight(), color, "upRight");
        if (upRight != null) positions.add(upRight);

        Position downLeft = strategy.isValid(board, position.downLeft(), color, "downLeft");
        if (downLeft != null) positions.add(downLeft);

        Position downRight = strategy.isValid(board, position.downRight(), color, "downRight");
        if (downRight != null) positions.add(downRight);

        return positions;
    }
}
