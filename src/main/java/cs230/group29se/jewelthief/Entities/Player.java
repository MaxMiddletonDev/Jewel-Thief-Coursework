package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Gate;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a Player Character while implementing MoveableCharacter.
 * This Player will serve as the Main Character in which the User will use.
 * @author Max Middleton, Gustas Rove
 */

public class Player implements MoveableCharacter, Protectable {
    private Tile currentTile;
    private boolean isAlive;
    private Direction direction;

    private Level level;
    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER.png").toString());

    private boolean isProtected;

    /**
     * Constructs a new Player instance.
     * @param startTile the tile where the player spawns at the start of the level.
     * @param level the level instance the player belongs to, used for checking boundaries and tile data.
     */
    public Player(Tile startTile, Level level) {
        this.currentTile = startTile;
        this.level = level;
        this.direction = Direction.UP;
        this.isAlive = true;
    }
    /**
     * Interactions with a collectable item.
     * @param item the item to interact with.
     */
    public void collectItem(Item item) {
        // If the item is collectable, record that the player collected it
        item.setCollector(this);

        // Then trigger the item's behaviour
        item.interact();
    }

    /**
     * Updates the facing direction of the player.
     * @param direction the new direction for the player to face.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Retrieves the player's current facing direction.
     * @return direction
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * Retrieves the coordinate position of the player.
     * @return an int array containing the x and y coordinates of the player's current tile.
     */
    @Override
    public int[] getPosition() {
        return currentTile.getPosition();
    }

    /**
     * Sets the player's current tile.
     * @param tile the new tile for the player to occupy.
     */
    public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }

    /**
     * Checks if the player is currently alive.
     * @return True if the player is alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAliveTo(boolean alive) {isAlive = alive; }

    /**
     * Draw Function for Player, shapes it to the tile size.
     * @param gc The GraphicsContext used for drawing.
     */
    public void draw(GraphicsContext gc) {
        int tileSize = Tile.TILE_SIZE;

        int tileX = currentTile.getX();
        int tileY = currentTile.getY();

        double x = tileX * tileSize;
        double y = tileY * tileSize;

        gc.drawImage(image, x, y, tileSize, tileSize);
    }

    /**
     * Attempts to move the player in the current facing direction.
     * The method scans the grid along the vector of the current direction (dx, dy).
     * Then if its valid it will change co-ordinates
     */
    public void move() {
        int x = currentTile.getX();
        int y = currentTile.getY();

        int dx = direction.getX();
        int dy = direction.getY();

        x += dx;
        y += dy;

        while (x >= 0 && x <= level.getWidth() && y >= 0 && y <= level.getHeight()) {
            Tile target = level.getTile(x, y);
            if (target != null && currentTile.isValidMove(target)) {
                Object occupant = target.getOccupying();
                if (occupant instanceof Gate || occupant instanceof Bomb) {
                    return;
                }
                this.currentTile = target;
                if (occupant instanceof Item item) {
                    collectItem(item);
                }
                triggerAdjacentBombs();
                return;
            }
            x += dx;
            y += dy;
        }
    }

    /**
     * Checks the 4 tiles immediately surrounding the Thief. If a bomb is found, it is activated.
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

    /**
     * This method is to handle player collision with enemy
     * which will end the game as player will die.
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        // Will create when enemies are created
    }

    /**
     * This method handles when the player gets hit by an enemy.
     * If the player is protected, the protection is removed.
     * If not protected, the player is marked as not alive.
     */
    public void getHit(){
        if(isProtected()){
            setProtected(false);
        }else{
            setAliveTo(false);
            System.out.println("Player has been hit and is no longer alive.");
        }
    }

    /**
     * Checks if the player is currently protected.
     * @return True if the player is protected, false otherwise.
     */
    @Override
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * Sets the player's protection status.
     * @param value True to protect the player, false to remove protection.
     */
    @Override
    public void setProtected(boolean value) {
        System.out.println("Player setProtected set to " + value);
        isProtected = value;
    }
}
