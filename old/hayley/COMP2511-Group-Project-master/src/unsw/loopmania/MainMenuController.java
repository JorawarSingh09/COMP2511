package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.util.HashMap;
import javafx.scene.Scene;


/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private Popup mapOptions = new Popup();

    private HashMap<String, String> mapFilenames = new HashMap<String, String>();
    private String selectedMap;

    @FXML 
    private RadioButton standardMode;
    @FXML 
    private RadioButton survivalMode;
    @FXML 
    private RadioButton berserkerMode;
    @FXML 
    private RadioButton confusingMode;
    @FXML
    private VBox mainMenuContainer;

    public MainMenuController() {

        VBox content = new VBox();
        content.setStyle("-fx-padding: 8;" + 
        "-fx-border-style: solid inside;" + 
        "-fx-border-width: 1;" +
        "-fx-border-insets: 3;" + 
        "-fx-border-color: grey;" +
        "-fx-background-color: white;");

        HBox mapPresets = new HBox();
        mapPresets.setStyle("-fx-padding: 8;");

        mapPresets.getChildren().add(loadMapPreset("src/images/map_preset_1.png"));
        mapFilenames.put("src/images/map_preset_1.png", "world_with_twists_and_turns.json");
        mapPresets.getChildren().add(loadMapPreset("src/images/map_preset_2.png"));
        mapFilenames.put("src/images/map_preset_2.png", "winding_world.json");
        mapPresets.getChildren().add(loadMapPreset("src/images/map_preset_3.png"));
        mapFilenames.put("src/images/map_preset_3.png", "basic_world_with_player.json");

        content.getChildren().add(mapPresets);

        mapOptions.getContent().add(content);
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    @FXML
    private void openMapSelectScreen() throws IOException {
        mapOptions.show(mainMenuContainer.getScene().getWindow());
    }

    private ImageView loadMapPreset(String filename) {
        ImageView map = new ImageView(new Image((new File(filename)).toURI().toString()));

        map.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                map.setOpacity(0.50);
            }
        });
        
        map.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                map.setOpacity(1);
            }
        });

        map.setOnMouseClicked((MouseEvent e) -> {
            mapOptions.hide();
            selectedMap = new String(filename);
            System.out.println("Selected map: " + filename);
        });

        return map;
    }

    public String getSelectedMap() {
        if (selectedMap != null) return mapFilenames.get(selectedMap);
        return "world_with_twists_and_turns.json";
    }

    public int getSelectedGameMode() {
        if (standardMode.isSelected()) return 0;
        if (survivalMode.isSelected()) return 1;
        if (berserkerMode.isSelected()) return 2;
        if (confusingMode.isSelected()) return 3;
        else return 0;
    }
}
