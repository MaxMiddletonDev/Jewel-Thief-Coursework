package cs230.group29se.jewelthief.Cosmetics;

/**
 * An enum representing different skin IDs for the game characters.
 * @author Gustas
 * @version 1.0
 */
public enum SkinId {
    DEFAULT_GUY,
    SPEEDSTER,
    SURVIVOR,
    DEMO_MAN,
    MONEY_MAN,
    PRO,
    TANK;

    /**
     * Converts a string to the corresponding SkinId enum value.
     * @param selectedSkinId The string representation of the skin ID.
     * @return The corresponding SkinId enum value,
     * or DEFAULT_GUY if no match is found.
     */
    public static SkinId fromString(final String selectedSkinId) {
        for (SkinId skinId : SkinId.values()) {
            if (skinId.name().equals(selectedSkinId)) {
                return skinId;
            }
        }
        return DEFAULT_GUY; // Return a default value if no match is found
    }
}
