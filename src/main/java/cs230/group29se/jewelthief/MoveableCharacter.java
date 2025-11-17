package cs230.group29se.jewelthief;

/**
 * This interface represents any character that moves and provides a contract for which all those characters adhere
 * to.
 * @author Baba
 */
public interface MoveableCharacter {

    /**
     * Tells us whether a character is still in the game or not
     * Sidenote: if a character is no longer "active" in the game, they should be removed
     *
     * @return true, if character is still in game, false otherwise
     */
    boolean isAlive();

    /**
     * Tells us the characters current facing direction
     *
     * @return the current direction towards which a character is facing
     */
    Direction getDirection();

    /**
     * Provides movement behaviour for the character using movement logic in specification.
     */
    void move();

    /**
     * Handles functionality for when a character collides with another character.
     * Implementation must specify what happens upon collision with characters, that is, player death when colliding
     * with SmartThief/FloorThief/FlyingAssassin, Thief death when crossing path with FlyingAssassin etc.
     *
     * @param other - character that collides with this one
     */
    void onCollision(MoveableCharacter other);

    /**
     * Handles item collection for all characters.
     * Implementation provides functionality, except for FlyingAssassin this must be left empty.
     *
     * @param item
     */
    void collectItem(Item item);

}