package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * An NPC that moves in a straight line, either horizontally or vertically, upon contact (when occupying the same
 * tile as a player/thief) it takes out the other moveable character, it doesn't take loot, just moves, and takes
 * out other moveable characters
 *
 * @author Baba
 */
public class FlyingAssasin extends NonPlayableCharacter {

    private Level level;
    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/FLYINGASSASSIN.png").toString());

    /**
     * Constructor for creating new instance of FlyingAssasin.
     */
    public FlyingAssasin(Tile startingTile, Direction direction, Level level, String id) {
        super(startingTile, direction);
        this.id = id;
        this.level = level;
        setMoveCooldownSeconds(0.3F); // Flying Assasin moves every 2 seconds
        setHitCooldownSeconds(1.5); // Flying Assasin can hit every 2 seconds
    }

    /**
     * kept empty because a Flying Assasin does not collect items.
     *
     * @param item that would be collected.
     */
    @Override
    public void collectItem(Item item) {}

    /**
     * if a flying assassin occupies the same tile as another character, that character is taken off the game, if it's
     * a player, the player loses and is no longer in game.
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (other instanceof Player) {
            if(canHit()){
                ((Player) other).getHit();
                resetHitCooldown();
                System.out.println("Flying Assasin hit the Player!");
            }else{
                System.out.println("Flying Assasin is on hit cooldown: " + hitCooldown + " ticks remaining.");
            }
        }
        else if (other instanceof NonPlayableCharacter && !(other instanceof FlyingAssasin)) {
            other.setAliveTo(false);
        }
    }

    /**
     * moves in a straight line, either horizontally or vertically, and when at the edge, takes a 180-degree turn and
     * moves in the opposite direction.
     */
    @Override
    public void move() {
        if (!isAlive) {
            return;
        }

        //Only move once every X seconds
        if(!canMove()){
            return;
        }

        int[] currentPosition = getPosition();
        int targetX = currentPosition[0];
        int targetY = currentPosition[1];

        if (direction == Direction.UP) {
            targetY--;
        } else if (direction == Direction.DOWN) {
            targetY++;
        } else if (direction == Direction.LEFT) {
            targetX--;
        } else if (direction == Direction.RIGHT) {
            targetX++;
        }

        if (isValidMove(targetX, targetY)) {
            Tile targetTile = level.getTile(targetX, targetY);
            if (targetTile != null) {
                currentTile = targetTile;
            }
        } else {
            reverseDirection();
        }


    }

    /**
     * Checks if a flying assassin is at an edge or not
     */
    public boolean isValidMove(int targetX, int targetY) {
        if (targetX >= 0 && targetY >= 0 && targetX < level.getWidth() && targetY < level.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reverses direction of Flying assassin
     */
    public void reverseDirection() {
        if (direction == Direction.UP) {
            direction = Direction.DOWN;
        } else if (direction == Direction.DOWN) {
            direction = Direction.UP;
        } else if (direction == Direction.LEFT) {
            direction = Direction.RIGHT;
        } else if (direction == Direction.RIGHT) {
            direction = Direction.LEFT;
        }
    }

    /**
     * Draw Function for Flying Assassin, shapes it to the tile size.
     */
    public void draw(GraphicsContext gc) {

        int tileSize = Tile.TILE_SIZE;

        double x = currentTile.getX() * tileSize;
        double y = currentTile.getY() * tileSize;

        gc.drawImage(image, x, y, tileSize, tileSize);
    }

    @Override
    public boolean isProtected() {
        return isProtected;
    }

    @Override
    public void setProtected(boolean value) {
        isProtected = value;
    }
}
