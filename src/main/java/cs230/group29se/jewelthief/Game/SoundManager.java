package cs230.group29se.jewelthief.Game;

import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 * Sound Manager to handle all sound effects and music within the game.
 * This centralizes all audio loading and playback logic.
 *
 * @author Max Middleton
 */
public class SoundManager {
    private static final double DEFAULT_VOLUME = 0.1;
    private static final String AUDIO_BASE_PATH = "/cs230/group29se/jewelthief/Audios/";

    // Filenames for audio clipS
    private static final String FILE_COLLECT = "Collect.mp3";
    private static final String FILE_BOMB = "Bomb.mp3";
    private static final String FILE_DEATH = "Death.mp3";
    private static final String FILE_GAME = "Game.mp3";
    private static final String FILE_MAINMENU = "MainMenu.mp3";
    private static final String FILE_PAUSE = "Pause.mp3";
    private static final String FILE_START = "Start.mp3";
    private static final String FILE_WIN = "Win.mp3";

    // AudioClip instances
    public static final AudioClip COLLECT = load(FILE_COLLECT);
    public static final AudioClip BOMB = load(FILE_BOMB);
    public static final AudioClip DEATH = load(FILE_DEATH);
    public static final AudioClip GAME = load(FILE_GAME);
    public static final AudioClip MAINMENU = load(FILE_MAINMENU);
    public static final AudioClip PAUSE = load(FILE_PAUSE);
    public static final AudioClip START = load(FILE_START);
    public static final AudioClip WIN = load(FILE_WIN);

    public static final String MSG_MISSING = "Missing sound file: ";
    public static final String MSG_ERROR_LOADING = "Error loading %s: %s";
    /**
     * Loads an AudioClip from resources.
     *
     * @param filename the name of the audio file to load
     * @return the AudioClip, or null if loading fails
     */
    private static AudioClip load(String filename) {
        try {
            URL resource = SoundManager.class.getResource(AUDIO_BASE_PATH + filename);
            if (resource == null) {
                System.err.println(MSG_MISSING + filename);
                return null;
            }
            return new AudioClip(resource.toString());
        } catch (Exception e) {
            System.err.println(String.format(MSG_ERROR_LOADING, filename, e.getMessage()));
            return null;
        }
    }

    // -------------------------------
    // Playback Methods (No magic nums)
    // -------------------------------

    public static void playCollect() {
        if (COLLECT != null) COLLECT.play();
    }

    public static void playBomb() {
        if (BOMB != null) BOMB.play();
    }

    public static void playDeath() {
        if (DEATH != null) DEATH.play();
    }

    public static void playGame() {
        if (GAME != null) {
            GAME.stop();
            MAINMENU.stop();
            GAME.play(DEFAULT_VOLUME);
        }
    }

    public static void playMainMenu() {
        if (MAINMENU != null) {
            MAINMENU.stop();
            GAME.stop();
            MAINMENU.play(DEFAULT_VOLUME);
        }
    }

    public static void playPause() {
        if (PAUSE != null) PAUSE.play();
    }

    public static void playStart() {
        if (START != null) START.play(DEFAULT_VOLUME);
    }

    public static void playWin() {
        if (WIN != null) WIN.play();
    }
}
