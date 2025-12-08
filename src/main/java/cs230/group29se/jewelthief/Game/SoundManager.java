package cs230.group29se.jewelthief.Game;

import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 * Sound Manager to handle all the Sound Effects and Music within the Game
 * This allows for sound to be played in specific points where needed.
 *
 * @author Max Middleton
 */
public class SoundManager {
    private static final double VOLUME = 0.1;
    private static final String BASE_PATH = "/cs230/group29se/jewelthief/Audios/";

    // End File path for Audios
    public static final AudioClip COLLECT = load("Collect.mp3");
    public static final AudioClip BOMB = load("Bomb.mp3");
    public static final AudioClip DEATH = load("Death.mp3");
    public static final AudioClip GAME = load("Game.mp3");
    public static final AudioClip MAINMENU = load("MainMenu.mp3");
    public static final AudioClip PAUSE = load("Pause.mp3");
    public static final AudioClip START = load("Start.mp3");
    public static final AudioClip WIN = load("Win.mp3");

    /**
     * Load Method Concatenates the base path and filename.
     * Has a catch for incorrect files
     * @param filename Audio Files to be added
     * @return null;
     */
    private static AudioClip load(String filename) {
        try {
            URL resource = SoundManager.class.getResource(BASE_PATH + filename);
            if (resource == null) {
                System.out.println("Missing sound file: " + filename);
                return null;
            }
            return new AudioClip(resource.toString());
        } catch (Exception e) {
            System.out.println("Error loading " + filename + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Plays a sound that represents Collect.
     */
    public static void playCollect() {
        if (COLLECT != null) {
            COLLECT.play();
        }
    }

    /**
     * Plays a sound that represents Bomb.
     */
    public static void playBomb() {
        if (BOMB != null) {
            BOMB.play();
        }
    }

    /**
     * Plays a sound that represents Death.
     */
    public static void playDeath() {
        if (DEATH != null) {
            DEATH.play();
        }
    }

    /**
     * Plays a sound that represents Game.
     */
    public static void playGame() {
        if (GAME != null) {
            GAME.stop();
            MAINMENU.stop();
            GAME.play(VOLUME);
        }
    }

    /**
     * Plays a sound that represents Main Menu.
     */
    public static void playMainMenu() {
        if (MAINMENU != null) {
            MAINMENU.stop();
            GAME.stop();
            MAINMENU.play(VOLUME);
        }
    }

    /**
     * Plays a sound that represents Pause.
     */
    public static void playPause() {
        if (PAUSE != null) {
            PAUSE.play();
        }
    }

    /**
     * Plays a sound that represents Start.
     */
    public static void playStart() {
        if (START != null) {
            START.play(VOLUME);
        }
    }

    /**
     * Plays a sound that represents Win.
     */
    public static void playWin() {
        if (WIN != null) {
            WIN.play();
        }
    }
}