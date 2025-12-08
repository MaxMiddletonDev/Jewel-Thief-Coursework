package cs230.group29se.jewelthief.Persistence.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads a LevelDef from a compact level file like:
 *
 * SIZE 4 4TIME 300YYYY RBYG YRGB BRGYYBGY RBYG YRGB BRGYGBGY RBYG YRGB BRGYBBGY RBYG YRGB BRGYPLAYER 0 0...
 *
 * All content is treated as a single stream of whitespace-separated tokens.
 */
public class LevelLoader {

    /**
     * Parses a level definition from the given file.
     *
     * @param levelId  logical id, e.g. "1" for level8.txt
     * @param filePath path to the level text file
     * @return populated LevelDef
     */
    public LevelDef loadLevel(String levelId, Path filePath) {
        List<String> tokens = tokenize(filePath);
        int index = 0;

        LevelDef def = new LevelDef();
        def.levelId = levelId;
        def.tiles = new ArrayList<>();
        def.entities = new ArrayList<>();
        def.npcStartStates = new ArrayList<>();
        def.gates = new ArrayList<>();
        def.items = new ArrayList<>();

        // --- 1) SIZE w h ---
        // Expect: SIZE 4 4
        if (!tokens.get(index).equalsIgnoreCase("SIZE")) {
            throw new IllegalArgumentException("Expected SIZE at start of " + filePath);
        }
        index++;
        def.width = Integer.parseInt(tokens.get(index++));
        def.height = Integer.parseInt(tokens.get(index++));

        // --- 2) TIME seconds ---
        // Expect: TIME 300
        if (!tokens.get(index).equalsIgnoreCase("TIME")) {
            throw new IllegalArgumentException("Expected TIME after SIZE in " + filePath);
        }
        index++;
        def.timeLimitSec = Integer.parseInt(tokens.get(index++));

        // --- 3) Tiles ---
        // For SIZE 4 4 we expect width*height = 16 tile tokens.
        int tileCount = def.width * def.height;
        List<String> flatTiles = new ArrayList<>();
        for (int t = 0; t < tileCount && index < tokens.size(); t++) {
            flatTiles.add(tokens.get(index++));
        }
        // You can either keep flatTiles as-is, or join them into "rows".
        // Here we store each row as a single string in def.tiles.
        for (int row = 0; row < def.height; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < def.width; col++) {
                sb.append(flatTiles.get(row * def.width + col));
                if (col < def.width - 1) sb.append(" ");
            }
            def.tiles.add(sb.toString());
        }

        // --- 4) Entities ---
        // Remaining tokens are like:
        // PLAYER 0 0
        // FLYING 1 1 RIGHT
        // FOLLOWER 4 6 DOWN B
        // SMART 5 7 LEFT
        // LOOT 2 3 DOLLAR
        // BOMB 6 6
        // LEVER 1 8 R
        // GATE 3 8 R
        // DOOR 10 8
        while (index < tokens.size()) {
            String type = tokens.get(index++).toUpperCase();

            // Guard in case of malformed data
            if (index + 1 >= tokens.size()) break;

            int x = Integer.parseInt(tokens.get(index++));
            int y = Integer.parseInt(tokens.get(index++));

            String arg1 = null;
            String arg2 = null;

            // Look ahead: some entity types have extra parameters
            if (index < tokens.size()) {
                String next = tokens.get(index);

                // If next token looks like a word (not another entity keyword),
                // treat it as arg1. We keep it simple: just check known types.
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
                case "LOOT", "BOMB", "LEVER","CLOCK","SHIELD" -> def.items.add(e);
                default -> { /* others only in entities list */ }
            }
        }

        return def;
    }

    // ----- helpers -----

    private List<String> tokenize(Path filePath) {
        try {
            String text = Files.readString(filePath);
            // Replace any line breaks with spaces, then split on whitespace
            text = text.replace('\n', ' ')
                    .replace('\r', ' ');
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

    private boolean isEntityKeyword(String token) {
        String t = token.toUpperCase();
        return switch (t) {
            case "PLAYER", "FLYING", "FOLLOWER", "SMART", "CAMPER",
                 "LOOT", "BOMB", "LEVER", "GATE","CLOCK" , "DOOR",
                 "SIZE", "TIME", "SHIELD" -> true;
            default -> false;
        };
    }
}