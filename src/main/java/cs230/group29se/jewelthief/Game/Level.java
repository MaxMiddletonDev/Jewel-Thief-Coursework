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

    GameController gameController;

    List<Item> items = new ArrayList<>();
    Tile[][] grid;

    //Replace with actual player object later
    public Node dummyPlayer;

    //Add arraylist of enemies later

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
        grid = new Tile[][] {
                {
                        new Tile(1,1,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(1,2,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(1,3,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(1,4,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(1,5,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(1,6,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(1,7,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(1,8,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(1,9,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(1,10,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE})
                },
                {
                        new Tile(2,1,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(2,2,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(2,3,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(2,4,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(2,5,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(2,6,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(2,7,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(2,8,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(2,9,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(2,10,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED})
                },
                {
                        new Tile(3,1,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(3,2,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(3,3,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(3,4,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(3,5,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(3,6,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(3,7,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(3,8,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(3,9,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(3,10,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE})
                },
                {
                        new Tile(4,1,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(4,2,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(4,3,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(4,4,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(4,5,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(4,6,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(4,7,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(4,8,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(4,9,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(4,10,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED})
                },
                {
                        new Tile(5,1,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(5,2,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(5,3,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(5,4,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(5,5,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(5,6,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(5,7,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(5,8,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(5,9,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(5,10,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE})
                },
                {
                        new Tile(6,1,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(6,2,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(6,3,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(6,4,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(6,5,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(6,6,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(6,7,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(6,8,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(6,9,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(6,10,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED})
                },
                {
                        new Tile(7,1,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(7,2,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(7,3,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(7,4,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(7,5,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(7,6,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(7,7,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(7,8,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(7,9,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(7,10,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE})
                },
                {
                        new Tile(8,1,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(8,2,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(8,3,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(8,4,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(8,5,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(8,6,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(8,7,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(8,8,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(8,9,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(8,10,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED})
                },
                {
                        new Tile(9,1,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(9,2,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(9,3,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(9,4,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(9,5,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(9,6,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(9,7,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(9,8,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(9,9,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(9,10,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE})
                },
                {
                        new Tile(10,1,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(10,2,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(10,3,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(10,4,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(10,5,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(10,6,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED}),
                        new Tile(10,7,new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW}),
                        new Tile(10,8,new Colour[]{Colour.GREEN, Colour.RED, Colour.YELLOW, Colour.BLUE}),
                        new Tile(10,9,new Colour[]{Colour.BLUE, Colour.YELLOW, Colour.RED, Colour.GREEN}),
                        new Tile(10,10,new Colour[]{Colour.YELLOW, Colour.BLUE, Colour.GREEN, Colour.RED})
                }
        };



        timeRemaining = maxTime * 1000L;
        lastUpdateTime = System.nanoTime();
        gameController.scoreLabel.setText("Score: " + score);

        //Populate level from file todo: ben will fix later
//        try{
//            readLevelFile(levelName);
//        }catch(FileNotFoundException e){
//            System.out.println("Level file not found: " + levelName);
//            e.printStackTrace();
//        }

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

    //Called every tick from GameScreen.draw()
    public void draw(GraphicsContext gc) {
        for (Item item : items) {
            item.draw(gc);
        }

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                tile.draw(gc);
            }
        }

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
    public void readLevelFile(String filename) throws FileNotFoundException {
        int testMultiplier = 1;
        File inputFile = new File("levels/"+filename);
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
                for (int z = 0; z < 4; z++) {
                    char colChar = (char) reader.nextShort();
                    colours[z] = colourSetter(String.valueOf(colChar));
                }
                grid[j][i] = new Tile(j * testMultiplier, i * testMultiplier, colours);
            }
        }
        reader.nextLine();

        //reads characters (to be uncommented once player class pushed)

        /*
        x = reader.nextInt();
        y = reader.nextInt();
        Player player = new Player(x, y);
         */

        while (!reader.nextLine().isEmpty()) {
            String entityType = reader.next();
            switch (entityType) {
                case "FLYING" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    //new FlyingEnemy(xPos, yPos, direction);
                }
                case "SMART" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    //new SmartEnemy(xPos, yPos, direction);
                }
                case "FOLLOWER" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    String npcDirection = reader.next();
                    Direction direction = directionSetter(npcDirection);
                    String followerColour = reader.next();
                    Colour colour = colourSetter(followerColour);
                    //FollowerEnemy followerEnemy = new FollowerEnemy(xPos, yPos, direction, colour);
                }

            }

        }
        reader.nextLine();

        //reads items
        while (reader.hasNext()) {
            String itemType = reader.next();
            switch(itemType) {
                case "LOOT" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    //TEMPORARY FIX for array constructor
                    int[] itemPos = new int[]{xPos, yPos};
                    String value = reader.next();
                    switch (value) {
                        case "CENT" -> {
                            new Loot(itemPos, LootEnum.CENT);
                        }
                        case "DOLLAR" -> {
                            new Loot(itemPos, LootEnum.DOLLAR);
                        }
                        case "RUBY" -> {
                            new Loot(itemPos, LootEnum.RUBY);
                        }
                        case "DIAMOND" -> {
                            new Loot(itemPos, LootEnum.DIAMOND);
                        }
                    }
                }
                case "BOMB" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    //TEMPORARY FIX for array constructor
                    int[] itemPos = new int[]{xPos, yPos};
                    new Bomb(itemPos);
                }
                case "LEVER" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    //TEMPORARY FIX for array constructor
                    int[] itemPos = new int[]{xPos, yPos};
                    String leverColour = reader.next();
                    Colour colour = colourSetter(leverColour);
                    new Lever(colour, itemPos);
                }
                case "GATE" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    //TEMPORARY FIX for array constructor
                    int[] itemPos = new int[]{xPos, yPos};
                    String gateColour = reader.next();
                    Colour colour = colourSetter(gateColour);
                    new Gate(colour);
                }
                case "DOOR" -> {
                    int xPos = reader.nextInt();
                    int yPos = reader.nextInt();
                    //TEMPORARY FIX for array constructor
                    int[] itemPos = new int[]{xPos, yPos};
                    new Door(itemPos);
                }
            }
        }
        reader.close();
    }

    /**
     * Sets the direction based on the input string.
     * @param direction
     * @return
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
     * Sets the colour based on the input string.
     * @param colour
     * @return
     */
    private Colour colourSetter (String colour) {
        switch (colour) {
            case "R" -> {return Colour.RED;}
            case "G" -> {return Colour.GREEN;}
            case "B" -> {return Colour.BLUE;}
            case "Y" -> {return Colour.YELLOW;}
        }
        return null;
    }
}
