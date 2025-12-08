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
        def.levelId = levelId;
        def.tiles = new ArrayList<>();
        def.entities = new ArrayList<>();
        def.npcStartStates = new ArrayList<>();
        def.gates = new ArrayList<>();
        def.items = new ArrayList<>();

        // --- 1) SIZE w h ---
        // Parses the level's width and height.
        if (!tokens.get(index).equalsIgnoreCase("SIZE")) {
            throw new IllegalArgumentException("Expected SIZE at start of " + filePath);
        }
        index++;
        def.width = Integer.parseInt(tokens.get(index++));
        def.height = Integer.parseInt(tokens.get(index++));

        // --- 2) TIME seconds ---
        // Parses the time limit for the level.
        if (!tokens.get(index).equalsIgnoreCase("TIME")) {
            throw new IllegalArgumentException("Expected TIME after SIZE in " + filePath);
        }
        index++;
        def.timeLimitSec = Integer.parseInt(tokens.get(index++));

        // --- 3) Tiles ---
        // Parses the tile layout of the level.
        int tileCount = def.width * def.height;
        List<String> flatTiles = new ArrayList<>();
        for (int t = INITIAL_INDEX; t < tileCount && index < tokens.size(); t++) {
            flatTiles.add(tokens.get(index++));
        }
        for (int row = INITIAL_INDEX; row < def.height; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = INITIAL_INDEX; col < def.width; col++) {
                sb.append(flatTiles.get(row * def.width + col));
                if (col < def.width - INCREASE_INDEX_BY) sb.append(" ");
            }
            def.tiles.add(sb.toString());
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
            def.entities.add(e);

            switch (type) {
                case "PLAYER" -> def.playerStart = e;
                case "FLYING", "FOLLOWER", "SMART", "CAMPER" -> def.npcStartStates.add(e);
                case "GATE" -> def.gates.add(e);
                case "LOOT", "BOMB", "LEVER", "CLOCK", "SHIELD" -> def.items.add(e);
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
            throw new RuntimeException("Failed to read level file: " + filePath, e);
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
