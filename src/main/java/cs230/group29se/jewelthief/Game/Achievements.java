package cs230.group29se.jewelthief.Game;

public enum Achievements {
    SPEEDSTER("Speedster", "Completing a game under 30 seconds", "/cs230/group29se/jewelthief/Images/Achievements/Speedster.png"),
    SURVIVOR("Survivor", "Survive a hit from an enemy", "/cs230/group29se/jewelthief/Images/Achievements/Survivor.png"),
    DEMO_MAN("Demolition Expert", "Set off a bomb", "/cs230/group29se/jewelthief/Images/Achievements/Demoman.png"),
    MONEY_MAN("Money Man", "Collected your first loot", "/cs230/group29se/jewelthief/Images/Achievements/MoneyMan.png"),
    PRO("Professional", "Complete a level", "/cs230/group29se/jewelthief/Images/Achievements/Pro.png"),
    TANK("Tank", "Picked up shield", "/cs230/group29se/jewelthief/Images/Achievements/Tank.png");

    private final String name;
    private final String description;
    private final String iconPath;

    Achievements(String name, String description, String iconPath) {
        this.name = name;
        this.description = description;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getIconPath() {
        return iconPath;
    }
}
