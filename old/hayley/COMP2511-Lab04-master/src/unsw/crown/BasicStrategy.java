package unsw.crown;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class BasicStrategy implements CheckerStrategy {
    @Override
    public Node renderSpecial(CheckerColor color) {
        StackPane pane = new StackPane();
        Circle circle = new Circle(20, Paint.valueOf(color.getColor()));
        pane.getChildren().add(circle);

        return pane;
    }

    @Override
    public Position isValid(Checkerboard board, Position position, CheckerColor color, String direction) {
        if (color.equals(CheckerColor.RED)) {
            if (direction.contains("down")) return null;
            if (board.isInBounds(position)) {
                Checker checkerPosition = board.getPieceAt(position);
                if (checkerPosition == null) {
                    return position;
                } else if (!checkerPosition.getColor().equals(color)) {
                    switch (direction) {
                        case "upLeft":
                            if (board.isInBoundsAndEmpty(position.upLeft())) return position.upLeft();
                        break;
                        case "upRight":
                            if (board.isInBoundsAndEmpty(position.upRight())) return position.upRight();
                        break;
                        default:
                            return null;
                    }
                }
            }
        } else if (color.equals(CheckerColor.WHITE)) {
            if (direction.contains("up")) return null;
            if (board.isInBounds(position)) {
                Checker checkerPosition = board.getPieceAt(position);
                if (checkerPosition == null) {
                    return position;
                } else if (!checkerPosition.getColor().equals(color)) {
                    switch (direction) {
                        case "downLeft":
                            if (board.isInBoundsAndEmpty(position.downLeft())) return position.downLeft();
                        break;
                        case "downRight":
                            if (board.isInBoundsAndEmpty(position.downRight())) return position.downRight();
                        break;
                        default:
                            return null;
                    }
                }
            }
        }

        return null;
    }
}

