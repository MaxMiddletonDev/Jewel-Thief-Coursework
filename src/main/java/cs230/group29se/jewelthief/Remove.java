package cs230.group29se.jewelthief;

/**
 * Remove allows for items to be removed from the current level in play.
 * @author Charlie
 * @version 0.1 TODO should now be able to implement remove.
 */
public interface Remove {
    /**
     * remove will take an item out of the level and make it inaccessible
     * to the characters. needs to remove from both items/gates and the tile it is on.
     * will work based on the items co-ordinates then set that tile's item to void.
     * will be in interact for collectables and will be triggered by it or by Bomb.destroy().
     */
    default void remove(){
    }
}
