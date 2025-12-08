package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Entities.*;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Clock;
import cs230.group29se.jewelthief.Items.Destroyable;
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
    // File/path constants
    private static final String LEVEL_PREFIX = "level";
    private static final String LEVEL_SUFFIX = ".txt";
    private static final String LEVELS_FOLDER = "levels";

    // Entity type identifiers (from level/entity files)
    private static final String TYPE_FLYING = "FLYING";
    private static final String TYPE_SMART = "SMART";
    private static final String TYPE_FOLLOWER = "FOLLOWER";
    private static final String TYPE_CAMPER = "CAMPER";
    private static final String TYPE_LOOT = "LOOT";
    private static final String TYPE_BOMB = "BOMB";
    private static final String TYPE_LEVER = "LEVER";
    private static final String TYPE_GATE = "GATE";
    private static final String TYPE_DOOR = "DOOR";
    private static final String TYPE_CLOCK = "CLOCK";
    private static final String TYPE_SHIELD = "SHIELD";

    // Loot types
    private static final String LOOT_CENT = "CENT";
    private static final String LOOT_DOLLAR = "DOLLAR";
    private static final String LOOT_RUBY = "RUBY";
    private static final String LOOT_DIAMOND = "DIAMOND";

    // Save-data field keys
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_DIR = "dir";
    private static final String KEY_COLOUR = "colour";
    private static final String KEY_PARAM = "param";

    // Bomb save-data separator
    private static final String PARAM_SEPARATOR = "#";

    // Error / log messages
    private static final String ERR_UNKNOWN_ENTITY =
            "Unknown entity type in level file: ";
    private static final String ERR_UNKNOWN_ENTITY_SAVE =
            "Unknown NPC type in save data: ";
    private static final String ERR_UNKNOWN_ITEM_SAVE =
            "Unknown Item type in save data: ";
    private static final String ERR_UNKNOWN_GATE_SAVE =
            "Unknown Gate type in save data: ";
    private static final String ERR_LOAD_GRID =
            "Failed to load grid for ";
    private static final String ERR_LEVEL_NOT_FOUND =
            "Level file not found: ";

    // Fail messages
    private static final String FAIL_TIME_UP = "Time's up!";
    private static final String FAIL_PLAYER_DIED = "Player has DIED!";

    // Ids for bomb parameters in save data
    private static final int COUNTDOWN_LEFT = 0;
    private static final int COUNTDOWN_TICK_PROGRESS = 1;
    private static final int NEXT_BOOM_COUNTDOWN = 2;
    private static final int EXPLOSIONS = 3;
    private static final int ARMED = 4;
    private static final int EXPLODING = 5;

    // Time conversion constants
    private static final long MILLISECONDS = 1000;
    private static final long NANOSECONDS = 1_000_000;

    private static final int TILE_COLOUR_COUNT = 4;

    private static final int DEFAULT_MAX_TIME_SECONDS = 60;

    private static final int COORD_OFFSET = 1;

    // UI label prefixes/suffixes
    public static final String SCORE_LABEL_PREFIX = "Score: ";
    private static final String TIMER_LABEL_PREFIX = "Time: ";
    private static final String TIMER_LABEL_SUFFIX = "s";

    // Direction strings
    private static final String DIR_UP = "UP";
    private static final String DIR_DOWN = "DOWN";
    private static final String DIR_LEFT = "LEFT";
    private static final String DIR_RIGHT = "RIGHT";

    // Colour strings
    private static final String COLOUR_RED = "R";
    private static final String COLOUR_GREEN = "G";
    private static final String COLOUR_BLUE = "B";
    private static final String COLOUR_YELLOW = "Y";
    private static final String COLOUR_CYAN = "C";
    private static final String COLOUR_MAGENTA = "M";


    private final GameController gameController;
    private List<Item> items = new ArrayList<>();
    private List<Gate> gates = new ArrayList<>();
    private ArrayList<NonPlayableCharacter> enemies = new ArrayList<>();
    private Tile[][] grid;
    private Player player;
    private static int MAX_TIME = DEFAULT_MAX_TIME_SECONDS; // Seconds
    private long timeRemaining; // Milliseconds
    private long lastUpdateTime; // Nanoseconds
    private int score;

    private boolean failedLevel = false;

    // Reason for level failure
    private String failReason = "";

    private boolean finishedLevel = false;
    private final SaveData saveData;

    /**
     * Constructs a Level with the specified level name and game controller.
     *
     * @param levelName      the name of the level file
     * @param gameController the game controller for updating UI elements
     * @param saveData       the players save data
     */

    public Level(final String levelName, final GameController gameController,
                 final SaveData saveData) {
        this.gameController = gameController;
        this.saveData = saveData;
        try {
            //readLevelFile(levelName);
            if (saveData != null) {
                loadSavedLevel();
            } else {
                loadFreshLevel(levelName);
            }
        } catch (FileNotFoundException e) {
            System.out.println(ERR_LEVEL_NOT_FOUND + levelName);
            e.printStackTrace();
        }

        // initialises timeRemaining from SaveData if available,
        // else from maxTime
        if (saveData != null && saveData.getTimeRemainingMs() > 0) {
            timeRemaining = saveData.getTimeRemainingMs();
        } else {
            timeRemaining = MAX_TIME * MILLISECONDS;
        }

        lastUpdateTime = System.nanoTime();
        gameController.scoreLabel.setText(SCORE_LABEL_PREFIX + score);
    }

    /**
     * Load a level from the players save.
     */
    private void loadSavedLevel() {
        loadGrid();
        loadSaveState();
    }

    /**
     * Updates the level state, including the timer.
     */
    public void update() {
        if (!getPlayer().isAlive()) {
            failLevel(FAIL_PLAYER_DIED);
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

        ArrayList<Destroyable> destroyed = new ArrayList<>();
        //countdown bombs or continues the explosion
        for (Item item : items) {
            if (item instanceof Bomb bomb) {
                if (bomb.getExploding()) {
                    bomb.updateNextBoom();
                    ArrayList<Destroyable> temp = bomb.toDestroy();
                    while (!temp.isEmpty()) {
                        destroyed.add(temp.getLast());
                        temp.removeLast();
                    }
                } else if (bomb.getArmed()) {
                    // do countdown stuff
                    bomb.updateCountDown();
                }
            }
        }
        while (!destroyed.isEmpty()) {
            destroyed.getFirst().remove(destroyed.getFirst());
            destroyed.removeFirst();
        }

        checkCollisions();
    }

    /**
     * Checks if a tile has an Item and calls an NPC's collectItem
     * method if it does.
     *
     * @param npc the npc to try collect an item.
     */
    private void checkAndCollectItem(final NonPlayableCharacter npc) {
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
     * Checks if any characters are occupying the same tile and triggers
     * interactions.
     */
    private void checkCollisions() {
        for (NonPlayableCharacter npc : enemies) {
            if (npc.isAlive()) {
                if (npc.getPosition()[0] == player.getPosition()[0]
                        &&
                        npc.getPosition()[1] == player.getPosition()[1]) {
                    npc.onCollisionWith(player);
                }
            }
        }
    }

    /**
     * Updates the remaining time for the level and checks
     * if the time has run out.
     * Updates the timer label in the game controller.
     */
    private void updateTime() {
        long now = System.nanoTime();
        long elapsedTime = (now - lastUpdateTime) / NANOSECONDS;
        lastUpdateTime = now;

        timeRemaining -= elapsedTime;

        if (timeRemaining <= 0) {
            timeRemaining = 0;
            failLevel(FAIL_TIME_UP);
        }

        gameController.timerLabel.setText(TIMER_LABEL_PREFIX + getTimeRemainingTimeInSeconds() + TIMER_LABEL_SUFFIX);

    }

    /**
     * Marks the level as failed and sets the reason for failure.
     *
     * @param failReason the reason for the level failure
     */
    public void failLevel(final String failReason) {
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
     * @return Retrieves the tile at the specified location,
     * or null if the coordinates are out of bounds.
     */
    public Tile getTile(final int x, final int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return grid[x][y];
        }
        return null;
    }

    /**
     * Get the time remaining in the level from milliseconds.
     *
     * @return the time remaining in the level in milliseconds.
     */
    public long getTimeRemainingMs() {
        return timeRemaining;
    }

    /**
     * Draws the level, including tiles, items, gates, and the player.
     *
     * @param gc the graphics context used for drawing
     */
    public void draw(final GraphicsContext gc) {
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
     * Adds time to the remaining time, up to the maximum time.
     * Unit of time is seconds.
     *
     * @param timeToAdd the time to add to the timer.
     */
    public void addTime(final int timeToAdd) {
        timeRemaining += timeToAdd;
        if (timeRemaining > MAX_TIME * MILLISECONDS) {
            timeRemaining = MAX_TIME * MILLISECONDS;
        }
    }

    /**
     * Removes time from the remaining time, down to a minimum of 0.
     * Unit of time is seconds.
     *
     * @param timeToRemove time to remove from the level.
     */
    public void removeTime(final int timeToRemove) {
        timeRemaining -= timeToRemove;
        if (timeRemaining < 0) {
            timeRemaining = 0;
        }
    }

    /**
     * Resets the last update time to the current system time.
     * Used when resuming from pause to prevent time jump.
     */
    public void resetLastUpdateTime() {
        this.lastUpdateTime = System.nanoTime();
    }


    /**
     * Gets the time remaining in seconds.
     *
     * @return time remaining in seconds
     */
    public long getTimeRemainingTimeInSeconds() {
        return timeRemaining / MILLISECONDS;
    }

    /**
     * Adds score to the current score.
     *
     * @param scoreToAdd the score to add
     */
    public void addScore(final int scoreToAdd) {
        score += scoreToAdd;
        gameController.scoreLabel.setText(SCORE_LABEL_PREFIX + score);
    }

    /**
     * Sets the current score to a specific value.
     *
     * @param score the new score value
     */
    private void setScore(final int score) {
        this.score = score;
    }

    /**
     * Removes score from the current score, down to a minimum of 0.
     *
     * @param scoreToRemove the score to remove
     */
    public void removeScore(final int scoreToRemove) {
        score -= scoreToRemove;
        if (score < 0) {
            score = 0;
        }
        gameController.scoreLabel.setText(SCORE_LABEL_PREFIX + score);
    }

    /**
     * Gets the current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the reason why the player failed the level.
     *
     * @param failReason the reason of failure.
     */
    public void setFailReason(final String failReason) {
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
     * Reads through the level file and populates the level appropriately.
     * This covers map size, time limit, individual tiles,
     * the player start location, NPCs and Items.
     *
     * @param filename the name of the level to be loaded
     * @throws FileNotFoundException if the file name entered is not found
     * @author Ben Poole, Iyaad
     */
    public void loadFreshLevel(final String filename)
            throws FileNotFoundException {
        String levelId = extractLevelId(filename);
        java.nio.file.Path levelPath = java.nio.file.Path.of(LEVELS_FOLDER,
                filename);
        LevelDef def = new LevelLoader().loadLevel(levelId, levelPath);
        MAX_TIME = def.timeLimitSec;
        int x = def.width;
        int y = def.height;
        grid = new Tile[x][y];

        // Tiles: row 0 = top, row y-1 = bottom
        for (int row = 0; row < y; row++) {
            String rowString = def.tiles.get(row); //e.g. "YYYY RBYG YRGB BRGY"
            String[] tileTokens = rowString.split("\\s+");
            for (int col = 0; col < x; col++) {
                String sequence = tileTokens[col];      // e.g. "YYYY"
                Colour[] colours = new Colour[TILE_COLOUR_COUNT];
                for (int z = 0; z < TILE_COLOUR_COUNT; z++) {
                    char colChar = sequence.charAt(z);
                    colours[z] = colourSetter(String.valueOf(colChar));
                }
                int xPos = (x - COORD_OFFSET) - col;    // 0..x-1
                int yPos = (y - COORD_OFFSET) - row;    // 0..y-1
                grid[xPos][yPos] = new Tile(xPos, yPos, colours);
            }
        }

        int xPos = def.playerStart.x;  // tile index
        int yPos = def.playerStart.y;  // tile index
        // clamp to grid bounds just in case
        xPos = Math.max(0, Math.min(xPos, getWidth() - COORD_OFFSET));
        yPos = Math.max(0, Math.min(yPos, getHeight() - COORD_OFFSET));
        // use tile indices directly
        double px = xPos;
        double py = yPos;
        player = new Player(grid[(int) px][(int) py], this);

        // Items and gates + ENEMIES
        for (EntityDef e : def.entities) {
            int rawX = e.x;
            int rawY = e.y;
            int entityXPos = rawX - COORD_OFFSET;
            int entityYPos = rawY - COORD_OFFSET;
            String npcId = e.type;

            switch (e.type) {
                case TYPE_FLYING -> {
                    // e.arg1 = direction string ("UP","DOWN","LEFT","RIGHT")
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    FlyingAssassin tempEnemy = new FlyingAssassin(
                            grid[entityXPos][entityYPos], direction,
                            this, npcId);
                    enemies.add(tempEnemy);
                }
                case TYPE_SMART -> {
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    SmartThief tempEnemy = new SmartThief(
                            grid[entityXPos][entityYPos], direction,
                            this, npcId);
                    enemies.add(tempEnemy);
                }
                case TYPE_FOLLOWER -> {
                    // e.arg1 = direction,
                    // e.arg2 = follower colour ("R","G","B","Y")
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = e.arg2;
                    Colour colour = colourSetter(followerColour);
                    FloorThief tempEnemy = new FloorThief(colour,
                            grid[entityXPos][entityYPos], direction,
                            this, npcId);
                    enemies.add(tempEnemy);
                }
                case TYPE_CAMPER -> {
                    String npcDirection = e.arg1;
                    Direction direction = directionSetter(npcDirection);
                    Camper tempEnemy = new Camper(
                            grid[entityXPos][entityYPos], direction,
                            this, npcId);
                    enemies.add(tempEnemy);
                }
                case TYPE_LOOT -> {
                    String value = e.arg1;
                    Loot tempLoot;
                    switch (value) {
                        case LOOT_CENT -> tempLoot = new Loot(
                                LootEnum.CENT,
                                entityXPos, entityYPos);
                        case LOOT_DOLLAR -> tempLoot = new Loot(
                                LootEnum.DOLLAR,
                                entityXPos, entityYPos);
                        case LOOT_RUBY -> tempLoot = new Loot(
                                LootEnum.RUBY,
                                entityXPos, entityYPos);
                        case LOOT_DIAMOND -> tempLoot = new Loot(
                                LootEnum.DIAMOND,
                                entityXPos, entityYPos);
                        default -> tempLoot = null;
                    }
                    if (tempLoot != null) {
                        items.add(tempLoot);
                        grid[entityXPos][entityYPos].setOccupying(tempLoot);
                    }
                }
                case TYPE_BOMB -> {
                    Bomb tempBomb = new Bomb(entityXPos, entityYPos);
                    items.add(tempBomb);
                    grid[entityXPos][entityYPos].setOccupying(tempBomb);
                }
                case TYPE_LEVER -> {
                    Colour colour = colourSetter(e.arg1);
                    Lever tempLever = new Lever(colour, entityXPos, entityYPos);
                    items.add(tempLever);
                    grid[entityXPos][entityYPos].setOccupying(tempLever);
                }
                case TYPE_GATE -> {
                    Colour colour = colourSetter(e.arg1);
                    Gate tempGate = new Gate(colour, entityXPos, entityYPos);
                    gates.add(tempGate);
                    grid[entityXPos][entityYPos].setOccupying(tempGate);
                }
                case TYPE_DOOR -> {
                    Door tempDoor = new Door(entityXPos, entityYPos);
                    items.add(tempDoor);
                    grid[entityXPos][entityYPos].setOccupying(tempDoor);
                }
                case TYPE_CLOCK -> {
                    Clock tempClock = new Clock(entityXPos, entityYPos);
                    items.add(tempClock);
                    grid[entityXPos][entityYPos].setOccupying(tempClock);
                }
                case TYPE_SHIELD -> {
                    Shield shield = new Shield(entityXPos, entityYPos);
                    items.add(shield);
                    grid[entityXPos][entityYPos].setOccupying(shield);
                }
                default -> {
                    System.out.println(
                            ERR_UNKNOWN_ENTITY + e.type);
                }
            }
        }

        //Add gates to levers
        for (Item item : items) {
            if (item instanceof Lever lever) {
                for (Gate gate : gates) {
                    if (gate.getColour() == lever.getColour()) {
                        lever.addGate(gate);
                    }
                }
            }
        }
    }

    /**
     * Loads the grid from the level file.
     */
    public void loadGrid() {
        String filename = LEVEL_PREFIX + GameManager.getCurrentLevelNumber() + LEVEL_SUFFIX;
        String levelId = extractLevelId(filename);
        java.nio.file.Path levelPath = java.nio.file.Path.of(LEVELS_FOLDER, filename);
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
                    for (int z = 0; z < colours.length; z++) {
                        char colChar = sequence.charAt(z);
                        colours[z] = colourSetter(String.valueOf(colChar));
                    }
                    int xPos = (x - COORD_OFFSET) - col;
                    int yPos = (y - COORD_OFFSET) - row;
                    grid[xPos][yPos] = new Tile(xPos, yPos, colours);
                }
            }
        } catch (Exception e) {
            System.out.println(ERR_LOAD_GRID + filename);
            e.printStackTrace();
        }
    }

    /**
     * Loads the saved state into the level,
     * including player position, NPCs, time remaining, score, and items.
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
        // temporary init; will be overridden below
        player = new Player(playerTile, this);
        // Initialize NPCs from save data -----------------------------------
        for (Map.Entry<String, Object> npcState
                : saveData.getNpcStates().entrySet()) {
            Map<String, Object> state =
                    (Map<String, Object>) npcState.getValue();
            String npcType = npcState.getKey();
            int rawX = (int) state.get(KEY_X);
            int rawY = (int) state.get(KEY_Y);
            int xPos = rawX;
            int yPos = rawY;

            switch (npcType) {
                case TYPE_FLYING -> {
                    String npcDirection = (String) state.get(KEY_DIR);
                    Direction direction = directionSetter(npcDirection);
                    FlyingAssassin tempEnemy =
                            new FlyingAssassin(grid[xPos][yPos],
                                    direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                case TYPE_FOLLOWER -> {
                    String npcDirection = (String) state.get(KEY_DIR);
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = (String) state.get(KEY_COLOUR);
                    Colour colour = colourSetter(followerColour);
                    FloorThief tempEnemy =
                            new FloorThief(colour, grid[xPos][yPos],
                                    direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                case TYPE_SMART -> {
                    String npcDirection = (String) state.get(KEY_DIR);
                    Direction direction = directionSetter(npcDirection);
                    SmartThief tempEnemy =
                            new SmartThief(grid[xPos][yPos],
                                    direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                case TYPE_CAMPER -> {
                    String npcDirection = (String) state.get(KEY_DIR);
                    Direction direction = directionSetter(npcDirection);
                    Camper tempEnemy =
                            new Camper(grid[xPos][yPos],
                                    direction, this, npcType);
                    enemies.add(tempEnemy);
                }
                default -> {
                    System.out.println(ERR_UNKNOWN_ENTITY_SAVE
                            + npcType);
                }
            }
        }

        // Set time remaining from save data -------------------------------
        setTimeRemaining(saveData.getTimeRemainingMs());

        // Set score from save data ----------------------------------------
        setScore(saveData.getScore());

        // Set the items from save data --------------------------------------
        for (Map.Entry<String, Object> itemState
                : saveData.getItems().entrySet()) {
            String itemType = itemState.getKey().split(PARAM_SEPARATOR)[0];
            Map<String, Object> state =
                    (Map<String, Object>) itemState.getValue();
            int xPos = (int) state.get(KEY_X);
            int yPos = (int) state.get(KEY_Y);

            switch (itemType) {
                case TYPE_LOOT -> {
                    String value = (String) state.get(KEY_PARAM);
                    Loot tempLoot;
                    switch (value) {
                        case LOOT_CENT -> tempLoot = new Loot(
                                LootEnum.CENT, xPos, yPos);
                        case LOOT_DOLLAR -> tempLoot = new Loot(
                                LootEnum.DOLLAR, xPos, yPos);
                        case LOOT_RUBY -> tempLoot = new Loot(
                                LootEnum.RUBY, xPos, yPos);
                        case LOOT_DIAMOND -> tempLoot = new Loot(
                                LootEnum.DIAMOND, xPos, yPos);
                        default -> tempLoot = null;
                    }
                    if (tempLoot != null) {
                        items.add(tempLoot);
                        grid[xPos][yPos].setOccupying(tempLoot);
                    }
                }
                case TYPE_BOMB -> {
                    String[] bombParams = ((String)
                            state.get(KEY_PARAM)).split(PARAM_SEPARATOR);
                    Bomb tempBomb = getBombFromSave(bombParams, xPos, yPos);
                    items.add(tempBomb);
                    grid[xPos][yPos].setOccupying(tempBomb);
                }
                case TYPE_LEVER -> {
                    Colour colour = colourSetter((String) state.get(KEY_PARAM));
                    Lever tempLever = new Lever(colour, xPos, yPos);
                    items.add(tempLever);
                    grid[xPos][yPos].setOccupying(tempLever);
                }
                case TYPE_DOOR -> {
                    Door tempDoor = new Door(xPos, yPos);
                    items.add(tempDoor);
                    grid[xPos][yPos].setOccupying(tempDoor);
                }
                case TYPE_CLOCK -> {
                    Clock tempClock = new Clock(xPos, yPos);
                    items.add(tempClock);
                    grid[xPos][yPos].setOccupying(tempClock);
                }
                case TYPE_SHIELD -> {
                    Shield shield = new Shield(xPos, yPos);
                    items.add(shield);
                    grid[xPos][yPos].setOccupying(shield);
                }
                default -> {
                    System.out.println(ERR_UNKNOWN_ITEM_SAVE
                            + itemType);
                }
            }
        }

        // Set the gates from save data --------------------------------------
        for (Map.Entry<String, Object> gateState
                : saveData.getGates().entrySet()) {
            String gateType = gateState.getKey().split(PARAM_SEPARATOR)[0];
            Map<String, Object> state = (Map<String, Object>)
                    gateState.getValue();
            int xPos = (int) state.get(KEY_X);
            int yPos = (int) state.get(KEY_Y);

            switch (gateType) {
                case TYPE_GATE -> {
                    Colour colour = colourSetter((String) state.get(KEY_PARAM));
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
                    System.out.println(ERR_UNKNOWN_GATE_SAVE
                            + gateType);
                }
            }
        }

        // Re-link gates to levers after all items are loaded ----------------

    }

    /**
     * Makes a bomb from existing sava data.
     *
     * @param bombParams the parameters to make the bomb.
     * @param xPos       its x location
     * @param yPos       its y location
     * @return the bomb made.
     */
    private static Bomb getBombFromSave(String[] bombParams, int xPos, int yPos) {
        int countDownLeft = Integer.parseInt(bombParams[COUNTDOWN_LEFT]);
        double countdownTickProgress = Double.parseDouble(bombParams[COUNTDOWN_TICK_PROGRESS]);
        double nextBoomCountdown = Double.parseDouble(bombParams[NEXT_BOOM_COUNTDOWN]);
        int explosions = Integer.parseInt(bombParams[EXPLOSIONS]);
        boolean armed = Boolean.parseBoolean(bombParams[ARMED]);
        boolean exploding = Boolean.parseBoolean(bombParams[EXPLODING]);
        Bomb tempBomb = new Bomb(countDownLeft, countdownTickProgress,
                nextBoomCountdown, explosions, armed, exploding, xPos, yPos);
        return tempBomb;
    }


    /**
     * Quick helper method to find the levelID.
     *
     * @param filename the level file in .txt format
     * @return the level id
     */
    private String extractLevelId(final String filename) {
        // very basic: strip "level" prefix and ".txt" suffix if present
        String name = filename;
        if (name.startsWith(LEVEL_PREFIX)) {
            name = name.substring(LEVEL_PREFIX.length());
        }
        if (name.endsWith(LEVEL_SUFFIX)) {
            name = name.substring(0, name.length() - LEVEL_SUFFIX.length());
        }
        return name;
    }

    /**
     * converts strings to direction enum values.
     *
     * @param direction the string to be converted
     * @return the appropriate direction value
     */
    private Direction directionSetter(final String direction) {
        switch (direction) {
            case DIR_UP -> {
                return Direction.UP;
            }
            case DIR_DOWN -> {
                return Direction.DOWN;
            }
            case DIR_LEFT -> {
                return Direction.LEFT;
            }
            case DIR_RIGHT -> {
                return Direction.RIGHT;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * converts strings to colour enum values.
     *
     * @param colour the string to be converted
     * @return the appropriate colour value
     */
    private Colour colourSetter(final String colour) {
        switch (colour) {
            case COLOUR_RED -> {
                return Colour.RED;
            }
            case COLOUR_GREEN -> {
                return Colour.GREEN;
            }
            case COLOUR_BLUE -> {
                return Colour.BLUE;
            }
            case COLOUR_YELLOW -> {
                return Colour.YELLOW;
            }
            case COLOUR_CYAN -> {
                return Colour.CYAN;
            }
            case COLOUR_MAGENTA -> {
                return Colour.MAGENTA;
            }
            default -> {
                return null;
            }
        }
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
    public void setItems(final List<Item> items) {
        this.items = items;
    }

    /**
     * Adds an item to the level's list of items.
     *
     * @param item the item to be added.
     */
    public void addItem(final Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the level's list of items.
     *
     * @param item the item to be removed.
     */
    public void removeItem(final Item item) {
        items.remove(item);
    }

    /**
     * Allows tiles to be accessed.
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
    public void setGates(final List<Gate> gates) {
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

    /**
     * Removes a gate from the level.
     *
     * @param gate to remove from level.
     */
    public void removeGate(final Gate gate) {
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
     * Gets the current GameController.
     *
     * @return the game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Gets the list of all enemies.
     *
     * @return all the npcs.
     */
    public java.util.List<NonPlayableCharacter> getEnemies() {
        return enemies;
    }

    /**
     * Says if the level is finished.
     *
     * @return true if the level is finished.
     */
    public boolean isFinishedLevel() {
        return finishedLevel;
    }

    /**
     * sets level finished to true.
     */
    public void finishLevel() {
        this.finishedLevel = true;
    }

    /**
     * Sets the remaining time for the level.
     *
     * @param timeRemaining the remaining time in milliseconds
     */
    private void setTimeRemaining(final long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}