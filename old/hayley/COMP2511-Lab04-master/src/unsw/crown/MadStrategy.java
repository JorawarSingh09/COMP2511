package unsw.crown;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class MadStrategy implements CheckerStrategy {
    @Override
    public Node renderSpecial(CheckerColor color) {
        StackPane pane = new StackPane();
        Circle circle = new Circle(20, Paint.valueOf(color.getColor()));
        pane.getChildren().add(circle);

        Polygon poly = new Polygon(14, 26, 13.3, 26, 12.5, 26, 11.8, 25.9, 11.5, 25.7, 10.8, 25.4, 10.1, 25.1, 9.5,
                24.7, 8.8, 24.4, 8.5, 24.2, 7.9, 23.8, 7.6, 23.1, 7.2, 22.5, 6.9, 21.8, 6.7, 21.5, 6.4, 20.8, 6.1,
                20.1, 6.6, 20, 7.4, 20, 7.7, 20, 8.5, 20, 9.2, 20, 10, 20, 10.5, 19.5, 10.8, 19.2, 11.3, 18.7, 11.8,
                18.2, 12.3, 17.6, 12.7, 17, 12.9, 16.7, 13.3, 16, 13.7, 15.4, 14.1, 14.8, 14.6, 14.2, 15, 13.6,
                15.2, 13.2, 15.6, 12.6, 16, 12, 16.7, 12, 17.1, 12, 17.9, 12, 18.6, 12, 19.4, 12, 20.1, 12, 20.5,
                12, 21.2, 12, 22, 12, 22.7, 12, 23.5, 12, 23.8, 12, 24.6, 12, 25.3, 12, 26, 12, 26.6, 12.6, 26.8,
                12.8, 27.4, 13.4, 27.9, 13.9, 28.4, 14.4, 28.9, 14.9, 29.2, 15.2, 29.7, 15.7, 30, 16.4, 30, 17.1,
                30, 17.9, 30, 18.2, 30, 19, 30, 19.7, 30, 20.5, 30, 21.2, 30, 21.6, 30, 22.3, 30, 23.1, 30, 23.8,
                29.6, 24.4, 29.3, 24.7, 28.8, 25.2, 28.3, 25.7, 27.7, 26.3, 27.2, 26.8, 27, 27, 26.4, 27.6, 25.9,
                28, 25.1, 28, 24.4, 28, 24, 28, 23.2, 28, 22.5, 28, 21.8, 28, 21, 28, 20.6, 28, 19.9, 28, 19.1, 28,
                18.4, 28, 17.6, 28, 17.3, 28, 16.5, 28, 15.8, 27.8, 15.3, 27.3, 14.8, 26.8, 14.5, 26.5, 14, 26);
        poly.setFill(Paint.valueOf(color.getSpecialColor()));
        poly.setTranslateX(-3);
        poly.setTranslateY(2);
        pane.getChildren().add(poly);

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
                } else {
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
                } else {
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
