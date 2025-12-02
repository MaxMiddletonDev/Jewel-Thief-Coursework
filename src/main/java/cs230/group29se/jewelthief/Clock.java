package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Allows for a clock to be created which changes the time left in a level.
 * @author Charlie, Hamza
 *
 * @version 1 - IMPLEMENTED interact: Player adds time, Thief removes time.
 */
public class Clock extends Destroyable {

    /**
     * The amount of time (in seconds) to change when collected.
     */
    private static final int TIME_CHANGE = 5;

    private final Image image = new Image(
            getClass().getResource("/cs230/group29se/jewelthief/Images/CLOCK.png").toString()
    );

    public Clock(final int x, final int y) {
        super(x, y);
    }

    /**
     * Changes the time left in the level based on who collected the clock:
     *  - If Player collects  → add TIME_CHANGE seconds.
     *  - If Thief collects   → remove TIME_CHANGE seconds.
     * Then removes the clock from play.
     */
    @Override
    public void interact() {
        // Get current level from the game manager
        Level currentLevel = GameManager.getCurrentLevel();

        if (currentLevel != null && collector != null) {
            if (collector instanceof Player) {
                // Player collected → increase time
                currentLevel.addTime(TIME_CHANGE);
            } else if (collector instanceof NonPlayableCharacter) {
                // Thief (NPC) collected → decrease time
                currentLevel.removeTime(TIME_CHANGE);
            }
        }

        // Remove the clock from the level so it can't be reused
        remove(this);
    }

    /**
     * Draws the clock at its tile position.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX() * 64, getY() * 64);
    }
}
