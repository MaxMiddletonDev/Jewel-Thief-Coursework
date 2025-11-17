package cs230.group29se.jewelthief;

/**
 * Remove allows for items to be removed from the current level in play.
 * @author Charlie
 * @version 0.1
 */
public interface Remove {
    /**
     * remove will take an item out of the level and make it inaccessible
     * to the characters.
     * remove will be implemented here once level and tile are implemented.
     * will work based on the items co-ordinates then set that tile's item to void.
     * will be in interact for most items and will be triggered by it or by Bomb.destroy().
     */
    void remove();
}
