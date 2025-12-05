package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Colour;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;

public class SmartThief extends FloorThief{
    public SmartThief(Colour assignedColour, Tile startingTile, Direction direction, Level level) {
        super(assignedColour, startingTile, direction, level);
    }
}
