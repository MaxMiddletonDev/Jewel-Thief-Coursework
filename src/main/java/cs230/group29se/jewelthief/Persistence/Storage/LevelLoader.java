package cs230.group29se.jewelthief.Persistence.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for loading level definitions from compact level files.
 * Parses the file content into a `LevelDef` object, which contains
 * information about the level's size, time limit, tiles, and entities.
 */
public class LevelLoader {
    private static final int INITIAL_INDEX = 0;
    private static final int INCREASE_INDEX_BY = 1;
    private static final String READ_LVL_FAIL_MSG = "Failed to read level file: ";
    private static final String SIZE_FAIL_MSG = "Expected SIZE at start of ";
    private static final String TIME_FAIL_MSG = "Expected TIME after SIZE in ";


    /**
     * Loads a level definition from the specified file.
     *
     * @param levelId  The logical ID of the level (e.g., "1" for level8.txt).
     * @param filePath The path to the level text file.
     * @return A populated `LevelDef` object containing the level's data.
     * @throws RuntimeException if the file cannot be read or the format is invalid.
     */
    public LevelDef loadLevel(String levelId, Path filePath) {
        List<String> tokens = tokenize(filePath);
        int index = INITIAL_INDEX;

        LevelDef def = new LevelDef();
        def.setLevelId(levelId);
        def.setTiles(new ArrayList<>());
        def.setEntities(new ArrayList<>());
        def.setNpcStartStates(new ArrayList<>());
        def.setGates(new ArrayList<>());
        def.setItems(new ArrayList<>());

        // --- 1) SIZE w h ---
        // Parses the level's width and height.
        if (!tokens.get(index).equalsIgnoreCase("SIZE")) {
            throw new IllegalArgumentException(SIZE_FAIL_MSG + filePath);
        }
        index++;
        def.setWidth(Integer.parseInt(tokens.get(index++)));
        def.setHeight(Integer.parseInt(tokens.get(index++)));

        // --- 2) TIME seconds ---
        // Parses the time limit for the level.
        if (!tokens.get(index).equalsIgnoreCase("TIME")) {
            throw new IllegalArgumentException(TIME_FAIL_MSG + filePath);
        }
        index++;
        def.setTimeLimitSec(Integer.parseInt(tokens.get(index++)));

        // --- 3) Tiles ---
        // Parses the tile layout of the level.
        int tileCount = def.getWidth() * def.getHeight();
        List<String> flatTiles = new ArrayList<>();
        for (int t = INITIAL_INDEX; t < tileCount && index < tokens.size(); t++) {
            flatTiles.add(tokens.get(index++));
        }
        for (int row = INITIAL_INDEX; row < def.getHeight(); row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = INITIAL_INDEX; col < def.getWidth(); col++) {
                sb.append(flatTiles.get(row * def.getWidth() + col));
                if (col < def.getWidth() - INCREASE_INDEX_BY) sb.append(" ");
            }
            def.getTiles().add(sb.toString());
        }

        // --- 4) Entities ---
        // Parses the entities and their attributes.
        while (index < tokens.size()) {
            String type = tokens.get(index++).toUpperCase();

            if (index + INCREASE_INDEX_BY >= tokens.size()) break;

            int x = Integer.parseInt(tokens.get(index++));
            int y = Integer.parseInt(tokens.get(index++));

            String arg1 = null;
            String arg2 = null;

            if (index < tokens.size()) {
                String next = tokens.get(index);
                if (!isEntityKeyword(next)) {
                    arg1 = tokens.get(index++);
                }
            }
            if (index < tokens.size()) {
                String next = tokens.get(index);
                if (!isEntityKeyword(next)) {
                    arg2 = tokens.get(index++);
                }
            }

            EntityDef e = new EntityDef(type, x, y, arg1, arg2);
            def.getEntities().add(e);

            switch (type) {
                case "PLAYER" -> def.setPlayerStart(e);
                case "FLYING", "FOLLOWER", "SMART", "CAMPER" -> def.getNpcStartStates().add(e);
                case "GATE" -> def.getGates().add(e);
                case "LOOT", "BOMB", "LEVER", "CLOCK", "SHIELD" -> def.getItems().add(e);
                default -> { /* Other entities are only added to the entities list */ }
            }
        }

        return def;
    }

    /**
     * Tokenizes the content of the specified file.
     * Splits the file content into a list of whitespace-separated tokens.
     *
     * @param filePath The path to the file to tokenize.
     * @return A list of tokens extracted from the file.
     * @throws RuntimeException if the file cannot be read.
     */
    private List<String> tokenize(Path filePath) {
        try {
            String text = Files.readString(filePath);
            text = text.replace('\n', ' ').replace('\r', ' ');
            String[] parts = text.trim().split("\\s+");
            List<String> tokens = new ArrayList<>();
            for (String p : parts) {
                if (!p.isEmpty()) tokens.add(p);
            }
            return tokens;
        } catch (IOException e) {
            throw new RuntimeException(READ_LVL_FAIL_MSG + filePath, e);
        }
    }

    /**
     * Checks if the given token is a recognized entity keyword.
     *
     * @param token The token to check.
     * @return `true` if the token is an entity keyword, `false` otherwise.
     */
    private boolean isEntityKeyword(String token) {
        String t = token.toUpperCase();
        return switch (t) {
            case "PLAYER", "FLYING", "FOLLOWER", "SMART", "CAMPER",
                 "LOOT", "BOMB", "LEVER", "GATE", "CLOCK", "DOOR",
                 "SIZE", "TIME", "SHIELD" -> true;
            default -> false;
        };
    }
}
