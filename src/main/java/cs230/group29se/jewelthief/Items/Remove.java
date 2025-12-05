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
     * remove will take an item out of the level and make it inaccessible
     * to the characters. needs to remove from both items/gates and the tile it is on.
     * will work based on the items co-ordinates then set that tile's item to void.
     * will be in interact for collectables and will be triggered by it or by Bomb.destroy().
     */
    default void remove(Collectable collectable){
        Level level = GameManager.getCurrentLevel();
        level.removeItem(collectable);
        level.getTile(collectable.getX(),collectable.getY()).setOccupying(null);
    }

    default void remove(Gate gate){
        Level level = GameManager.getCurrentLevel();
        level.removeGate(gate);
        level.getTile(gate.getX(),gate.getY()).setOccupying(null);
    }
}
