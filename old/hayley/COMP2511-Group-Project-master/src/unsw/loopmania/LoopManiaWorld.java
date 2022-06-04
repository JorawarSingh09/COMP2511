package unsw.loopmania;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Goals.Goal;
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.cards.VampireCastleCard;
import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.enemies.Slug;
import unsw.loopmania.enemies.Doggie;
import unsw.loopmania.enemies.ElanMuske;
import unsw.loopmania.gameModes.ConfusingMode;
import unsw.loopmania.items.Anduril;
import unsw.loopmania.items.TreeStump;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.items.DoggieCoin;
import unsw.loopmania.generateItems.DoggieCoinGenerateItem;
/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld implements CharacterPositionObserver {
    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    /**
     * cycles - the current game cycle
     */
    private SimpleIntegerProperty gameCycle;

    // current tick
    private int tick;

    private List<BasicEnemy> enemies;

    private List<Card> cardEntities;

    private List<SpawnEnemyStrategy> spawnEnemyStrategies;

    private List<Building> buildingEntities;

    private List<BattleBehaviourContext> defeatedBosses;

    private List<NPC> npcEntities;

    private Set<AlliedSoldier> alliedSoldiers = new HashSet<AlliedSoldier>();


    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private HerosCastleMenu shopMenu;

    // Current Battle object
    Battle currentBattle;

    /**
     * This gets read in from JSON 
     * Gets set in the loader 
     */
    private Goal gameGoal;

    private GameMode gameMode;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        defeatedBosses = new ArrayList<>();
        cardEntities = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        npcEntities = new ArrayList<>();
        spawnEnemyStrategies = new ArrayList<>();
        shopMenu = new HerosCastleMenu();
        gameCycle = new SimpleIntegerProperty(0);
        tick = 0;
    }

    /**
     * Excellent hero's castle method
     * @return The shop menu
     */
    public HerosCastleMenu getHerosCastleMenu() {
        return shopMenu;
    }

    /**
     * Width of board getter
     * @return int 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Height of board getter
     * @return int 
     */
    public int getHeight() {
        return height;
    }

    /**
     * Goal of the game getter
     * @return Goal of the game
     */
    public Goal getGameGoal() {
        return gameGoal;
    }

    /**
     * Goal of the game setter
     * @param gameGoal
     */
    public void setGameGoal(Goal gameGoal) {
        this.gameGoal = gameGoal;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
        character.attach(this);
    }

    /**
     * Allows the controller to access stats from the character
     * @return character in the world
     * @pre the character has been set in the world
     */
    public Character getCharacter() {
        return character;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        nonSpecifiedEntities.add(entity);
    }

    /**
     * Adds an enemy to the list of enemies
     * @param enemy the enemy to add
     */
    public void addEnemy(BasicEnemy enemy) {
        enemies.add(enemy);
    }

    public BasicEnemy spawnBossEnemy() {
        Pair<Integer, Integer> pos = getRandomSpawnPosition();
        int indexInPath = orderedPath.indexOf(pos);

        if (gameCycle.get() == 20) {
            for (BasicEnemy enemy : enemies) {
                if (enemy instanceof Doggie) return null;
            }
            for (BattleBehaviourContext boss : defeatedBosses) {
                if (boss instanceof Doggie) return null;
            }
            Doggie doggieBoss = new Doggie(new PathPosition(indexInPath, orderedPath));
            enemies.add(doggieBoss);
            return doggieBoss;
        } else if (gameCycle.get() == 40 && character.getXpProperty().get() >= 10000) {
            for (BasicEnemy enemy : enemies) {
                if (enemy instanceof ElanMuske) return null;
            }
            for (BattleBehaviourContext boss : defeatedBosses) {
                if (boss instanceof ElanMuske) return null;
            }
            ElanMuske elanBoss = new ElanMuske(new PathPosition(indexInPath, orderedPath));
            enemies.add(elanBoss);
            for (Item item : character.getInventory()) {
                if (item instanceof DoggieCoin) {
                    DoggieCoinGenerateItem doggieCoinItem = (DoggieCoinGenerateItem) item.getItemDetails();
                    doggieCoinItem.setUpperValue(10);
                }
            }
            return elanBoss;
        }
        return null;
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies() {
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        // spawn from buildings
        if (character.isAtHerosCastle()) {
            for (SpawnEnemyStrategy spawnEnemyStrategy : spawnEnemyStrategies) {
                BasicEnemy newEnemy = spawnEnemyStrategy.possiblySpawnEnemy(orderedPath, gameCycle.get());
                if (newEnemy != null) {
                    enemies.add(newEnemy);
                    spawningEnemies.add(newEnemy);
                }
            }
        }

        return spawningEnemies;
    }

    /**
     * potentially spawns gold at a random position
     * @return list of new gold pieces to be displayed on screen
     */
    public List<Gold> possiblySpawnGold() {
        List<Gold> spawningGold = new ArrayList<>();

        Random random = new Random();
        if (random.nextInt(100) < 10) {
            Pair<Integer, Integer> spawnPosition = orderedPath.get(random.nextInt(orderedPath.size()));
            Gold gold = new Gold(new SimpleIntegerProperty(spawnPosition.getValue0()), new SimpleIntegerProperty(spawnPosition.getValue1()));

            spawningGold.add(gold);
            addEntity(gold);
            character.attach(gold);
        }

        return spawningGold;
    }

    /**
     * Returns an NPC if there is one currently being encountered
     * @return NPC being encountered
     */
    public NPC getNPCEncounter() {
        for (NPC n : npcEntities) {
            if (n.getEncountered()) {
                return n;
            }
        }
        return null;
    }

    /**
     * potentially spawns a friendly npc at a random position
     * @return list of newly spawned NPCs
     */
    public List<NPC> possiblySpawnNPC() {
        List<NPC> newNpcs = new ArrayList<>();
        Random random = new Random();
        // 1% chance of spawning
        if (random.nextInt(1000) < 10) {
            List<Pair<Integer, Integer>> validTiles = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // can't have path tiles
                    if (orderedPath.contains(Pair.with(x, y))) {
                        continue;
                    }
                    // must be adjacent to a path tile
                    List<Pair<Integer, Integer>> adjacentSquares = List.of(
                        Pair.with(x-1, y),
                        Pair.with(x+1, y),
                        Pair.with(x, y-1),
                        Pair.with(x, y+1)
                    );
                    for (Pair<Integer, Integer> adjSquare : adjacentSquares) {
                        if (orderedPath.contains(adjSquare)) {
                            validTiles.add(Pair.with(x, y));
                            break;
                        }
                    }
                }
            }
            // remove any tiles where an existing NPC is there
            for (NPC n: npcEntities) {
                validTiles.remove(Pair.with(n.getX(), n.getY()));
            }

            if (validTiles.size() < 1) {
                return null;
            } 
            Pair<Integer, Integer> pos = validTiles.get(random.nextInt(validTiles.size()));

            NPC npc = new NPC(new SimpleIntegerProperty(pos.getValue0()), new SimpleIntegerProperty(pos.getValue1()));
            npcEntities.add(npc);
            newNpcs.add(npc);
            character.attach(npc);
        }
        return newNpcs;
    }

    /**
     * Character gambles with an NPC. Gains an item if successful
     * @param character
     * @param npc
     */
    public Item gambleWithNPC(Character character, NPC npc) {
        GenerateItem gItem = npc.gamble(character);
        if (gItem != null) {
            Pair<Integer, Integer> pos = getFirstAvailableSlotForItem();
            Item item = gItem.createItem(new SimpleIntegerProperty(pos.getValue0()), new SimpleIntegerProperty(pos.getValue1()));
            character.addItemToInventory(item);
            return item;
        }
        return null;
    }

    public Item purchaseItemFromHerosCastle(GenerateItem item) {
        Pair<Integer, Integer> coords = getFirstAvailableSlotForItem();
        Item purchasedItem = shopMenu.purchaseItem(character, item, new SimpleIntegerProperty(coords.getValue0()), new SimpleIntegerProperty(coords.getValue1()), gameMode);
        if (!purchasedItem.equals(null)) {
            return purchasedItem;
        }

        return null;
    }

    /**
     * Sells an item from the shop menu and adds to character
     * @param item an item to sell from the menu
     */
    public void sellItem(Item item) {
        shopMenu.sellItem(character, item);
    }

    /**
     * Removes the item from the inventory once it has been used
     * @param item to remove when used
     * @pre item is in inventory
     */
    public void removeItemWhenUsed(Item item) {
        item.destroy();
        character.removeItemFromInventory(item);
    }

    /**
     * Removes enemies that have died for other reasons
     * @return list of enemies that have died
     */
    public List<BasicEnemy> otherDefeatedEnemies() {
        List<BasicEnemy> killedEnemies = new ArrayList<BasicEnemy>();
        for (BasicEnemy enemy : enemies) {
            if (!enemy.isAlive()) {
                enemy.destroy();
                killedEnemies.add(enemy);
            }
        }

        for (BasicEnemy enemy : killedEnemies) {
            enemies.remove(enemy);
        }
        return killedEnemies;
    }

    /**
     * Add a new building
     * @param building to add to the world
     */
    public void addBuilding(Building building) {
        buildingEntities.add(building);
    }

    /**
     * Add a new card
     * @param card to add to the world
     */
    public void addCard(Card card) {
        cardEntities.add(card);
    }

    /**
     * Goes through buildings and NPCs, and removes those that shouldn't exist
     */
    public void removeDestroyedEntities() {
        buildingEntities.removeIf(b -> !b.shouldExist().get());
        npcEntities.removeIf(n -> !n.shouldExist().get());
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(BasicEnemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    // GET ITEM DROPS FROM ENEMY
    /**
     * Goes through and gets all of the items which a defeated enemy will drop
     * @param enemy which hsa been defeated
     * @return List<Item> all the items that the enemy drops when it has been defeated drops
     */
    public List<Item> defeatedEnemyItemDrops(BasicEnemy enemy) {
        List<GenerateItem> itemDrops = enemy.getItemDrops();
        List<Item> itemInstances = new ArrayList<Item>();

        for (int i = 0; i < itemDrops.size(); i++) {
            Pair<Integer, Integer> coords = getFirstAvailableSlotForItem();
            if (coords == null) {
                return null;
            }
            int x = coords.getValue0() + i;
            int y = coords.getValue1();
            if (x > 3) {
                x = 0;
                y += 1;
            }
            
            if (!(x > 3 && y == 3)) {
                Item newDrop = itemDrops.get(i).createItem(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                itemInstances.add(newDrop);
            }
        }
        for (Item item : itemInstances) {
            character.addItemToInventory(item);
        }
        return itemInstances;
    }

    /**
     * Updates gold and xp once the enemy has been defeated
     * @param enemy That drops gold and xp once defeated
     */
    public void getGoldAndXpDrops(BasicEnemy enemy) {
        enemy.getXPAndGold(character);
    }

    // GET CARD DROPS FROM ENEMY
    /**
     * Goes through and gets all of the cards which a defeated enemy will drop
     * @param enemy which hsa been defeated
     * @return List<Card> all the cards that the enemy drops when it  has been defeated drops
     */
    public List<Card> defeatedEnemyCardDrops(BasicEnemy enemy) {
        List<GenerateCard> cardDrops = enemy.getCardDrops();
        List<Card> cardInstances = new ArrayList<Card>();

        for (int i = 0; i < cardDrops.size(); i++) {
            // if adding more cards than have, remove the first card...
            if (cardEntities.size() >= getWidth()){
                character.addGold(10);
                character.addXp(10);
                removeCard(0);
            }
            SimpleIntegerProperty x = new SimpleIntegerProperty(cardEntities.size());
            SimpleIntegerProperty y = new SimpleIntegerProperty(0);
            Card newCard = cardDrops.get(i).createCard(x, y);
            cardEntities.add(newCard);
            cardInstances.add(newCard);
        }

        return cardInstances;
    }

     /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard () {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            character.addGold(5);
            character.addXp(5);
            removeCard(0);
        }
        SimpleIntegerProperty posX = new SimpleIntegerProperty(cardEntities.size());
        SimpleIntegerProperty posY = new SimpleIntegerProperty(0);
        // pick a random card
        List<Card> potentialCards = new ArrayList<>();
        potentialCards.add(new VampireCastleCard(posX, posY));
        potentialCards.add(new ZombiePitCard(posX, posY));

        Random random = new Random();
        Card newCard = potentialCards.get(random.nextInt(potentialCards.size()));

        cardEntities.add(newCard);
        return newCard;
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        if (characterAtHerosCastle()) { return enemies; }
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        BasicEnemy boss = null;
        for (BasicEnemy e: enemies){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Helper.withinRadius(character, e, e.getBattleRadius())){
                // Loop through enemies again, to see who is in the influence radius of the enemy, and add them to the battle.
                List<BasicEnemy> enemiesEncountered = new ArrayList<BasicEnemy>();
                if (e instanceof BattleBehaviourContext) {
                    boss = e;
                } else {
                    enemiesEncountered.add(e);
                }
                for (BasicEnemy support : enemies) {
                    if (Helper.withinRadius(e, support, support.getSupportRadius()) && !support.equals(e)) {
                        if (support instanceof BattleBehaviourContext) {
                            boss = support;
                        } else {
                            enemiesEncountered.add(support);
                        }
                    }
                }

                // get all the buildings that can affect a battle
                List<CharacterEffect> battleBuildings = new ArrayList<>();
                for (Building b : buildingEntities) {
                    if (b instanceof CharacterEffect) {
                        battleBuildings.add((CharacterEffect) b);
                    }
                }

                setCurrentBattle(new Battle(character, enemiesEncountered, battleBuildings, boss));
                if (character.isAlive()) {
                    defeatedEnemies.add(e);
                    // if defeated boss add to list
                    if (e instanceof BattleBehaviourContext) defeatedBosses.add((BattleBehaviourContext) e);
                } else {
                    // Finish Game
                }
                break;
            }
        }
        for (BasicEnemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    /**
     * Sets current battle
     * @param battle the current battle happening
     */
    public void setCurrentBattle(Battle battle) {
        this.currentBattle = battle;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Item item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        character.removeItemFromInventory(item);
    }

    /**
     * Adds in items by cordinates to the inventory
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void equipInventoryItemByCoordinates(int x, int y){
        Item item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        if (item instanceof EquippableItem) {
            character.equipItem((EquippableItem) item);
            if (gameMode instanceof ConfusingMode) {
                if (item instanceof RareItem) {
                    List<EquippableItem> extraRareItemPossibility = new ArrayList<EquippableItem>();
                    
                    if (!(item instanceof Anduril)) {
                        EquippableItem anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                        extraRareItemPossibility.add(anduril);
                    }

                    if (!(item instanceof TheOneRing)) {
                        EquippableItem theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                        extraRareItemPossibility.add(theOneRing);
                    }

                    if (!(item instanceof TreeStump)) {
                        EquippableItem treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                        extraRareItemPossibility.add(treeStump);
                    }

                    Random rand = new Random(System.currentTimeMillis());
                    int rareNum = rand.nextInt(2);
                    character.equipItem(extraRareItemPossibility.get(rareNum));
                }
            }
        }
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        // move characters
        character.moveInDirection();
        character.updateObservers();

        // move enemies
        for (BasicEnemy e : enemies) {
            e.move(tick);
            e.updateObservers();
        }
        tick++;
    }


    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Item getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Item e: character.getInventory()) {
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * Gets the current battle object
     * @return currentBattle
     */
    public Battle getCurrentBattle() {
      return currentBattle;
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return  Pair<Integer, Integer>  x,y coordinate pair
     */
    public Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * Observe character movements
     * @param Character the character to observe
     */
    public void encounter(Character character) {
        if (character.isAtHerosCastle()) {
            iterateGamecycle();
        }
    }

    /**
     * Checks if the character is at heros castle
     * @return boolean
     */
    public boolean characterAtHerosCastle() {
        return character.isAtHerosCastle();
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition() {
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)){
            return getRandomSpawnPosition();
        }
        return null;
    }

    private Pair<Integer, Integer> getRandomSpawnPosition() {
        Random rand = new Random(System.currentTimeMillis());
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
        int endNotAllowed = (indexPosition + 3)%orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

        return spawnPosition;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     * @pre the position of the building to be added is a valid position
     * @return A new building
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) { // Check placeable
                card = c;
                break;
            }
        }
        
        // Check for character position, enemy positon, spawn
        // now spawn building
        Building newBuilding = card.generateBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
        buildingEntities.add(newBuilding);
        if (newBuilding instanceof SpawnEnemyStrategy) {
            spawnEnemyStrategies.add((SpawnEnemyStrategy) newBuilding);
        }

        if (newBuilding instanceof CharacterPositionObserver) {
            character.attach((CharacterPositionObserver) newBuilding);
        }

        if (newBuilding instanceof EnemyPositionObserver) {
            for (BasicEnemy enemy : enemies) {
                enemy.attach((EnemyPositionObserver) newBuilding);
            }
        }

        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    /**
     * Checks if a card is placemable and thus can build
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     * @return boolean if the build can happen
     */
    public boolean canBuildByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // check that there is not an existing building at the position
        for (Building b : buildingEntities) {
            if ((b.getX() == buildingNodeX) && (b.getY() == buildingNodeY)) {
                return false;
            }
        }

        Card card = null;
        for (Card c : cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) { // Check placeable
                card = c;
                break;
            }
        } 

        if (card != null) {
            return card.isValidPosition(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), orderedPath);
        }
        return false;
    }

    /**
     * iterates cycle
     */
    public void iterateGamecycle() {
        this.gameCycle.set(this.gameCycle.get() + 1);
    }

    /**
     * Gets game cycle as an integer
     * @return cycle
     */
    public int getGameCycle() {
        return this.gameCycle.get();
    }

    /**
     * Gets game cycle as a SimpleIntegerProperty
     * @return cycle
     */
    public SimpleIntegerProperty getGameCycleProperty() {
        return this.gameCycle;
    }

    /**
     * Gets Game mode
     * @return cycle
     */
    public GameMode getGameMode() {
        return this.gameMode;
    }

    /**
     * Resets game to initial state
     * @return void
     */
    public void resetGame() {
        nonSpecifiedEntities = new ArrayList<>();
        character.reset();
        for (BasicEnemy enemy : enemies) {
            enemy.destroy();
        }
        enemies.clear();
        for (Card card : cardEntities) {
            card.destroy();
        }
        cardEntities.clear();
        for (Building building : buildingEntities) {
            building.destroy();
        }
        buildingEntities.clear();
        spawnEnemyStrategies.clear();
        gameCycle.set(0);
    }

    /**
     * Updates Allied Soldiers
     * @returns a new allied soldier
     */
    public AlliedSoldier getAlliedSoldiers() {
        // Gets new allied soldiers
        Set<AlliedSoldier> currentAlliedSoldiers = new HashSet<AlliedSoldier>();
        currentAlliedSoldiers.addAll(character.getAlliedSoldiers());

        // Remove dead allied soldiers and add new ones
        alliedSoldiers.retainAll(currentAlliedSoldiers);
        currentAlliedSoldiers.removeAll(alliedSoldiers);
        if (alliedSoldiers.size() == 0 && currentAlliedSoldiers.size() > 0) {
            Iterator<AlliedSoldier> alliedSoldierIterator = currentAlliedSoldiers.iterator();
            return alliedSoldierIterator.next();
        }
        return null;
    }
}