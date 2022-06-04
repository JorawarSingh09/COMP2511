package unsw.automata;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;

public class GameOfLifeController {
    private final GameOfLife gameOfLife;
    private final Timeline timeline;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Button playButton;

    public GameOfLifeController() {
        gameOfLife = new GameOfLife();
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> gameOfLife.tick()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @FXML
    void playButtonOnAction(ActionEvent event) {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            playButton.setText("Stop");
        } else if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            playButton.setText("Play");
        }
    }

    @FXML
    void tickButtonOnAction(ActionEvent event) {
        gameOfLife.tick();
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                CheckBox checkbox = new CheckBox();
                gameGrid.add(checkbox, i, j);
                checkbox.selectedProperty().bindBidirectional(gameOfLife.cellProperty(i, j));
            }
        }
    }

}