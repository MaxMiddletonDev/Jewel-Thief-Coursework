package cs230.group29se.jewelthief.Game;

public enum Achievements {
    SPEEDSTER("Speedster", "Completing a game under 30 seconds", "/cs230/group29se/jewelthief/Images/Achievements/Speedster.png"),
    SURVIVOR("Survivor", "Survive a hit from an enemy", "/cs230/group29se/jewelthief/Images/Achievements/Survivor.png"),
    DEMO_MAN("Demolition Expert", "Set off 2 bombs", "/cs230/group29se/jewelthief/Images/Achievements/Demoman.png"),
    MONEY_MAN("Money Man", "Collected a total of 5 loot items", "/cs230/group29se/jewelthief/Images/Achievements/MoneyMan.png"),
    PRO("Professional", "Completed 3 levels", "/cs230/group29se/jewelthief/Images/Achievements/Pro.png"),
    TANK("Tank", "Picked up 3 shields", "/cs230/group29se/jewelthief/Images/Achievements/Tank.png");


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
