package unsw.crown;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class CrownedStrategy implements CheckerStrategy {
    @Override
    public Node renderSpecial(CheckerColor color) {
        StackPane pane = new StackPane();
        Circle circle = new Circle(20, Paint.valueOf(color.getColor()));
        pane.getChildren().add(circle);

        Polygon poly = new Polygon(10, 35, 15, 25, 25, 45, 35, 25, 40, 35);
        poly.setRotate(180);
        poly.setTranslateY(-10);
        poly.setFill(Paint.valueOf(color.getSpecialColor()));
        pane.getChildren().add(poly);

        return pane;
    }

    @Override
    public Position isValid(Checkerboard board, Position position, CheckerColor color, String direction) {
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
        return null;
    }
}
