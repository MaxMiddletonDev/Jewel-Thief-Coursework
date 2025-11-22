package cs230.group29se.jewelthief.Persistence.Storage;

import cs230.group29se.jewelthief.Persistence.Profile.*;
import java.nio.file.Path;

/**
 * Simple demo showcasing the functionality of PersistenceManager, without any JavaFX/UI implementation.
 * @author Iyaad
 * @version 1.0
 */
public class MainHeadlessDemo {
    public static void main(String[] args) {
        // 1) Point storage to a project-local folder to inspect files easily
        var store = new FileStore(Path.of("data/jewelthief"));
        var json = new JsonSerializer();
        var pm = new PersistenceManager(store, json);

        // 2) Seed minimal DTOs
        pm.setActiveProfileName("Amsyar");
        pm.setActiveLevelId("3");
        pm.setActiveRunScore(1200);

        var profile = new ProfileData();
        profile.setProfileName("Amsyar"); // uncomment when getters/setters are in your class
        pm.setCachedProfile(profile);

        var save = new SaveData();
        save.setProfileName("Amsyar");
        save.setLevelId("3");
        save.setPlayerState(new Object[]{128.0, 64.0, 100, "hasKey", true});
        save.setElapsedSeconds(900);
        pm.setCachedSave(save);

        // 3) Run persistence code flow
        pm.saveProfile();
        pm.saveGame();
        pm.submitScore();

        // 4) Load back and print a couple of things
        var loadedProfile = pm.loadProfile();
        var loadedSave = pm.getSaveData();
        var table = pm.loadHighScores();

        System.out.println("Profiles: " + pm.listProfiles());
        System.out.println("Loaded profile: " + (loadedProfile != null));
        System.out.println("Loaded save: " + (loadedSave != null));
        System.out.println("Highscores size: " + table.getEntries().size());

        // 5) Where to look:
        //   data/jewelthief/profiles/*.json
        //   data/jewelthief/saves/<profileName>/level-<levelId>.json
        //   data/jewelthief/scores/highscores.json

/*
            Expected JSON outputs (after saveProfile, saveGame, submitScore):

            1) profiles/<profileName>.json
            {
              "profileName": "<PROFILE_NAME>",      // e.g., "Amsyar"
              "maxUnlockedLvl": <INT>               // e.g., 3
            }

            2) saves/<profileName>/level-<levelId>.json
            {
              "profileName": "<PROFILE_NAME>",      // e.g., "Amsyar"
              "levelId": "<LEVEL_ID>",              // e.g., "3"
              "playerState": [ ... ],               // Object[] seeded, e.g. [128.0, 64.0, 100, "hasKey", true]
              "elapsedSeconds": <INT>,              // e.g., 900
              "npcStates": { ... },                 // map seeded, e.g. {"floorThief#1": {"x": 50, "y": 16}}
              "gates": { ... },                     // e.g., {"gate#A": "open"}
              "items": { ... }                      // e.g., {"gem#12": "collected"}
            }

            3) scores/highscores.json
            {
              "globalRanking": [
                {
                  "profileName": "<PROFILE_NAME>",  // e.g., "Amsyar"
                  "score": <INT>,                   // e.g., 1200 (pm.setPendingRunScore(...))
                  "timestamp": "<ISO-8601>"         // generated at runtime, e.g., "2025-11-22T14:03:05Z"
                }
              ],
              "perLevelBest": {
                "<LEVEL_ID>": [
                  {
                    "profileName": "<PROFILE_NAME>",
                    "score": <INT>,
                    "timestamp": "<ISO-8601>"
                  }
                ]
              }
            }

            /*
            Notes:
            - Base directory is set in FileStore (e.g., data/jewelthief).
            - Timestamps will differ each run ([Instant.now]).
            - If you don’t set some fields in the DTOs, they may be null or omitted depending on your getters/setters and annotations.
            - Change pm.setActiveProfileName("..."), pm.setActiveLevelId("..."), pm.setPendingRunScore(...), and pm.setCachedProfile/Save(...) to control outputs.
*/

    }
}