package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Items.Item;

/**
 * This interface represents any character that moves and provides a contract for which all those characters adhere.
 * to.
 * @author Baba
 * @author Max Middleton
 */
public interface MoveableCharacter {

    /**
     * Returns the position of a character
     * @return characters x,y position
     */
    int[] getPosition();

    /**
     * Tells us whether a character is still in the game or not
     * Sidenote: if a character is no longer "active" in the game, they should be removed.
     *
     * @return true, if character is still in game, false otherwise
     */
    boolean isAlive();

    /**
     * When a character has collided with another character capable of removing them from the game, it set isAlive to.
     * false, indicating their removal from the game
     *
     * @return set to true if character is still alive, false otherwise.
     */
    void setAliveTo(boolean alive);

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
    void onCollisionWith(MoveableCharacter other);

    /**
     * Handles item collection for all characters.
     * Implementation provides functionality, except for FlyingAssassin this must be left empty.
     *
     * @param item
     */
    void collectItem(Item item);


}