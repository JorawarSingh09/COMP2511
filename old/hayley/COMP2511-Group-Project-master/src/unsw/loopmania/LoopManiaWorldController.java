package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;


import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;
import unsw.loopmania.Goals.Goal;
import unsw.loopmania.buildings.BarracksBuilding;
import unsw.loopmania.buildings.CampfireBuilding;
import unsw.loopmania.buildings.HerosCastleBuilding;
import unsw.loopmania.buildings.TowerBuilding;
import unsw.loopmania.buildings.TrapBuilding;
import unsw.loopmania.buildings.VampireCastleBuilding;
import unsw.loopmania.buildings.VillageBuilding;
import unsw.loopmania.buildings.ZombiePitBuilding;
import unsw.loopmania.cards.BarracksCard;
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.cards.TowerCard;
import unsw.loopmania.cards.TrapCard;
import unsw.loopmania.cards.VampireCastleCard;
import unsw.loopmania.cards.VillageCard;
import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.enemies.Doggie;
import unsw.loopmania.enemies.ElanMuske;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.enemies.Vampire;
import unsw.loopmania.enemies.Zombie;
import unsw.loopmania.gameModes.StandardMode;
import unsw.loopmania.gameModes.SurvivalMode;
import unsw.loopmania.generateItems.AndurilGenerateItem;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.generateItems.DoggieCoinGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.HelmetGenerateItem;
import unsw.loopmania.generateItems.ReversePathPotionGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;
import unsw.loopmania.items.Anduril;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.DoggieCoin;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.items.TreeStump;
import unsw.loopmania.items.ReversePathPotion;
import unsw.loopmania.gameModes.BerserkerMode;
import unsw.loopmania.gameModes.ConfusingMode;


