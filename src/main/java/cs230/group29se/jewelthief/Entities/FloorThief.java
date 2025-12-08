package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Colour;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Collectable;
import cs230.group29se.jewelthief.Items.Item;
import cs230.group29se.jewelthief.Items.Lever;
import javafx.scene.image.Image;

/**
 * An NPC that moves following the left hand edge of their "assigned" colour,
 * they collect items and upon coming in
 * contact with a player they cause the player to lose.
 * If they come in contact with a flying assassin however (occupy)
 * the same tile, then they are removed from the game.
 *
 * @author Baba
 */
public class FloorThief extends NonPlayableCharacter {
    public static final float MOVE_COOLDOWN_SECONDS = 0.5f;
    public static final int HIT_COOLDOWN_SECONDS = 2;
    public static final int[][] OFFSETS = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private Colour assignedColour;
    private Level level;

    private final Image image = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Entities/NPCs/FLOORTHIEF.png")
            .toString());

    /**
     * Creates a new Floor Thief instance.
     *
     * @param assignedColour The colour the Thief starts on
     * @param startingTile The tile the Thief starts on
     * @param direction The direction the thief starts
     * @param level The level the Thief will be instantiated on
     * @param id Thief's ID
     */
    public FloorThief(Colour assignedColour, Tile startingTile, Direction direction, Level level, String id) {
        super(startingTile, direction);
        this.id = id;
        this.assignedColour = assignedColour;
        this.level = level;

        setMoveCooldownSeconds(MOVE_COOLDOWN_SECONDS); // Floor Thief moves every 1 second
        setHitCooldownSeconds(HIT_COOLDOWN_SECONDS); // Floor Thief can hit every 2 seconds
    }

    /**
     * Fetches a Floor Thief's assigned colour.
     * @return assignedColour
     */
    public Colour getAssignedColour() {
        return assignedColour;
    }

    /**
     * When a FloorThief collides/comes in contact with a player,
     * the player is removed from the game,
     * if it occupies the same tile as a flying assassin,
     * the Floor Thief is removed from the game.
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (!isAlive) {
            return;
        }

        if (other instanceof FlyingAssassin) {
            this.isAlive = false;
        } else if (other instanceof Player) {
            if(canHit()){
                ((Player) other).getHit();
                resetHitCooldown();
            }

        }
    }

    /**
     * Collects Items and calls relevant logic
     * @param item Specific Item
     */
    @Override
    public void collectItem(Item item) {
        if (item == null) {
            return;
        }

        collectedItems.add(item);

        if (item instanceof Collectable collectable) {
            collectable.setCollector(this);
        }
        item.interact();
    }

    /**
     * Handles Floor thief movements on the tiles and validates it's movements.
     */
    @Override
    public void move(){
        if (!isAlive) {
            return;
        }

        //Only move once every X seconds
        if(!canMove()){
            return;
        }

        Direction nextDirection = findNextDirection();

        if (nextDirection != null) {
            moveIn(nextDirection);
            direction = nextDirection; //change direction facing to nextDirection
        }
    }


    /**
     * Checks the 4 tiles immediately surrounding the player.
     * If a bomb is found, it is activated.
     */
    private void triggerAdjacentBombs() {
        int currentX = currentTile.getX();
        int currentY = currentTile.getY();

        // These are the adjacent tiles

        for (int[] offset : OFFSETS) {
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
     * Gets specific enemies sprite
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * finds what direction the FloorThief can move in
     * @return direction to move towards
     */
    public Direction findNextDirection() {
        Direction[] possibleDirections = getPossibleDirections();

        for (Direction direction : possibleDirections) {
            if (isValidColorTile(direction)) {
                return direction;
            }
        }
        return null;
    }

    /**
     * Provides the possible directions a floor thief can move in, keeping in mind that the LHS has greater priority
     * @return possible directions to move in with LHS rule in mind
     */
    public Direction[] getPossibleDirections() {
        if (direction == Direction.UP) {
            return new Direction[]{Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};
        } else if (direction == Direction.DOWN) {
            return new Direction[]{Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP};
        } else if (direction == Direction.LEFT) {
            return new Direction[]{Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT};
        } else if (direction == Direction.RIGHT) {
            return new Direction[]{Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
        } else {
            return new Direction[0];
        }
    }

    /**
     * checks if a tile a thief wants to move in is 1) a valid tile to actually move to i.e. not an edge, and 2) shares
     * a common colour with the thief's assigned colour, 3) tile does not contain a bomb or unpassable gate
     * @param moveDirection - direction to move towards
     * @return true if conditions are met, false otherwise
     */
    public boolean isValidColorTile(Direction moveDirection) {
        int[] currentPos = getPosition();
        int thiefTargetX = currentPos[0];
        int thiefTargetY = currentPos[1];

        if (moveDirection == Direction.UP) {
            thiefTargetY--;
        } else if (moveDirection == Direction.DOWN) {
            thiefTargetY++;
        } else if (moveDirection == Direction.LEFT) {
            thiefTargetX--;
        } else if (moveDirection == Direction.RIGHT) {
            thiefTargetX++;
        }

        if (!isValidMove(thiefTargetX, thiefTargetY)) {
            return false;
        }

        Tile targetTile = level.getTile(thiefTargetX, thiefTargetY);
        if (targetTile == null) {
            return false;
        }

        if (!targetTile.containsColour(assignedColour)) {
            return false;
        }

        if (targetTile.hasBomb()) {
            return false;
        }

        if (targetTile.hasGate()) {
            return hasLever(targetTile.getGateColour());
        }
        return true;
    }

    /**
     * Checks if an NPC has the appropriate lever colour
     * @param colour
     * @return
     */
    public boolean hasLever(Colour colour) {
        for (Item item : collectedItems) {
            if (item instanceof Lever && ((Lever) item).getColour() == colour) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes the move to the nextDirection
     * @param direction to move towards
     */
    public void moveIn(Direction direction) {
        int[] currentPos = getPosition();
        int thiefTargetX = currentPos[0];
        int thiefTargetY = currentPos[1];

        if (direction == Direction.UP) {
            thiefTargetY--;
        } else if (direction == Direction.DOWN) {
            thiefTargetY++;
        } else if (direction == Direction.LEFT) {
            thiefTargetX--;
        } else if (direction == Direction.RIGHT) {
            thiefTargetX++;
        }

        Tile targetTile = level.getTile(thiefTargetX, thiefTargetY);
        if (targetTile != null) {
            currentTile = targetTile;
            triggerAdjacentBombs();
        }
    }

    /**
     * Checks if a floor thief is at an edge or not
     */
    public boolean isValidMove(int targetX, int targetY) {
        if (targetX >= 0 && targetY >= 0 && targetX < level.getWidth() && targetY < level.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the FloorThief is protected or not
     * @return true if protected, false otherwise
     */
    @Override
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * Sets the protection status of the FloorThief
     * @param value - true to set as protected, false otherwise
     */
    @Override
    public void setProtected(boolean value) {
        isProtected = value;
    }

    /**
     * Gets the colour.
     * @return assignedColour
     */
    public Colour getColour() {
        return assignedColour;
    }
}
