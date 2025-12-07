package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Entities.*;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Clock;
import cs230.group29se.jewelthief.Items.Door;
import cs230.group29se.jewelthief.Items.Gate;
import cs230.group29se.jewelthief.Items.Item;
import cs230.group29se.jewelthief.Items.Lever;
import cs230.group29se.jewelthief.Items.Loot;
import cs230.group29se.jewelthief.Items.LootEnum;
import cs230.group29se.jewelthief.Items.Shield;
import cs230.group29se.jewelthief.Persistence.Profile.SaveData;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import cs230.group29se.jewelthief.Persistence.Storage.LevelLoader;
import cs230.group29se.jewelthief.Persistence.Storage.LevelDef;
import cs230.group29se.jewelthief.Persistence.Storage.EntityDef;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a level in the game, containing tiles, items, enemies, and player.
 * Manages time and score for the level.
 *
 * @author Gustas Rove, Ben Poole
 * @version 1.0
 */
public class Level {
    private int levelNumber = 1;

    private GameController gameController;

    private List<Item> items = new ArrayList<>();
    private List<Gate> gates = new ArrayList<>();
    private ArrayList<NonPlayableCharacter> enemies = new ArrayList<>();
    private Tile[][] grid;

    private static final int HIT_COOLDOWN_TICKS = 10;
    private int hitCooldown = 0;

    // in Level
    private Player player;


    private int maxTime = 60; // Seconds
    private long timeRemaining; // Milliseconds
    private long lastUpdateTime; // Nanoseconds

    private int score;

    private boolean failedLevel = false;
    private String failReason = "";
    private boolean finishedLevel = false;

