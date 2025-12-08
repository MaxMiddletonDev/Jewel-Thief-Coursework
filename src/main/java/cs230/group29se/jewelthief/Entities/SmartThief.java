package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Collectable;
import cs230.group29se.jewelthief.Items.Door;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a Smart Thief.
 * The Smart Thief actively seeks out items using BFS.
 * If no loot remains, it targets the exit. If no path to a target is found, it defaults to moving randomly.
 *
 * @author Max Middleton
 */
public class SmartThief extends FloorThief {
    private Level level;
    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/Entities/NPCs/SMARTTHIEF.png").toString());

    /**
     * Constructs a new SmartThief instance.
     * @param startingTile The tile where the thief spawns.
     * @param direction The initial facing direction.
     * @param level The game level instance.
     * @param id The unique identifier for this entity.
     */
    public SmartThief(Tile startingTile, Direction direction, Level level, String id) {
        super(null, startingTile, direction, level, id);
        this.level = level;
    }

    /**
     * Gets specific enemies sprite
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * Interactions with a collectable item.
     * @param item the item to interact with.
     */
    @Override
    public void collectItem(Item item) {
        if (item instanceof Door) {
            level.getPlayer().setAliveTo(false);
        }
        super.collectItem(item);
    }

    /**
     * Executes the movement logic for the Smart Thief.
     * Checks if movement is possible. Attempts to find a path to items. If no loot remains, targets the door.
     * If a valid path is found, moves towards the target.
     * If no path is found, moves in a random valid direction.
     */
    @Override
    public void move() {
        if (!isAlive) {
            return;
        }

        if (!canMove()) {
            return;
        }

        Direction nextMove;

        if (!level.containsNoLootAndLevers()) {
            nextMove = findPathTo("LOOT");
        } else {
            nextMove = findPathTo("EXIT");
        }

        if (nextMove != null) {
            this.direction = nextMove;
            Tile destination = calculateDestination(currentTile, nextMove);
            if (destination != null) {
                this.currentTile = destination;
                triggerAdjacentBombs();
            }
        } else {
            // Random but valid movement if path finding doesn't work
            List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
            Collections.shuffle(directions);

            for (Direction direction : directions) {
                Tile target = calculateDestination(currentTile, direction);
                if (target != null) {
                    this.direction = direction;
                    this.currentTile = target;

                    return;
                }
            }
        }
    }

    /**
     * Uses Breadth-First Search to find the first move required to reach the nearest target.
     * @param targetType The type of object to search for items or doors.
     * @return The Direction to move in to start the path, or null if no path exists.
     */
    private Direction findPathTo(String targetType) {
        int width = level.getWidth();
        int height = level.getHeight();

        boolean[][] visitedTiles = new boolean[width][height];
        // Stores the initial direction taken to reach any given tile
        Direction[][] firstMove = new Direction[width][height];

        Queue<Tile> tilesToVisit = new LinkedList<>();

        Tile startNode = this.currentTile;
        tilesToVisit.add(startNode);
        visitedTiles[startNode.getX()][startNode.getY()] = true;

        while (!tilesToVisit.isEmpty()) {
            Tile current = tilesToVisit.poll();
            Object obj = current.getOccupying();

            // Check for current tile contains target
            boolean found = false;

            if (targetType.equals("LOOT")) {
                if (obj instanceof Collectable && !(obj instanceof Bomb)) {
                    found = true;
                }
            } else if (targetType.equals("EXIT")) {
                if (obj instanceof Door) {
                    found = true;
                }
            }

            // If found, return the direction that started this path
            if (found) {
                return firstMove[current.getX()][current.getY()];
            }

            for (Direction direction : Direction.values()) {
                // Calculate where a move in this direction would land
                Tile neighbour = calculateDestination(current, direction);

                if (neighbour != null) {
                    int nx = neighbour.getX();
                    int ny = neighbour.getY();

                    if (!visitedTiles[nx][ny]) {
                        // Ensure we can pass gates
                        boolean gateOpen = !neighbour.hasGate() || hasLever(neighbour.getGateColour());

                        if (!isBlockedPath(neighbour) && gateOpen) {
                            visitedTiles[nx][ny] = true;
                            tilesToVisit.add(neighbour);

                            // Track the first move: if we are at start, this direction is the first move. Else, passes the first move
                            if (current == startNode) {
                                firstMove[nx][ny] = direction;
                            } else {
                                firstMove[nx][ny] = firstMove[current.getX()][current.getY()];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calculates the destination tile based on the current position and direction.
     * @param start The starting tile.
     * @param direction The direction of movement.
     * @return the final tile destination.
     */
    private Tile calculateDestination(Tile start, Direction direction) {
        int x = start.getX();
        int y = start.getY();
        int dx = direction.getX();
        int dy = direction.getY();

        x += dx;
        y += dy;

        while (x >= 0 && x < level.getWidth() && y >= 0 && y < level.getHeight()) {
            Tile target = level.getTile(x, y);

            if (start.isValidMove(target)) {
                return target;
            }

            if (isBlockedPath(target)) {
                return null;
            }

            x += dx;
            y += dy;
        }
        return null;
    }

    /**
     * Checks if a tile is blocked by other entities.
     * @param tile The tile to check.
     * @return true if the tile is occupied by a Bomb, Player, or another Enemy; false otherwise.
     */
    private boolean isBlockedPath(Tile tile) {
        if (tile.hasBomb()) {
            return true;
        }

        Player player = level.getPlayer();
        if (player.isAlive()) {
            int[] pos = player.getPosition();
            if (pos[0] == tile.getX() && pos[1] == tile.getY()) {
                return true;
            }
        }

        for (NonPlayableCharacter npc : level.getEnemies()) {
            if (npc != this && npc.isAlive()) {
                if (npc.currentTile == tile) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks the 4 tiles immediately surrounding the player. If a bomb is found, it is activated.
     */
    private void triggerAdjacentBombs() {
        int currentX = currentTile.getX();
        int currentY = currentTile.getY();

        // These are the adjacent tiles
        int[][] offsets = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] offset : offsets) {
            int checkX = currentX + offset[0];
            int checkY = currentY + offset[1];

            if (checkX >= 0 && checkX < level.getWidth() && checkY >= 0 && checkY < level.getHeight()) {
                Tile neighbour = level.getTile(checkX, checkY);
                if (neighbour != null && neighbour.getOccupying() instanceof Bomb bomb) {
                    bomb.interact();
                }
            }
        }
    }
}