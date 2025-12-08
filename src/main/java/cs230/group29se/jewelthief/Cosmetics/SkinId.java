package cs230.group29se.jewelthief.Cosmetics;

/**
 * enum listing the only available skins
 * @author Gustas Rove
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
     * converts the id into a string
     * @param selectedSkinId the id to convert into a string
     * @return the string equivalent of the skin id
     */
    public static SkinId fromString(String selectedSkinId) {
        for (SkinId skinId : SkinId.values()) {
            if (skinId.name().equals(selectedSkinId)) {
                return skinId;
            }
        }
        return DEFAULT_GUY; // Return a default value if no match is found
    }
}
