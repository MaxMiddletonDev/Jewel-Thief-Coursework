package cs230.group29se.jewelthief.Cosmetics;

public enum SkinId {
    DEFAULT_GUY,
    SPEED_GUY,
    CAT_CAT;

    public static SkinId fromString(String selectedSkinId) {
        for (SkinId skinId : SkinId.values()) {
            if (skinId.name().equals(selectedSkinId)) {
                return skinId;
            }
        }
        return DEFAULT_GUY; // Return a default value if no match is found
    }
}
