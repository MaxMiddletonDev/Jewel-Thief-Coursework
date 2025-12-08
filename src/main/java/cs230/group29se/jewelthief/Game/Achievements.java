package cs230.group29se.jewelthief.Game;

/**
 * Enum representing the achievements in the game.
 * Each achievement has a name, description, and an associated icon path.
 *
 * @author Max Middleton
 */
public enum Achievements {
    /**
     * Achievement for completing a game under 30 seconds.
     */
    SPEEDSTER("Speedster",
            "Completing a game under 30 seconds",
            "/cs230/group29se/jewelthief/Images/Achievements/Speedster.png"),

    /**
     * Achievement for surviving a hit from an enemy.
     */
    SURVIVOR("Survivor",
            "Survive a hit from an enemy",
            "/cs230/group29se/jewelthief/Images/Achievements/Survivor.png"),

    /**
     * Achievement for setting off a bomb.
     */
    DEMO_MAN("Demolition Expert",
            "Set off a bomb",
            "/cs230/group29se/jewelthief/Images/Achievements/Demoman.png"),

    /**
     * Achievement for collecting the first loot.
     */
    MONEY_MAN("Money Man",
            "Collected your first loot",
            "/cs230/group29se/jewelthief/Images/Achievements/MoneyMan.png"),

    /**
     * Achievement for completing a level.
     */
    PRO("Professional",
            "Complete a level",
            "/cs230/group29se/jewelthief/Images/Achievements/Pro.png"),

    /**
     * Achievement for picking up a shield.
     */
    TANK("Tank",
            "Picked up shield",
            "/cs230/group29se/jewelthief/Images/Achievements/Tank.png");

    private final String name; // The name of the achievement
    private final String description; // The description of the achievement
    private final String iconPath; // The file path to the achievement's icon

    /**
     * Constructor for the Achievements enum.
     *
     * @param name        The name of the achievement.
     * @param description The description of the achievement.
     * @param iconPath    The file path to the achievement's icon.
     */
    Achievements(final String name,
                 final String description,
                 final String iconPath) {
        this.name = name;
        this.description = description;
        this.iconPath = iconPath;
    }

    /**
     * Gets the name of the achievement.
     *
     * @return The name of the achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the achievement.
     *
     * @return The description of the achievement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the file path to the achievement's icon.
     *
     * @return The file path to the achievement's icon.
     */
    public String getIconPath() {
        return iconPath;
    }
}
