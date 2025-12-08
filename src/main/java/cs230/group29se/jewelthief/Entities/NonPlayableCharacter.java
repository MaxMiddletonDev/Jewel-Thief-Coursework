package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import cs230.group29se.jewelthief.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides functionality for a non-playable character as well as provides data for the same.
 *
 * @author Baba & Max
 */
public abstract class NonPlayableCharacter implements MoveableCharacter, Protectable {
    public static final int HIT_COOLDOWN = 2;
    public static final int MOVE_COOLDOWN = 1;
    /**
     * Tracks Facing direction
     */
    protected boolean isFacingRight = true;

    /**
     * Gets specific enemies sprite
     */
    public abstract Image getImage();

    /**
     * Gives us an NPCs current tile on which they're on.
     */
    public Tile currentTile;

    /**
     * tells us if an NPC is alive or not
     */
    public boolean isAlive;

    /**
     * tells us which direction an NPC is facing
     */
    public Direction direction;

    /**
     * Tells us what items an NPC has collected
     */
    protected List<Item> collectedItems = new ArrayList<>();

    /**
     * Used for saving npcs name and location
     */
    protected String id;

    /**
     * Tells us whether an NPC is protected or not
     */
    protected boolean isProtected;

    private double hitCooldownSeconds = HIT_COOLDOWN;
    private double hitCooldownTicks = hitCooldownSeconds * MainApplication.TPS;
    protected double hitCooldown = 0;
    private double moveCooldownSeconds = MOVE_COOLDOWN;
    private double moveCooldownTicks = moveCooldownSeconds * MainApplication.TPS;
    private double moveCooldownCounter = 0;

    /**
     * Constructor for creating a new instance of an NPC
     * @param startingTile The beginning tile of NPC
     * @param direction The Direction of the tile
     */
    public NonPlayableCharacter(Tile startingTile, Direction direction) {
        this.currentTile = startingTile;
        this.direction = direction;
        this.isAlive = true;
    }

    /**
     * This gets the ID of NPC
     * @return id
     */
    public String getId() { return id; }

    /**
     * This sets the ID of NPC
     * @param id ID of NPC
     */
    public void setId(String id) { this.id = id; }

    /**
     * Checker if NPC is alive
     * @return isAlive
     */
    @Override
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Changes if NPC is alive or not.
     * @param alive State of NPC
     */
    @Override
    public void setAliveTo(boolean alive) {isAlive = alive; }

    /**
     * This gets the direction of the NPC.
     * @return direction
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * This gets the position of the NPC.
     * @return position
     */
    @Override
    public int[] getPosition() {
        return currentTile.getPosition();
    }

    /**
     * Collect Item method for NPC to interact with items
     * @param item Specific Item
     */
    @Override
    public abstract void collectItem(Item item);

    /**
     * Checks a collision with Player.
     * @param other - character that collides with this one
     */
    @Override
    public abstract void onCollisionWith(MoveableCharacter other);

    /**
     * Move method for NPC
     */
    @Override
    public abstract void move();

    /**
     * Checks if the NPC can move based on cooldown
     * @return true if NPC can move, false otherwise
     */
    protected boolean canMove() {
        if (moveCooldownCounter >= moveCooldownTicks) {
            moveCooldownCounter = 0; // Reset counter after allowing movement
            return true;
        } else {
            moveCooldownCounter++;
            return false;
        }
    }

    /**
     * Updates the hit cooldown for the NPC
     */
    public void updateHitCooldown() {
        if (hitCooldown > 0) {
            hitCooldown--;
        }
    }

    /**
     * Resets the hit cooldown for the NPC
     */
    protected void resetHitCooldown() {
        hitCooldown = hitCooldownTicks;
    }

    /**
     * Checks if the NPC can hit based on cooldown
     * @return true if you can hit, false otherwise
     */
    protected boolean canHit() {
        return hitCooldown <= 0;
    }


    /**
     * Sets the move cooldown in seconds for the NPC
     *
     * Designed to be called in the NPCs constructor if different cooldown is needed.
     * @param moveCooldownSeconds cooldown time in seconds
     */
    protected void setMoveCooldownSeconds(double moveCooldownSeconds) {
        // This method can be implemented to adjust move cooldown if needed
        this.moveCooldownSeconds = moveCooldownSeconds;
        this.moveCooldownTicks = moveCooldownSeconds * MainApplication.TPS;
    }

    /**
     * Sets the hit cooldown in seconds for the NPC
     * Designed to be called in the NPCs constructor if different cooldown is needed.
     * @param hitCooldownSeconds cooldown time in seconds
     */
    protected void setHitCooldownSeconds(double hitCooldownSeconds) {
        this.hitCooldownSeconds = hitCooldownSeconds;
        this.hitCooldownTicks = hitCooldownSeconds * MainApplication.TPS;
    }


    /**
     * Draw function for NPCs, can show left and right images.
     * @param gc Graphics Context
     */
    public void draw(GraphicsContext gc) {
        if (direction == Direction.LEFT) {
            isFacingRight = false;
        } else if (direction == Direction.RIGHT) {
            isFacingRight = true;
        }

        Image img = getImage();
        if (img == null) return;

        int tileSize = Tile.TILE_SIZE;
        double x = currentTile.getX() * tileSize;
        double y = currentTile.getY() * tileSize;

        if (!isFacingRight) {
            gc.save();
            gc.translate(x + tileSize, y);
            gc.scale(-1, 1);
            gc.drawImage(img, 0, 0, tileSize, tileSize);
            gc.restore();
        } else {
            gc.drawImage(img, x, y, tileSize, tileSize);
        }
    }
}
