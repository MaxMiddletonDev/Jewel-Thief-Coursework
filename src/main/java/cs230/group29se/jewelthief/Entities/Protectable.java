package cs230.group29se.jewelthief.Entities;

/**
 * Interface representing a Protectable entity in the Jewel Thief game.
 * A Protectable entity can be protected or unprotected,
 * and its protection status
 * can be queried or modified.
 *
 * A protected entity shouldn't die.
 *
 * @author Gustas Rove
 */
public interface Protectable {

    /**
     * Checks if the entity is currently protected.
     *
     * @return true if the entity is protected, false otherwise.
     */
    boolean isProtected();

    /**
     * Sets the protection status of the entity.
     *
     * @param value true to protect the entity, false to remove protection.
     */
    void setProtected(boolean value);
}
