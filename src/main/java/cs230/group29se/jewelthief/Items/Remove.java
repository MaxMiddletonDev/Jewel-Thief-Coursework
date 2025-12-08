package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;

/**
 * Remove allows for items to be removed from the current level in play.
 * @author Charlie
 * @version 0.1
 */
public interface Remove {
    /**
     * Removes the selected item from the level and tile it is on.
     * @param collectable the collectable item to be removed.
     */
    default void remove(final Collectable collectable) {
        Level level = GameManager.getCurrentLevel();
        level.removeItem(collectable);
        level.getTile(collectable.getX(),
                collectable.getY()).setOccupying(null);
    }

    /**
     * Removes the selected gate from the level and tile it is on.
     * @param gate the gate to remove from the level.
     */
    default void remove(final Gate gate) {
        Level level = GameManager.getCurrentLevel();
        level.removeGate(gate);
        level.getTile(gate.getX(), gate.getY()).setOccupying(null);
    }
}