    /**
     * Constructs a Level with the specified level name and game controller.
     *
     * @param levelName      the name of the level file
     * @param gameController the game controller for updating UI elements
     */
    private final SaveData saveData;
    public Level(String levelName, GameController gameController, SaveData saveData) {
        this.gameController = gameController;
        this.saveData = saveData;
        try {
            //readLevelFile(levelName);
            if(saveData != null) {
                loadSavedLevel();
            }else{
                loadFreshLevel(levelName);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Level file not found: " + levelName);
            e.printStackTrace();
        }

        // initialises timeRemaining from SaveData if available, else from maxTime
        if (saveData != null && saveData.getTimeRemainingMs() > 0) {
            timeRemaining = saveData.getTimeRemainingMs();
        } else {
            timeRemaining = maxTime * 1000L;
        }

        lastUpdateTime = System.nanoTime();
        gameController.scoreLabel.setText("Score: " + score);
    }

    private void loadSavedLevel() {
        loadGrid();
        loadSaveState();
    }


    /**
     * Updates the level state, including the timer.
     */
    public void update() {
        if (!getPlayer().isAlive()){
            failLevel("Player has been DIED!");
        }
        updateTime();

        //Move enemies
        for (NonPlayableCharacter npc : enemies) {
            if (npc.isAlive()) {
                npc.move();
                npc.updateHitCooldown();
                checkAndCollectItem(npc);
            }
        }

        checkCollisions();
    }

    /**
     * Checks if a tile has an Item and calls an NPC's collectItem method if it does
     * @param npc
     */
    private void checkAndCollectItem(NonPlayableCharacter npc) {
        int[] position = npc.getPosition();
        Tile tile = getTile(position[0], position[1]);

        if (tile != null) {
            Object occupying = tile.getOccupying();
            if (occupying instanceof Item) {
                Item item = (Item) occupying;
                npc.collectItem(item);
            }
        }
    }

    /**
     * Checks if any characters are occupying the same tile and triggers interactions.
     */
    private void checkCollisions() {
        for (NonPlayableCharacter npc : enemies) {
            if (npc.isAlive()) {
                if (npc.getPosition()[0] == player.getPosition()[0] &&
                        npc.getPosition()[1] == player.getPosition()[1]) {
                    npc.onCollisionWith(player);
                }
            }
        }
    }

    /**
     * Updates the remaining time for the level and checks if the time has run out.
     * Updates the timer label in the game controller.
     */
    private void updateTime() {
        long now = System.nanoTime();
        long elapsedTime = (now - lastUpdateTime) / 1_000_000;
        lastUpdateTime = now;

        timeRemaining -= elapsedTime;

        if (timeRemaining <= 0) {
            timeRemaining = 0;
            failLevel("Time's up!");
        }
        gameController.timerLabel.setText("Time: " + getTimeRemainingTimeInSeconds() + "s");

    }

    /**
     * Marks the level as failed and sets the reason for failure.
     *
     * @param failReason the reason for the level failure
     */
    public void failLevel(String failReason) {
        failedLevel = true;
        setFailReason(failReason);
    }

    /**
     * Checks if the level has failed.
     *
     * @return true if the level has failed, false otherwise
     */
    public boolean isLevelFailed() {
        return failedLevel;
    }

    /**
     * Returns the width of the grid.
     *
     * @return the width of the grid.
     */
    public int getWidth() {
        return grid.length;
    }

    /**
     * Returns the height of the grid.
     *
     * @return the height of the grid.
     */
    public int getHeight() {
        return grid[0].length;
    }

    /**
     * This method finds the tile at the specified (x, y) coordinates.
     *
     * @param x the x-coordinate to retrieve.
     * @param y the y-coordinate to retrieve.
     * @return Retrieves the tile at the specified location, or null if the coordinates are out of bounds.
     */
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return grid[x][y];
        }
        return null;
    }

    public long getTimeRemainingMs() {
        return timeRemaining;
    }

    /**
     * Draws the level, including tiles, items, gates, and the player.
     *
     * @param gc the graphics context used for drawing
     */
    public void draw(GraphicsContext gc) {
        // needs to go first so it doesn't draw over items or gates
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                tile.draw(gc);
            }
        }
        for (Item item : items) {
            item.draw(gc);
        }
        for (Gate gate : gates) {
            gate.draw(gc);
        }
        for (NonPlayableCharacter npc : enemies) {
            if (npc.isAlive()) {
                npc.draw(gc);
            }
        }
        player.draw(gc);
    }

    /**
     * Gets the level number.
     *
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Adds time to the remaining time, up to the maximum time.
     * Unit of time is seconds.
     *
     * @param timeToAdd
     */
    public void addTime(int timeToAdd) {
        timeRemaining += timeToAdd;
        if (timeRemaining > maxTime*1000) {
            timeRemaining = maxTime*1000;
        }
    }

    /**
     * Removes time from the remaining time, down to a minimum of 0.
     * Unit of time is seconds.
     *
     * @param timeToRemove
     */
    public void removeTime(int timeToRemove) {
        timeRemaining -= timeToRemove;
        if (timeRemaining < 0) {
            timeRemaining = 0;
        }
    }

    /**
     * Gets the time remaining in seconds.
     *
     * @return time remaining in seconds
     */
    public long getTimeRemainingTimeInSeconds() {
        return timeRemaining / 1000;
    }

    /**
     * Adds score to the current score.
     *
     * @param scoreToAdd the score to add
     */
    public void addScore(int scoreToAdd) {
        score += scoreToAdd;
        gameController.scoreLabel.setText("Score: " + score);
    }

    /**
     * Sets the current score to a specific value.
     *
     * @param score the new score value
     */
    private void setScore(int score) {
        this.score = score;
    }

    /**
     * Removes score from the current score, down to a minimum of 0.
     *
     * @param scoreToRemove the score to remove
     */
    public void removeScore(int scoreToRemove) {
        score -= scoreToRemove;
        if (score < 0) {
            score = 0;
        }
        gameController.scoreLabel.setText("Score: " + score);
    }

    /**
     * Gets the current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /**
     * Gets the reason for level failure.
     *
     * @return the reason for failure
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * Reads a level file and populates the level with tiles, items, and enemies.
     *
     * @param filename the name of the level file
     * @throws FileNotFoundException if the level file is not found
     */

    /**
     * reads through the level file and populates the level appropriately
     * this covers map size, time limit, individual tiles, the player start location, NPCs and Items
     *
     * @param filename the name of the level to be loaded
     * @throws FileNotFoundException if the file name entered is not found
     * @author Ben Poole, Iyaad
     */
    public void loadFreshLevel(String filename) throws FileNotFoundException {
        String levelId = extractLevelId(filename);
        java.nio.file.Path levelPath = java.nio.file.Path.of("levels", filename);
        LevelDef def = new LevelLoader().loadLevel(levelId, levelPath);
        maxTime = def.timeLimitSec;
        int x = def.width;
        int y = def.height;
        grid = new Tile[x][y];

        // Tiles: row 0 = top, row y-1 = bottom
        for (int row = 0; row < y; row++) {
            String rowString = def.tiles.get(row);      // e.g. "YYYY RBYG YRGB BRGY"
            String[] tileTokens = rowString.split("\\s+");
            for (int col = 0; col < x; col++) {
                String sequence = tileTokens[col];      // e.g. "YYYY"
                Colour[] colours = new Colour[4];
                for (int z = 0; z < 4; z++) {
                    char colChar = sequence.charAt(z);
                    colours[z] = colourSetter(String.valueOf(colChar));
                }
                int xPos = (x - 1) - col;    // 0..x-1
                int yPos = (y - 1) - row;    // 0..y-1
                grid[xPos][yPos] = new Tile(xPos, yPos, colours);
            }
        }

        int xPos = def.playerStart.x;  // tile index
        int yPos = def.playerStart.y;  // tile index
        // clamp to grid bounds just in case
        xPos = Math.max(0, Math.min(xPos, getWidth() - 1));
        yPos = Math.max(0, Math.min(yPos, getHeight() - 1));
        // use tile indices directly
        double px = xPos;
        double py = yPos;
        player = new Player(grid[(int) px][(int) py], this);

        // Items and gates + ENEMIES
        for (EntityDef e : def.entities) {
            int rawX = e.x;
            int rawY = e.y;
            int entityXPos = rawX - 1;
            int entityYPos = rawY - 1;  // flip to reflect level design
            String npcId = e.type;

            switch (e.type) {
                case "FLYING" -> {
                    // e.arg1 = direction string ("UP","DOWN","LEFT","RIGHT")
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    FlyingAssasin tempEnemy = new FlyingAssasin(grid[entityXPos][entityYPos], direction, this, npcId);
                    enemies.add(tempEnemy);
                }
                case "SMART" -> {
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    // SmartEnemy enemy = new SmartEnemy(xPos, yPos, direction, this, npcId);
                    // grid[xPos][yPos].setOccupying(enemy);
                }
                case "FOLLOWER" -> {
                    // e.arg1 = direction, e.arg2 = follower colour ("R","G","B","Y")
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = e.arg2;
                    Colour colour = colourSetter(followerColour);
                    FloorThief tempEnemy = new FloorThief(colour, grid[entityXPos][entityYPos], direction, this, npcId);
                    enemies.add(tempEnemy);
                }
                case "CAMPER" -> {
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    Camper tempEnemy = new Camper(grid[entityXPos][entityYPos], direction, this, npcId);
                    enemies.add(tempEnemy);
                }
                case "LOOT" -> {
                    String value = e.arg1;
                    Loot tempLoot;
                    switch (value) {
                        case "CENT"    -> tempLoot = new Loot(LootEnum.CENT,    entityXPos, entityYPos);
                        case "DOLLAR"  -> tempLoot = new Loot(LootEnum.DOLLAR,  entityXPos, entityYPos);
                        case "RUBY"    -> tempLoot = new Loot(LootEnum.RUBY,    entityXPos, entityYPos);
                        case "DIAMOND" -> tempLoot = new Loot(LootEnum.DIAMOND, entityXPos, entityYPos);
                        default        -> tempLoot = null;
                    }
                    if (tempLoot != null) {
                        items.add(tempLoot);
                        grid[entityXPos][entityYPos].setOccupying(tempLoot);
                    }
                }
                case "BOMB" -> {
                    Bomb tempBomb = new Bomb(entityXPos, entityYPos);
                    items.add(tempBomb);
                    grid[entityXPos][entityYPos].setOccupying(tempBomb);
                }
                case "LEVER" -> {
                    Colour colour = colourSetter(e.arg1);
                    Lever tempLever = new Lever(colour, entityXPos, entityYPos);
                    items.add(tempLever);
                    grid[entityXPos][entityYPos].setOccupying(tempLever);
                }
                case "GATE" -> {
                    Colour colour = colourSetter(e.arg1);
                    Gate tempGate = new Gate(colour, entityXPos, entityYPos);
                    gates.add(tempGate);
                    grid[entityXPos][entityYPos].setOccupying(tempGate);
                    for (Item item : items) {
                        if (item instanceof Lever lever) {
                            if (tempGate.getColour() == lever.getColour()) {
                                lever.addGate(tempGate);
                            }
                        }
                    }
                }
                case "DOOR" -> {
                    Door tempDoor = new Door(entityXPos, entityYPos);
                    items.add(tempDoor);
                    grid[entityXPos][entityYPos].setOccupying(tempDoor);
                }
                case "CLOCK" -> {
                    Clock tempClock = new Clock(entityXPos, entityYPos);
                    items.add(tempClock);
                    grid[entityXPos][entityYPos].setOccupying(tempClock);
                }
                case "SHIELD" -> {
                    Shield shield = new Shield(entityXPos, entityYPos);
                    items.add(shield);
                    grid[entityXPos][entityYPos].setOccupying(shield);
                }
                default -> {
                    System.out.println("Unknown entity type in level file: " + e.type);
                }
            }
        }
    }

    /**
     * Loads the grid from the level file.
     */
    public void loadGrid() {
        String filename = "level" + levelNumber + ".txt";
        String levelId = extractLevelId(filename);
        java.nio.file.Path levelPath = java.nio.file.Path.of("levels", filename);
        try {
            LevelDef def = new LevelLoader().loadLevel(levelId, levelPath);
            int x = def.width;
            int y = def.height;
            grid = new Tile[x][y];

            // Tiles: row 0 = top, row y-1 = bottom
            for (int row = 0; row < y; row++) {
                String rowString = def.tiles.get(row);
                String[] tileTokens = rowString.split("\\s+");
                for (int col = 0; col < x; col++) {
                    String sequence = tileTokens[col];
                    Colour[] colours = new Colour[4];
                    for (int z = 0; z < 4; z++) {
                        char colChar = sequence.charAt(z);
                        colours[z] = colourSetter(String.valueOf(colChar));
                    }
                    int xPos = (x - 1) - col;
                    int yPos = (y - 1) - row;
                    grid[xPos][yPos] = new Tile(xPos, yPos, colours);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load grid for " + filename);
            e.printStackTrace();
        }
    }
    /**
     * Loads the saved state into the level, including player position, NPCs, time remaining, score, and items.
     */
    public void loadSaveState() {

        // Initialize player position from save data ------------------------
        Object[] ps = saveData.getPlayerState();
        Object pxObj = ps[0];
        Object pyObj = ps[1];
        int px = 0;
        int py = 0;
        if (pxObj instanceof Number && pyObj instanceof Number) {

            // Cast to int and clamp to grid bounds
            px = ((Number) pxObj).intValue();
            py = ((Number) pyObj).intValue();
            px = Math.max(0, Math.min(px, getWidth() - 1));
            py = Math.max(0, Math.min(py, getHeight() - 1));
        }
        Tile playerTile = getTile(px, py);
        player = new Player(playerTile, this); // temporary init; will be overridden below

        // Initialize NPCs from save data -----------------------------------
        for(Map.Entry<String,Object> npcState : saveData.getNpcStates().entrySet()){
            Map<String,Object> state = (Map<String,Object>)npcState.getValue();
            String npcType = npcState.getKey();
            int rawX = (int) state.get("x");
            int rawY = (int) state.get("y");
            int xPos = rawX;
            int yPos = rawY;

            switch (npcType) {
                case "FLYING" -> {
                    String npcDirection = (String) state.get("dir");
                    Direction direction = directionSetter(npcDirection);
                    FlyingAssasin tempEnemy = new FlyingAssasin(grid[xPos][yPos], direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                case "FOLLOWER" -> {
                    String npcDirection = (String) state.get("dir");
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = (String) state.get("colour");
                    Colour colour = colourSetter(followerColour);
                    FloorThief tempEnemy = new FloorThief(colour, grid[xPos][yPos], direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                case "SMART" -> {
                    String npcDirection = (String) state.get("dir");
                    Direction direction = directionSetter(npcDirection);
                    // SmartEnemy enemy = new SmartEnemy(xPos, yPos, direction, this, npcType);
                    // grid[xPos][yPos].setOccupying(enemy);
                }
                case "CAMPER" -> {
                    String npcDirection = (String) state.get("dir");
                    Direction direction = directionSetter(npcDirection);
                    Camper tempEnemy = new Camper(grid[xPos][yPos], direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                default -> {
                    System.out.println("Unknown NPC type in save data: " + npcType);
                }
            }
        }

        // Set time remaining from save data -------------------------------
        setTimeRemaining(saveData.getTimeRemainingMs());

        // Set score from save data ----------------------------------------
        setScore(saveData.getScore());

        // Set the items from save data --------------------------------------
        for (Map.Entry<String,Object> itemState : saveData.getItems().entrySet()) {
            String itemType = itemState.getKey();
            Map<String,Object> state = (Map<String,Object>)itemState.getValue();
            int xPos = (int) state.get("x");
            int yPos = (int) state.get("y");

            switch (itemType) {
                case "LOOT" -> {
                    String value = (String) state.get("param");
                    Loot tempLoot;
                    switch (value) {
                        case "CENT"    -> tempLoot = new Loot(LootEnum.CENT,    xPos, yPos);
                        case "DOLLAR"  -> tempLoot = new Loot(LootEnum.DOLLAR,  xPos, yPos);
                        case "RUBY"    -> tempLoot = new Loot(LootEnum.RUBY,    xPos, yPos);
                        case "DIAMOND" -> tempLoot = new Loot(LootEnum.DIAMOND, xPos, yPos);
                        default        -> tempLoot = null;
                    }
                    if (tempLoot != null) {
                        items.add(tempLoot);
                        grid[xPos][yPos].setOccupying(tempLoot);
                    }
                }
                case "BOMB" -> {
                    Bomb tempBomb = new Bomb(xPos, yPos);
                    items.add(tempBomb);
                    grid[xPos][yPos].setOccupying(tempBomb);
                }
                case "LEVER" -> {
                    Colour colour = colourSetter((String) state.get("param"));
                    Lever tempLever = new Lever(colour, xPos, yPos);
                    items.add(tempLever);
                    grid[xPos][yPos].setOccupying(tempLever);
                }
                case "DOOR" -> {
                    Door tempDoor = new Door(xPos, yPos);
                    items.add(tempDoor);
                    grid[xPos][yPos].setOccupying(tempDoor);
                }
                case "CLOCK" -> {
                    Clock tempClock = new Clock(xPos, yPos);
                    items.add(tempClock);
                    grid[xPos][yPos].setOccupying(tempClock);
                }
                case "SHIELD" -> {
                    Shield shield = new Shield(xPos, yPos);
                    items.add(shield);
                    grid[xPos][yPos].setOccupying(shield);
                }
                default -> {
                    System.out.println("Unknown Item type in save data: " + itemType);
                }
            }
        }

        // Set the gates from save data --------------------------------------
        for (Map.Entry<String,Object> gateState : saveData.getGates().entrySet()) {
            String gateType = gateState.getKey();
            Map<String,Object> state = (Map<String,Object>)gateState.getValue();
            int xPos = (int) state.get("x");
            int yPos = (int) state.get("y");

            switch (gateType) {
                case "GATE" -> {
                    Colour colour = colourSetter((String) state.get("param"));
                    Gate tempGate = new Gate(colour, xPos, yPos);
                    gates.add(tempGate);
                    grid[xPos][yPos].setOccupying(tempGate);
                    for (Item item : items) {
                        if (item instanceof Lever lever) {
                            if (tempGate.getColour() == lever.getColour()) {
                                lever.addGate(tempGate);
                            }
                        }
                    }
                }
                default -> {
                    System.out.println("Unknown Gate type in save data: " + gateType);
                }
            }
        }

        // Re-link gates to levers after all items are loaded ----------------

    }
    /**
     * Quick helper method to find the levelID
     *
     * @param filename the level file in .txt format
     * @return the level id
     */
    private String extractLevelId(String filename) {
        // very basic: strip "level" prefix and ".txt" suffix if present
        String name = filename;
        if (name.startsWith("level")) {
            name = name.substring("level".length());
        }
        if (name.endsWith(".txt")) {
            name = name.substring(0, name.length() - 4);
        }
        return name;
    }

    /**
     * converts strings to direction enum values
     *
     * @param direction the string to be converted
     * @return the appropriate direction value
     */
    private Direction directionSetter(String direction) {
        switch (direction) {
            case "UP" -> {
                return Direction.UP;
            }
            case "DOWN" -> {
                return Direction.DOWN;
            }
            case "LEFT" -> {
                return Direction.LEFT;
            }
            case "RIGHT" -> {
                return Direction.RIGHT;
            }
        }
        return null;
    }

    /**
     * converts strings to colour enum values
     *
     * @param colour the string to be converted
     * @return the appropriate colour value
     */
    private Colour colourSetter(String colour) {
        switch (colour) {
            case "R" -> {
                return Colour.RED;
            }
            case "G" -> {
                return Colour.GREEN;
            }
            case "B" -> {
                return Colour.BLUE;
            }
            case "Y" -> {
                return Colour.YELLOW;
            }
            case "C" -> {
                return Colour.CYAN;
            } case "M" -> {
                return Colour.MAGENTA;
            }
        }
        return null;
    }

    /**
     * Checks if the level contains no loot or levers.
     * Used for checking if the door can be opened.
     *
     * @return true if there are no loot or levers, false otherwise.
     */
    public boolean containsNoLootAndLevers() {
        for (Item item : items) {
            if (item instanceof Loot || item instanceof Lever) {
                return false;
            }
        }
        return true;
    }

    public void resetLevel() {
        //Set Eevery Value back to initial



        String levelId = "level" + levelNumber + ".txt";
        java.nio.file.Path levelPath = java.nio.file.Path.of("levels", levelId);
        try {
            loadFreshLevel(levelId);
        } catch (FileNotFoundException e) {
            System.out.println("Level file not found: " + levelId);
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of the current levels intact items.
     *
     * @return the list of items un-removed in the level.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Changes the current levels intact items.
     * Allows for items to be removed from drawing.
     *
     * @param items the new list of items.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Adds an item to the level's list of items.
     *
     * @param item the item to be added.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the level's list of items.
     *
     * @param item the item to be removed.
     */

    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Allows tiles to be accessed
     *
     * @return the grid of the level made of tiles.
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * Changes the list of intact gates in the level to be changed.
     *
     * @param gates the gates still intact.
     */
    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    /**
     * Gets the list of intact gates.
     *
     * @return the list of intact gates in the level.
     */
    public List<Gate> getGates() {
        return gates;
    }

    public void removeGate(Gate gate) {
        gates.remove(gate);
    }
    /**
     * Gets the player of the level.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the current GameController
     * @return the game controller
     */
    public GameController getGameController() {return gameController;}

    /**
     * Gets the list of all enemies
     * @return all the npcs.
     */
    public java.util.List<NonPlayableCharacter> getEnemies() {
        return enemies;
    }

    public boolean isFinishedLevel() {
        return finishedLevel;
    }

    public void finishLevel() {
        this.finishedLevel = true;
    }

    /**
     * Sets the remaining time for the level.
     *
     * @param timeRemaining the remaining time in milliseconds
     */
    private void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}