import java.util.EnumMap;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * container for all battle interactions
     */
    @FXML
    private VBox battle;

    @FXML
    private ImageView heroBattle;

    @FXML
    private ImageView enemyBattle;

    @FXML
    private ImageView bossBattle;

    @FXML
    private Button finishBattleButton;

    @FXML
    private ProgressBar enemyHealth;

    @FXML
    private ProgressBar characterHealth;

    @FXML
    private ProgressBar bossHealth;

    @FXML
    private Text enemiesLeft;

    @FXML
    private Text alliedSoldiersCount;

    @FXML
    private HBox gameOver;


    @FXML
    Button pauseButton;

    @FXML 
    Button muteButton;

    /**
     * container for all hero castle menu components
     */
    @FXML
    private ScrollPane heroCastle;

    /**
     * container for all game map components
     */
    @FXML 
    private VBox gameMap;

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * shopItems gridPane includes shop items
     */
    @FXML
    private GridPane shopItems;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane equippedArmour;

    @FXML
    private GridPane equippedShield; 

    @FXML
    private GridPane equippedWeapon;

    @FXML
    private GridPane equippedHelmet;

    @FXML
    private GridPane equippedAccessory;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Text gameModeDisplay;

    @FXML
    private Text cycleDisplay;

    @FXML
    private Text healthDisplay;

    @FXML
    private Text baseHealthDisplay;

    @FXML
    private Text goldDisplay;

    @FXML
    private Text xpDisplay;

    // All the media players

    @FXML
    MediaPlayer battleMusicPlayer;

    @FXML
    Media backgroundMedia;

    @FXML
    MediaPlayer backgroundMusicPlayer;

    @FXML
    MediaPlayer goldCollectingPlayer;

    @FXML
    MediaPlayer goldLossPlayer;

    @FXML
    MediaPlayer healthIncreasePlayer;

    @FXML
    MediaPlayer gameOverPlayer;

    SimpleIntegerProperty oldGoldCount;


    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    // maps objects to images
    private HashMap<Class<?>, Image> imageMap;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private boolean isMuted;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);
        imageMap = createImageMap();
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    /**
     * Getter for the current world
     * @return LoopManiaWorld the current world
     */
    public LoopManiaWorld getWorld() {
        return world;
    }

    /**
     * Sets up the board and world 
     */
    @FXML
    public void initialize() {
    
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Image test = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        // enemyBattle.setImage(test);
        // enemyBattle.setFitHeight(25);
        // enemyBattle.setFitWidth(25);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // add the starting heros castle shop items

        List<GenerateItem> itemMenu = world.getHerosCastleMenu().getItems();
        int row = 0;
        int col = 0;
        for (int i = 0; i < itemMenu.size(); i++) {
            VBox newTag = loadShopTag(itemMenu.get(i));
            if (col < 2) {
                shopItems.add(newTag, row, col);
                col++;
            } else {
                row += 1;
                col = 0;
                shopItems.add(newTag, row, col);
                col++;
            }
        }

        // Create all sounds and their respective media players
        Media sound = new Media(new File("src/sounds/battle.wav").toURI().toString());
        battleMusicPlayer = new MediaPlayer(sound);

        Media goldCollecting = new Media(new File("src/sounds/goldCollecting.wav").toURI().toString());
        goldCollectingPlayer = new MediaPlayer(goldCollecting);

        Media goldLoss = new Media(new File("src/sounds/goldLoss.wav").toURI().toString());
        goldLossPlayer = new MediaPlayer(goldLoss);

        Media healthIncrease = new Media(new File("src/sounds/healthIncrease.wav").toURI().toString());
        healthIncreasePlayer = new MediaPlayer(healthIncrease);

        Media gameOverSound = new Media(new File("src/sounds/gameOver.wav").toURI().toString());
        gameOverPlayer = new MediaPlayer(gameOverSound);

        battle.prefWidthProperty().bind(anchorPaneRoot.widthProperty());
        battle.prefHeightProperty().bind(anchorPaneRoot.heightProperty());
        heroCastle.setPrefWidth(320);

        // bind the game states layout to their visibility
        heroCastle.managedProperty().bind(heroCastle.visibleProperty());
        gameMap.managedProperty().bind(gameMap.visibleProperty());
        battle.managedProperty().bind(battle.visibleProperty());
        gameOver.managedProperty().bind(gameOver.visibleProperty());

        // heros castle menu not visible in the beginning
        heroCastle.setVisible(false);
        battle.setVisible(false);
        gameOver.setVisible(false);

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        // make bindings for stats
        cycleDisplay.textProperty().bindBidirectional(world.getGameCycleProperty(), new NumberStringConverter());
        healthDisplay.textProperty().bindBidirectional(world.getCharacter().getCurrentHealthProperty(), new NumberStringConverter());
        baseHealthDisplay.textProperty().bindBidirectional(world.getCharacter().getBaseHealthProperty(), new NumberStringConverter());
        goldDisplay.textProperty().bindBidirectional(world.getCharacter().getGoldProperty(), new NumberStringConverter());
        xpDisplay.textProperty().bindBidirectional(world.getCharacter().getXpProperty(), new NumberStringConverter());

        
        world.getCharacter().getGoldProperty().addListener(new ChangeListener<Number>()   {
            @Override
            public void changed(ObservableValue<? extends Number> observalbe, Number oldNumber, Number newNumber) {
                if (oldNumber.doubleValue() < newNumber.doubleValue()) {
                    System.out.println("GoldCollected");
                    goldCollectingPlayer.seek(Duration.ZERO);
                    goldCollectingPlayer.play();
                } else {
                    goldLossPlayer.seek(Duration.ZERO);
                    goldLossPlayer.play();
                }
            }

        });

        world.getCharacter().getCurrentHealthProperty().addListener(new ChangeListener<Number>()   {
            @Override
            public void changed(ObservableValue<? extends Number> observalbe, Number oldNumber, Number newNumber) {
                if (oldNumber.doubleValue() < newNumber.doubleValue()) {
                    System.out.println("health increase");
                    healthIncreasePlayer.seek(Duration.ZERO);
                    healthIncreasePlayer.play();
                }
            }
        });


        onLoad(world.loadCard());
        onLoad(world.loadCard());
    }

    public void setLoopManiaGameMode(int gameMode) {
        String path = "";
        switch (gameMode) {
            case 0:
                GameMode standardMode = new StandardMode();
                world.setGameMode(standardMode);
                gameModeDisplay.setText("Standard Mode");
                path = "src/sounds/standardModeMusic.wav";
            break;
            case 1:
                GameMode survivalMode = new SurvivalMode();
                world.setGameMode(survivalMode);
                gameModeDisplay.setText("Survival Mode");
                path = "src/sounds/survivalModeMusic.wav";
            break;
            case 2:
                GameMode berserkerMode = new BerserkerMode();
                world.setGameMode(berserkerMode);
                gameModeDisplay.setText("Berserker Mode");
                path = "src/sounds/berserkerModeMusic.wav";
            break;
            case 3:
                GameMode confusingMode = new ConfusingMode();
                world.setGameMode(confusingMode);
                gameModeDisplay.setText("Confusing Mode");
                path = "src/sounds/confusingModeMusic.wav";
            break;
        }
        // Create background music media player
        backgroundMedia = new Media(new File(path).toURI().toString());
        backgroundMusicPlayer = new MediaPlayer(backgroundMedia);
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        System.out.println("starting timer");
        gameOver.setVisible(false);
        heroCastle.setVisible(false);
        gameMap.setVisible(true);
        isPaused = false;
        isMuted = false;
        backgroundMusicPlayer.play();
        System.out.println("game mode " + gameModeDisplay.getText());
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            world.runTickMoves();

            if (evaluate(world.getGameGoal())) {
                terminate();
            }
            // check if character is at heros castle
            if (this.world.characterAtHerosCastle() && this.world.getGameCycle() != 0) {
                world.getCharacter().resetPurchases();
                heroCastle.setVisible(true);
                gameMap.setVisible(false);
                pauseButton.setText("Start");
                pause();
            }

            List<BasicEnemy> defeatedEnemies = world.runBattles();
            
            // check if character is in a battle
            if (this.world.getCurrentBattle() != null) {
                backgroundMusicPlayer.pause();
                battle.setVisible(true);
                gameMap.setVisible(false);
                finishBattleButton.setVisible(false);
                pause();
                SequentialTransition battleSequence = new SequentialTransition();
                List<Frame> frames = world.getCurrentBattle().runBattle();
                enemyBattle.setImage(imageMap.get(frames.get(0).getEnemy().getClass()));
                enemiesLeft.setText(frames.get(0).getEnemiesLeft() + " Enemies Left");
                alliedSoldiersCount.setText("You have " + frames.get(0).getNumOfAlliedSoldiers() + " allied soldiers.");
                characterHealth.setProgress(frames.get(0).getCharacterHealth());
                enemyHealth.setProgress(frames.get(0).getEnemyHealth());
                if (frames.get(0).getBossHealth() != 0) {
                    bossHealth.setVisible(true);
                    bossHealth.setProgress(frames.get(0).getBossHealth());
                    bossBattle.setVisible(true);
                    bossBattle.setImage(imageMap.get(frames.get(0).getBoss().getClass()));
                } else {
                    bossBattle.setVisible(false);
                    bossHealth.setVisible(false);
                }
                for (int i = 1; i < (frames.size()); i++) {
                    battleSequence.getChildren().add(animateBattleFrame(frames.get(i)));
                }
                battleSequence.setCycleCount(1);
                battleSequence.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        characterHealth.setProgress(frames.get(frames.size() - 1).getCharacterHealth());
                        enemyHealth.setProgress(frames.get(frames.size() - 1).getEnemyHealth());
                        battleMusicPlayer.stop();
                        world.getCurrentBattle().resetCharacter();
                        if (!world.getCurrentBattle().wonBattle()) {
                            terminate();

                        } else {
                            for (BasicEnemy e: defeatedEnemies){
                                reactToEnemyDefeat(e);
                            }
                            enemiesLeft.setText("You Won!");
                            enemiesLeft.setText("0 Enemies Left");
                            alliedSoldiersCount.setText("You have " + world.getCharacter().getNumOfAlliedSoldiers() + " allied soldiers.");            
                            finishBattleButton.setVisible(true);
                        }
                    }
                });
                battleSequence.play();
                battleMusicPlayer.play();
            }

            // check if there's any encounters with NPCs
            NPC encounteredNpc = world.getNPCEncounter();
            // run popup if there's no battle in progress
            if (encounteredNpc != null && world.getCurrentBattle() == null) {
                pause();
                Popup npcPopup = loadNPCPopup(encounteredNpc);
                npcPopup.show(anchorPaneRoot.getScene().getWindow());
            }
            
            List<BasicEnemy> otherDefeatedEnemies = world.otherDefeatedEnemies();
            for (BasicEnemy e: otherDefeatedEnemies) {
                reactToEnemyDefeat(e);
            }
            world.removeDestroyedEntities();

            List<BasicEnemy> newEnemies = world.possiblySpawnEnemies();
            for (BasicEnemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }
            // spawn boss if applicable
            BasicEnemy spawningBoss = world.spawnBossEnemy();
            if (spawningBoss != null) {
                onLoad(spawningBoss);
            }

            List<Gold> newGold = world.possiblySpawnGold();
            for (Gold gold: newGold) {
                onLoad(gold);
            }

            List<NPC> newNPCs = world.possiblySpawnNPC();
            for (NPC npc: newNPCs) {
                onLoad(npc);
            }

            AlliedSoldier newSoldier = world.getAlliedSoldiers();
            if (newSoldier != null) {
                onLoad(newSoldier);
            }
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        // mediaPlayer.play();
        timeline.stop();
        battleMusicPlayer.pause();
        backgroundMusicPlayer.pause();
    }

    public void endGame() {
        timeline.stop();
        world.resetGame();
        battleMusicPlayer.stop();
        backgroundMusicPlayer.stop();
    }

    /**
     * End the game due to success criteria / loss
     */
    public void terminate() {
        gameOver.setVisible(true);
        gameOverPlayer.play();
        gameMap.setVisible(false);
        battle.setVisible(false);
        endGame();
        world.setCurrentBattle(null);
    }

    /**
     * Restart game after shop has been open
     */
    public void resumeGameFromShop() {
        heroCastle.setVisible(false);
        gameMap.setVisible(true);
    }

    /**
     * Checks if the current stats meet the goal
     * @param expression the game current goal
     * @return boolean
     */
    public boolean evaluate(Goal expression) {
        // Return the expression evaluated
        return expression.metGoal(this.getWorld());
    }

    /**
     * Go through and get an item from heros castle menu
     * @param item An item to get from the shop
     */
    public void purchaseItemFromShop(GenerateItem item) {
        Item newItem = world.purchaseItemFromHerosCastle(item);

        if (newItem != null) {
            onLoad(newItem);
        }
    }

    /**
     * Loads the popup the handles interaction with an NPC
     * @param npc being interacted with
     * @return the popup panel to interact with the NPC
     */
    private Popup loadNPCPopup(NPC npc) {
        Popup npcPopup = new Popup();

        VBox popupBox = new VBox(10);
        popupBox.setStyle("-fx-padding: 8;" + 
        "-fx-border-style: solid inside;" + 
        "-fx-border-width: 1;" +
        "-fx-border-insets: 3;" + 
        "-fx-border-color: grey;" +
        "-fx-background-color: white;");

        // close button
        Button hide = new Button("X");
        hide.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                npcPopup.hide();

                try {
                    pauseGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); 
        hide.setStyle("-fx-font-family: 'Avenir Book'");

        HBox closeButtonRow = new HBox();
        closeButtonRow.getChildren().add(hide);
        popupBox.getChildren().add(closeButtonRow);

        // description
        VBox chatBox = new VBox();
        Label chatMsg = new Label("Greetings, I am a friendly inhabitant of this world. I would like to propose a trade offer. You give me 5 gold coin, and I will give you a chance to win an item. Will you try your luck?");
        chatMsg.setStyle("-fx-font-family: 'Avenir Book'");
        chatMsg.setWrapText(true);
        chatMsg.setPrefWidth(300);
        chatBox.getChildren().add(chatMsg);

        popupBox.getChildren().add(chatBox);

        // slot machine
        HBox slotMachine = new HBox();
        List<StackPane> slotGrids = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            StackPane slot = new StackPane();
            slotGrids.add(slot);
            slotMachine.getChildren().add(slot);
        }
        
        slotMachine.setAlignment(Pos.CENTER);
        popupBox.getChildren().add(slotMachine);

        // where the text announcing the result will go
        VBox result = new VBox();
        result.setAlignment(Pos.CENTER);
        popupBox.getChildren().add(result);

        // buttons for actions
        HBox actionsRow = new HBox();
        actionsRow.setAlignment(Pos.CENTER);
        Button yesGamble = new Button("Yes! (5 gold)");
        Button noThanks = new Button("No thanks");

        yesGamble.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                actionsRow.getChildren().remove(noThanks);
                actionsRow.getChildren().remove(yesGamble);

                if (!npc.canGamble(world.getCharacter())) {
                    Label msg = new Label("Not enough gold :(");
                    msg.setWrapText(true);
                    msg.maxWidth(200);
                    msg.setStyle("-fx-font-family: 'Avenir Next'"); 
                    msg.setAlignment(Pos.CENTER);
                    result.getChildren().add(msg);
                    return;
                }

                // trigger gambling
                Item itemWon = world.gambleWithNPC(world.getCharacter(), npc);

                Timeline slot1 = createItemCycle(slotGrids.get(0), 30);
                Timeline slot2 = createItemCycle(slotGrids.get(1), 40);
                Timeline slot3 = createItemCycle(slotGrids.get(2), 50);

                slot1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        if (itemWon != null) {
                            slotGrids.get(0).getChildren().remove(0);
                            slotGrids.get(0).getChildren().add(createImageView(itemWon));
                        }
                    }
                });

                slot2.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        if (itemWon != null) {
                            slotGrids.get(1).getChildren().remove(0);
                            slotGrids.get(1).getChildren().add(createImageView(itemWon));
                        }
                    }
                }); 

                slot3.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        Label winLoseMsg;
                        if (itemWon != null) {
                            slotGrids.get(2).getChildren().remove(0);
                            slotGrids.get(2).getChildren().add(createImageView(itemWon));
                            winLoseMsg = new Label("You won a " + itemWon.toString() + "!");
                            onLoad(itemWon); 
                        } else {
                            winLoseMsg = new Label("You did not win anything :( Better luck next time."); 
                        }
                        winLoseMsg.setWrapText(true);
                        winLoseMsg.maxWidth(200);
                        winLoseMsg.setStyle("-fx-font-family: 'Avenir Next'");
                        winLoseMsg.setAlignment(Pos.CENTER);
                        result.getChildren().add(winLoseMsg);
                    }
                });
        
                slot1.play();
                slot2.play();
                slot3.play();
            }
        }); 
        yesGamble.setStyle("-fx-font-family: 'Avenir Book'");
        actionsRow.getChildren().add(yesGamble);

        noThanks.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                npcPopup.hide();
                try {
                    pauseGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); 
        noThanks.setStyle("-fx-font-family: 'Avenir Book'");
        actionsRow.getChildren().add(noThanks);
        popupBox.getChildren().add(actionsRow);

        npcPopup.getContent().add(popupBox);

        return npcPopup;
    }

    /**
     * Creates a timeline which will cycle through item images
     * @param grid the pane on which the images should be displayed
     * @param cycleCount the number of cycles the animation will last for
     * @return the animated timeline
     */
    private Timeline createItemCycle(Pane grid, int cycleCount) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            Image newItem = getRandomItemImage();
            if (grid.getChildren().size() > 0) {
                grid.getChildren().remove(0);
            }
            grid.getChildren().add(new ImageView(newItem));
        }));
        timeline.setCycleCount(cycleCount);
        return timeline;
    }

    /**
     * Returns an image of an item at random
     * @return the random image
     */
    private Image getRandomItemImage() {
        Random random = new Random();
        List<Class<?>> itemKeys = new ArrayList<>(imageMap.keySet());
        itemKeys.removeIf(k -> !Item.class.isAssignableFrom(k));

        return imageMap.get(itemKeys.get(random.nextInt(itemKeys.size())));
    }

    /**
     * Creates the popup with an item's information
     * @param item which will be displayed
     * @return popup displaying the item's info
     */
    public Popup loadPopupInfo(Item item) {
        Popup itemDetailsPopup = new Popup();

        VBox itemInfo = new VBox();
        itemInfo.setStyle("-fx-padding: 8;" + 
        "-fx-border-style: solid inside;" + 
        "-fx-border-width: 1;" +
        "-fx-border-insets: 3;" + 
        "-fx-border-color: grey;" +
        "-fx-background-color: white;");

        Button hide = new Button("X");
        hide.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                itemDetailsPopup.hide();
                try {
                    pauseGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); 
        hide.setStyle("-fx-font-family: 'Avenir Book'");

        HBox closeButtonRow = new HBox();
        closeButtonRow.getChildren().add(hide);

        itemInfo.getChildren().add(closeButtonRow);

        HBox imgRow = new HBox();
        imgRow.setPadding(new Insets(3));
        imgRow.setPrefHeight(110);

        VBox nameDescription = new VBox();

        HBox priceRow = new HBox();
        priceRow.setPadding(new Insets(3));
        // add name of item
        Label name = new Label(item.getItemDetails().name().get());
        name.setStyle("-fx-font-weight: bold; -fx-font-family: 'Avenir Next'");
        nameDescription.getChildren().add(name);
        // add item description
        Label description = new Label(item.getItemDetails().description().get());
        description.setWrapText(true);
        description.setPrefWidth(100);
        description.setStyle("-fx-font-family: 'Avenir Book'");
        nameDescription.getChildren().add(description);
        // add item image
        ImageView itemView = createImageView(item);

        // create image row
        imgRow.getChildren().add(nameDescription);
        imgRow.getChildren().add(itemView);

        itemInfo.getChildren().add(imgRow);

        // add item price
        
        Label price = new Label();
        price.textProperty().bindBidirectional(item.getItemDetails().price(), new NumberStringConverter());
        price.setPrefWidth(95);
        price.setStyle("-fx-font-family: 'Avenir Book'");
        priceRow.getChildren().add(price);

        itemInfo.getChildren().add(priceRow);

        itemDetailsPopup.getContent().add(itemInfo);

        return itemDetailsPopup;
    }

    /**
     * Creates the shop and all the items in it 
     * @param item The item from shop
     * @return VBox
     */
    public VBox loadShopTag(GenerateItem item) {
        VBox GenerateItem = new VBox();
        GenerateItem.setStyle("-fx-padding: 8;" + 
        "-fx-border-style: solid inside;" + 
        "-fx-border-width: 1;" +
        "-fx-border-insets: 3;" + 
        "-fx-border-color: grey;" +
        "-fx-background-color: white;");

        HBox imgRow = new HBox();
        imgRow.setPadding(new Insets(3));
        imgRow.setPrefHeight(110);

        VBox nameDescription = new VBox();

        HBox priceRow = new HBox();
        priceRow.setPadding(new Insets(3));
        // add name of item
        Label name = new Label(item.name().get());
        name.setStyle("-fx-font-weight: bold");
        nameDescription.getChildren().add(name);
        // add item description
        Label description = new Label(item.description().get());
        description.setWrapText(true);
        description.setPrefWidth(100);
        nameDescription.getChildren().add(description);
        // add item image
        ImageView itemView = createImageView(item);

        // create image row
        imgRow.getChildren().add(nameDescription);
        imgRow.getChildren().add(itemView);

        GenerateItem.getChildren().add(imgRow);

        // add item price
        Label price = new Label("$" + item.price().get());
        price.setPrefWidth(95);
        priceRow.getChildren().add(price);
        // add buy button
        Button buyItem = new Button("Buy");
        buyItem.setOnAction(e -> { 
            purchaseItemFromShop(item);
        });
        buyItem.disableProperty().bind(world.getCharacter().canPurchase(item));
        priceRow.getChildren().add(buyItem);

        GenerateItem.getChildren().add(priceRow);

        return GenerateItem;
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        List<Item> itemsToLoad = world.defeatedEnemyItemDrops(enemy);
        for (int i = 0; i < itemsToLoad.size(); i++) {
            onLoad(itemsToLoad.get(i));
        }
        List<Card> cardsToLoad = world.defeatedEnemyCardDrops(enemy);
        for (int i = 0; i < cardsToLoad.size(); i++) {
            onLoad(cardsToLoad.get(i));
        }
        world.getGoldAndXpDrops(enemy);
    }

    /**
     * load a card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param Card to load to GUI 
     */
    private void onLoad(Card card) {
        ImageView view = createImageView(card);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item an item to load to GUI
     */
    private void onLoad(Item item) {
        ImageView view = createImageView(item);

        switch (item.getType()) {
            case ARMOUR:
                addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedArmour); 
                break;
            case WEAPON:
                addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedWeapon); 
                break;
            case SHIELD:
                addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedShield); 
                break;
            case HELMET:
                addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedHelmet); 
                break;
            case ACCESSORY:
                addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedAccessory); 
                break;
            default:
                break;
        }

        if (item instanceof UsableItem) {
            UsableItem usableItem = (UsableItem) item;
            view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    usableItem.affect(world.getCharacter());
                    world.removeItemWhenUsed(item);
                    view.setVisible(false);
                    view.setManaged(false);
                    event.consume();
                }
            });
        } else  {
            Popup itemDetails = loadPopupInfo(item);
            view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (heroCastle.isVisible()) {
                        world.sellItem(item);
                        view.setVisible(false);
                        view.setManaged(false);
                        event.consume();
                    } else {
                        itemDetails.show(anchorPaneRoot.getScene().getWindow());
                        try {
                            pauseGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        addEntity(item, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * @param enemy and enemy to load to GUI
     */
    private void onLoad(BasicEnemy enemy) {
        // Determine which image to load in.
        ImageView view = createImageView(enemy);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load an allied soldier into the GUI
     * @param alliedsoldier to load into the GUI
     */
    private void onLoad(AlliedSoldier alliedSoldier) {
        // Determine which image to load in.
        ImageView view = createImageView(alliedSoldier);
        addEntity(alliedSoldier, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building the building to load to GUI
     */
    private void onLoad(Building building) {
        ImageView view = createImageView(building);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a gold card into the GUI
     * @param gold The gold to load to GUI
     */
    private void onLoad(Gold gold) {
        ImageView view = createImageView(gold);
        addEntity(gold, view);
        squares.getChildren().add(view);
    }

    /**
     * load an NPC into the GUI
     * @param npc the NPC to load to GUI
     */
    private void onLoad(NPC npc) {
        ImageView view = createImageView(npc);
        addEntity(npc, view);
        squares.getChildren().add(view);
    }

    /**
     * Given an entity, creates the corresponding image view
     * @param entity of the item to be rendered
     * @pre entity has a corresponding image
     */
    private ImageView createImageView(Entity entity) {
        if (world.getGameMode() instanceof ConfusingMode) {
            if (entity instanceof Anduril) {
                return new ImageView(imageMap.get(Sword.class));
            } else if (entity instanceof TreeStump) {
                return new ImageView(imageMap.get(Shield.class));
            } else if (entity instanceof TheOneRing) {
                return new ImageView(imageMap.get(Helmet.class));
            }
        }

        return new ImageView(imageMap.get(entity.getClass()));
    }

    /**
     * Given a GenerateItem object, creates the corresponding image view
     * @param generateItem of the item to be rendered
     * @pre generateItem has a corresponding image
     */
    private ImageView createImageView(GenerateItem generateItem) {
        if (world.getGameMode() instanceof ConfusingMode) {
            if (generateItem instanceof AndurilGenerateItem) {
                return new ImageView(imageMap.get(SwordGenerateItem.class));
            } else if (generateItem instanceof TreeStumpGenerateItem) {
                return new ImageView(imageMap.get(ShieldGenerateItem.class));
            } else if (generateItem instanceof TheOneRingGenerateItem) {
                return new ImageView(imageMap.get(HelmetGenerateItem.class));
            }
        }

        return new ImageView(imageMap.get(generateItem.getClass()));
    }

    /**
     * Creates a map that can use classes to look up the corresponding image
     */
    private HashMap<Class<?>, Image> createImageMap() {
        HashMap<Class<?>, Image> imageMap = new HashMap<>();

        // buildings
        imageMap.put(BarracksBuilding.class, new Image((new File("src/images/barracks.png")).toURI().toString()));
        imageMap.put(CampfireBuilding.class, new Image((new File("src/images/campfire.png")).toURI().toString()));
        imageMap.put(HerosCastleBuilding.class, new Image((new File("src/images/heros_castle.png")).toURI().toString()));
        imageMap.put(TowerBuilding.class, new Image((new File("src/images/tower.png")).toURI().toString()));
        imageMap.put(TrapBuilding.class, new Image((new File("src/images/trap.png")).toURI().toString()));
        imageMap.put(VampireCastleBuilding.class, new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString()));
        imageMap.put(VillageBuilding.class, new Image((new File("src/images/village.png")).toURI().toString()));
        imageMap.put(ZombiePitBuilding.class, new Image((new File("src/images/zombie_pit.png")).toURI().toString()));

        // cards
        imageMap.put(BarracksCard.class, new Image((new File("src/images/barracks_card.png")).toURI().toString()));
        imageMap.put(CampfireCard.class, new Image((new File("src/images/campfire_card.png")).toURI().toString()));
        imageMap.put(TowerCard.class, new Image((new File("src/images/tower_card.png")).toURI().toString()));
        imageMap.put(TrapCard.class, new Image((new File("src/images/trap_card.png")).toURI().toString()));
        imageMap.put(VampireCastleCard.class, new Image((new File("src/images/vampire_castle_card.png")).toURI().toString()));
        imageMap.put(VillageCard.class, new Image((new File("src/images/village_card.png")).toURI().toString()));
        imageMap.put(ZombiePitCard.class, new Image((new File("src/images/zombie_pit_card.png")).toURI().toString()));

        // enemies
        imageMap.put(Doggie.class, new Image((new File("src/images/doggie.png")).toURI().toString()));
        imageMap.put(ElanMuske.class, new Image((new File("src/images/ElanMuske.png")).toURI().toString()));
        imageMap.put(Slug.class, new Image((new File("src/images/slug.png")).toURI().toString()));
        imageMap.put(Vampire.class, new Image((new File("src/images/vampire.png")).toURI().toString()));
        imageMap.put(Zombie.class, new Image((new File("src/images/zombie.png")).toURI().toString()));

        // items
        imageMap.put(Anduril.class, new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString()));
        imageMap.put(Armour.class, new Image((new File("src/images/armour.png")).toURI().toString()));
        imageMap.put(DoggieCoin.class, new Image((new File("src/images/doggiecoin.png")).toURI().toString()));
        imageMap.put(HealthPotion.class, new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString()));
        imageMap.put(Helmet.class, new Image((new File("src/images/helmet.png")).toURI().toString()));
        imageMap.put(Shield.class, new Image((new File("src/images/shield.png")).toURI().toString()));
        imageMap.put(Staff.class, new Image((new File("src/images/staff.png")).toURI().toString()));
        imageMap.put(Stake.class, new Image((new File("src/images/stake.png")).toURI().toString()));
        imageMap.put(Sword.class, new Image((new File("src/images/basic_sword.png")).toURI().toString()));
        imageMap.put(TheOneRing.class, new Image((new File("src/images/the_one_ring.png")).toURI().toString()));
        imageMap.put(TreeStump.class, new Image((new File("src/images/tree_stump.png")).toURI().toString()));
        imageMap.put(ReversePathPotion.class, new Image((new File("src/images/reverse_path_potion.png")).toURI().toString()));

        // generate items
        imageMap.put(AndurilGenerateItem.class, new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString()));
        imageMap.put(ArmourGenerateItem.class, new Image((new File("src/images/armour.png")).toURI().toString()));
        imageMap.put(DoggieCoinGenerateItem.class, new Image((new File("src/images/doggiecoin.png")).toURI().toString()));
        imageMap.put(HealthPotionGenerateItem.class, new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString()));
        imageMap.put(HelmetGenerateItem.class, new Image((new File("src/images/helmet.png")).toURI().toString()));
        imageMap.put(ShieldGenerateItem.class, new Image((new File("src/images/shield.png")).toURI().toString()));
        imageMap.put(StaffGenerateItem.class, new Image((new File("src/images/staff.png")).toURI().toString()));
        imageMap.put(StakeGenerateItem.class, new Image((new File("src/images/stake.png")).toURI().toString()));
        imageMap.put(SwordGenerateItem.class, new Image((new File("src/images/basic_sword.png")).toURI().toString()));
        imageMap.put(TheOneRingGenerateItem.class, new Image((new File("src/images/the_one_ring.png")).toURI().toString()));
        imageMap.put(TreeStumpGenerateItem.class, new Image((new File("src/images/tree_stump.png")).toURI().toString())); 
        imageMap.put(ReversePathPotionGenerateItem.class, new Image((new File("src/images/reverse_path_potion.png")).toURI().toString()));

        // other
        imageMap.put(Character.class, new Image((new File("src/images/human_new.png")).toURI().toString()));
        imageMap.put(Gold.class, new Image((new File("src/images/gold_pile.png")).toURI().toString()));
        imageMap.put(PathTile.class, new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString()));
        imageMap.put(NPC.class, new Image((new File("src/images/npc.png")).toURI().toString()));
        imageMap.put(AlliedSoldier.class, new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString()));

        return imageMap;
    }


    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                boolean validDrop = false;
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType){
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if (canBuildByCoordinates(nodeX, nodeY, x, y)) {
                                    Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                    onLoad(newBuilding);
                                    validDrop = true;
                                }
                               break;
                            case ITEM:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                equipItemByCoordinates(nodeX, nodeY);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                validDrop = true;
                                break;
                            default:
                                break;
                        }
                        if (!validDrop) {
                            currentlyDraggedImage.setVisible(true);
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                       
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * Checks if based on the co0rdinates can build 
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return boolean if can build based on the coordinates
     */
    private boolean canBuildByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.canBuildByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * Equips items based on the coordinates
     * @param itemNodeX the x coordiate for the item
     * @param itemNodeY the y coordinate for the item
     */
    private void equipItemByCoordinates(int itemNodeX, int itemNodeY) {
        world.equipInventoryItemByCoordinates(itemNodeX, itemNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case CARD:
                        draggedEntity.setImage(view.getImage());
                        break;
                    case ITEM:
                        draggedEntity.setImage(view.getImage());
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        Integer cIndex = GridPane.getColumnIndex(n);
                        Integer rIndex = GridPane.getRowIndex(n);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);

                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage() && canBuildByCoordinates(nodeX, nodeY, x, y)){
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
                pauseButton.setText("Pause");
                if (heroCastle.isVisible()) {
                    resumeGameFromShop();
                }
            }
            else{
                pause();
                pauseButton.setText("Resume");
            }
            break;
        default:
            break;
        }
    }

    /**
     * If pressed then takes user to main menu
     * @param mainMenuSwitcher the button to switch to main menu
     */
    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        endGame();
        mainMenuSwitcher.switchMenu();
    }


    /**
     * Sets up visibaily of heros castle
     * Stops and starts the timer 
     * @throws IOException 
     */
    @FXML
    private void pauseGame() throws IOException {
        if (isPaused) {
            pauseButton.setText("Pause");
            if (heroCastle.isVisible()) {
                resumeGameFromShop();
            }
            startTimer();
        } else {
            pauseButton.setText("Resume");
            pause();
        }
    }

    @FXML
    private void muteGame() {
        if (isMuted) {
            muteButton.setText("Mute");
            battleMusicPlayer.setMute(false);
            backgroundMusicPlayer.setMute(false);
            goldCollectingPlayer.setMute(false);
            goldLossPlayer.setMute(false);
            healthIncreasePlayer.setMute(false);
            isMuted = false;
            

        } else {
            muteButton.setText("Sound");
            battleMusicPlayer.setMute(true);
            backgroundMusicPlayer.setMute(true);
            goldCollectingPlayer.setMute(true);
            goldLossPlayer.setMute(true);
            healthIncreasePlayer.setMute(true);
            isMuted = true;
        }
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity 
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     * @param currentMethodLabel The method label to help with debugging
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

    public ParallelTransition animateBattleFrame(Frame frame) {
        ParallelTransition ptr = new ParallelTransition();
        //Duration = 2.5 seconds
        Duration duration = Duration.millis(250);
        //Create new translate transition
        TranslateTransition transitionHero = new TranslateTransition(duration, heroBattle);
        transitionHero.setByX(50);
        transitionHero.setAutoReverse(true);
        transitionHero.setCycleCount(2);

        SequentialTransition heroSequence = new SequentialTransition(transitionHero, new PauseTransition(Duration.millis(350)));
        heroSequence.setCycleCount(1);

        TranslateTransition transitionEnemy = new TranslateTransition(duration, enemyBattle);
        transitionEnemy.setByX(-50);
        transitionEnemy.setAutoReverse(true);
        transitionEnemy.setCycleCount(2);

        SequentialTransition enemySequence = new SequentialTransition(new PauseTransition(Duration.millis(350)), transitionEnemy);
        enemySequence.setCycleCount(1);

        TranslateTransition transitionBoss = new TranslateTransition(duration, bossBattle);
        transitionBoss.setByX(-50);
        transitionBoss.setAutoReverse(true);
        transitionBoss.setCycleCount(2);

        SequentialTransition bossSequence = new SequentialTransition(new PauseTransition(Duration.millis(350)), transitionBoss);
        bossSequence.setCycleCount(1);

        ptr.getChildren().addAll(heroSequence, enemySequence, bossSequence);
        ptr.setCycleCount(1);
        ptr.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemiesLeft.setText(frame.getEnemiesLeft() + " Enemies Left");
                alliedSoldiersCount.setText("You have " + frame.getNumOfAlliedSoldiers() + " allied soldiers.");        
                characterHealth.setProgress(Math.max(frame.getCharacterHealth(), 0));
                enemyHealth.setProgress(Math.max(frame.getEnemyHealth(), 0));
                enemyBattle.setImage(imageMap.get(frame.getEnemy().getClass()));
                System.out.println(frame.getBossHealth() + " vs " + frame.getEnemyHealth());
                if (frame.getBoss() != null && frame.getBossHealth() != 0) {
                    bossHealth.setProgress(frame.getBossHealth());
                    bossBattle.setImage(imageMap.get(frame.getBoss().getClass()));
                } else {
                    bossHealth.setVisible(false);
                    bossBattle.setVisible(false);
                }
            }
        });
        return ptr;
    }

    /**
     * Setting up the screen at the end of the battle
     * @param event
     */
    @FXML
    private void onFinishBattleButton(ActionEvent event) {
        battle.setVisible(false);
        gameMap.setVisible(true);
        world.setCurrentBattle(null);
        battleMusicPlayer.pause();
        startTimer();
    }
}
