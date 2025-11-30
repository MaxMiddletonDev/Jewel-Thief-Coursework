package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.*;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    //Replace with actual player object later
    public Node dummyPlayer;

    private Player player;

    private int maxTime = 60; // Seconds
    private long timeRemaining; // Milliseconds
    private long lastUpdateTime; // Nanoseconds

    private int score;


    /**
     * Constructs a Level with the specified level name and game controller.
     *
     * @param levelName      the name of the level file
     * @param gameController the game controller for updating UI elements
     */
    public Level(String levelName, GameController gameController){
        this.gameController = gameController;
        dummyPlayer = new Rectangle(38, 38, Color.GREEN);
        dummyPlayer.setTranslateY(0);
        dummyPlayer.setTranslateX(0);

        //Test Grid
        Tile[][] grid;


        //Populate level from file todo: ben will fix later
        try{
            readLevelFile(levelName);
        }catch(FileNotFoundException e){
            System.out.println("Level file not found: " + levelName);
            e.printStackTrace();
        }

        timeRemaining = maxTime * 1000L;
        lastUpdateTime = System.nanoTime();
        gameController.scoreLabel.setText("Score: " + score);



    }
    public void update() {
        updateTime();
    }
    private void updateTime() {
        long now = System.nanoTime();
        long elapsedTime = (now - lastUpdateTime) / 1_000_000;
        lastUpdateTime = now;

        timeRemaining -= elapsedTime;

        if (timeRemaining <= 0) {
            timeRemaining = 0;
            //TODO: PLAYER LOSS
        }
        gameController.timerLabel.setText("Time: " + getTimeRemainingTimeInSeconds() + "s");

    }

    /**
     * Returns the width of the grid.
     * @return the width of the grid.
     */
    public int getWidth() {
        return grid.length;
    }

    /**
     * Returns the height of the grid.
     * @return the height of the grid.
     */
    public int getHeight() {
        return grid[0].length;
    }

    /**
     * This method finds the tile at the specified (x, y) coordinates.
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

    //Called every tick from GameScreen.draw()
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
        //player.draw(gc);
    }

    /**
     * Gets the level number.
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Adds time to the remaining time, up to the maximum time.
     * Unit of time is seconds.
     * @param timeToAdd
     */
    public void addTime(int timeToAdd){
        timeRemaining += timeToAdd;
        if (timeRemaining > maxTime){
            timeRemaining = maxTime;
        }
    }

    /**
     * Removes time from the remaining time, down to a minimum of 0.
     * Unit of time is seconds.
     * @param timeToRemove
     */
    public void removeTime(int timeToRemove){
        timeRemaining -= timeToRemove;
        if (timeRemaining < 0){
            timeRemaining = 0;
        }
    }

    /**
     * Gets the time remaining in seconds.
     * @return time remaining in seconds
     */
    public long getTimeRemainingTimeInSeconds(){
        return timeRemaining / 1000;
    }

    /**
     * Adds score to the current score.
     * @param scoreToAdd the score to add
     */
    public void addScore(int scoreToAdd){
        score += scoreToAdd;
        gameController.scoreLabel.setText("Score: " + score);
    }

    /**
     * Removes score from the current score, down to a minimum of 0.
     * @param scoreToRemove the score to remove
     */
    public void removeScore(int scoreToRemove){
        score -= scoreToRemove;
        if (score < 0){
            score = 0;
        }
        gameController.scoreLabel.setText("Score: " + score);
    }

    /**
     * Gets the current score.
     * @return the current score
     */
    public int getScore() {
        return score;
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
     * @author Ben Poole
     * @param filename the name of the level to be loaded
     * @throws FileNotFoundException if the file name entered is not found
     */
    public void readLevelFile(String filename) throws FileNotFoundException {
        int testMultiplier = 1;
        File inputFile = new File("levels/"+filename); //TODO: save files are probs somewhere else bub
        Scanner reader = new Scanner(inputFile);
        reader.next();

        // reads size of grid
        int x = reader.nextInt();
        int y = reader.nextInt();
        grid = new Tile[x][y];
        reader.nextLine();

        // reads time limit
        reader.next();
        maxTime = reader.nextInt();
        reader.nextLine();

        // reads tile data
        for (int i = y; i > 0; i--) {
            reader.nextLine();
            for (int j = x; j > 0; j--) {
                Colour[] colours = new Colour[4];
                String sequence = reader.next();
                for (int z = 0; z < 4; z++) {
                    char colChar = sequence.charAt(z);
                    colours[z] = colourSetter(String.valueOf(colChar));
                }
                grid[j - 1][i - 1] = new Tile(j * testMultiplier, i * testMultiplier, colours);
            }
        }
        reader.nextLine();
        reader.nextLine();
        //reads characters (to be uncommented once player class pushed)
        reader.next();
        x = reader.nextInt();
        y = reader.nextInt();
        player = new Player(grid[x][y], this);

        // reads and creates NPCs and items
        while (reader.hasNextLine()) {
            String entityType = reader.next();
            switch (entityType) {
                case "FLYING" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    FlyingAssasin tempEnemy = new FlyingAssasin(grid[xPos][yPos], direction, this);
                    enemies.add(tempEnemy);
                }
                case "SMART" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    //Adding Smart enemy to arrayList
                    //Smart Enemy temp enemy = new SmartEnemy(grid[xPos][yPos], direction);
                    //enemies.add(tempEnemy);
                }
                case "FOLLOWER" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = reader.next();
                    Colour colour = colourSetter(followerColour);
                    FloorThief tempEnemy = new FloorThief(colour, grid[xPos][yPos], direction, this);
                    enemies.add(tempEnemy);
                }
                case "LOOT" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String value = reader.next();
                    switch (value) {
                        case "CENT" -> {
                            Loot tempLoot = new Loot(LootEnum.CENT, xPos, yPos);
                            items.add(tempLoot);
                            grid[xPos-1][yPos-1].setOccupying(tempLoot);
                        }
                        case "DOLLAR" -> {
                            Loot tempLoot = new Loot(LootEnum.DOLLAR, xPos, yPos);
                            items.add(tempLoot);
                            grid[xPos-1][yPos-1].setOccupying(tempLoot);
                        }
                        case "RUBY" -> {
                            Loot tempLoot = new Loot(LootEnum.RUBY, xPos, yPos);
                            items.add(tempLoot);
                            grid[xPos-1][yPos-1].setOccupying(tempLoot);
                        }
                        case "DIAMOND" -> {
                            Loot tempLoot =new Loot(LootEnum.DIAMOND, xPos, yPos);
                            items.add(tempLoot);
                            grid[xPos-1][yPos-1].setOccupying(tempLoot);
                        }
                    }
                }
                case "BOMB" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    Bomb tempBomb = new Bomb(xPos, yPos);
                    items.add(tempBomb);
                    grid[xPos-1][yPos-1].setOccupying(tempBomb);
                }
                case "LEVER" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String leverColour = reader.next();
                    Colour colour = colourSetter(leverColour);
                    Lever tempLever = new Lever(colour, xPos, yPos);
                    items.add(tempLever);
                    grid[xPos-1][yPos-1].setOccupying(tempLever);
                }
                case "GATE" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String gateColour = reader.next();
                    Colour colour = colourSetter(gateColour);
                    Gate tempGate = new Gate(colour, xPos, yPos);
                    gates.add(tempGate);
                    grid[xPos-1][yPos-1].setOccupying(tempGate);
                }
                case "DOOR" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    Door tempDoor = new Door(xPos, yPos);
                    items.add(tempDoor);
                    grid[xPos-1][yPos-1].setOccupying(tempDoor);
                }
                case "CLOCK" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    Clock tempClock = new Clock(xPos, yPos);
                    items.add(tempClock);
                    grid[xPos-1][yPos-1].setOccupying(tempClock);
                }
                default -> {

                }
            }
        }
        reader.close();
    }

    /**
     * converts strings to direction enum values
     * @param direction the string to be converted
     * @return the appropriate direction value
     */
    private Direction directionSetter (String direction) {
        switch (direction) {
            case "UP" -> {return Direction.UP;}
            case "DOWN" -> {return Direction.DOWN;}
            case "LEFT" -> {return Direction.LEFT;}
            case "RIGHT" -> {return Direction.RIGHT;}
        }
        return null;
    }

    /**
     * converts strings to colour enum values
     * @param colour the string to be converted
     * @return the appropriate colour value
     */
    private Colour colourSetter (String colour) {
        switch (colour) {
            case "R" -> { return Colour.RED; }
            case "G" -> { return Colour.GREEN; }
            case "B" -> { return Colour.BLUE; }
            case "Y" -> { return Colour.YELLOW; }
        }
        return null;
    }


    /**
     * Returns a list of the current levels intact items.
     * @return the list of items un-removed in the level.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Changes the current levels intact items.
     * Allows for items to be removed from drawing.
     * @param items the new list of items.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Adds an item to the level's list of items.
     * @param item the item to be added.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the level's list of items.
     * @param item the item to be removed.
     */

    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Allows tiles to be accessed
     * @return the grid of the level made of tiles.
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * Changes the list of intact gates in the level to be changed.
     * @param gates the gates still intact.
     */
    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    /**
     * Gets the list of intact gates.
     * @return the list of intact gates in the level.
     */
    public List<Gate> getGates() {
        return gates;
    }
}
