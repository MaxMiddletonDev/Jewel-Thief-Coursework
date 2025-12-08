package cs230.group29se.jewelthief.Game;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundManager {
    private static final double VOLUME = 0.1;
    private static final String BASE_PATH = "/cs230/group29se/jewelthief/Audios/";

    public static final AudioClip COLLECT = load("Collect.mp3");
    public static final AudioClip BOMB = load("Bomb.mp3");
    public static final AudioClip DEATH = load("Death.mp3");
    public static final AudioClip GAME = load("Game.mp3");
    public static final AudioClip MAINMENU = load("MainMenu.mp3");
    public static final AudioClip PAUSE = load("Pause.mp3");
    public static final AudioClip START = load("Start.mp3");
    public static final AudioClip WIN = load("Win.mp3");

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

    public static void playCollect() {
        if (COLLECT != null) {
            COLLECT.play();
        }
    }

    public static void playBomb() {
        if (BOMB != null) {
            BOMB.play();
        }
    }

    public static void playDeath() {
        if (DEATH != null) {
            DEATH.play();
        }
    }

    public static void playGame() {
        if (GAME != null) {
            GAME.stop();
            MAINMENU.stop();
            GAME.play(VOLUME);
        }
    }

    public static void playMainMenu() {
        if (MAINMENU != null) {
            MAINMENU.stop();
            GAME.stop();
            MAINMENU.play(VOLUME);
        }
    }
    public static void playPause() {
        if (PAUSE != null) {
            PAUSE.play();
        }
    }
    public static void playStart() {
        if (START != null) {
            START.play(VOLUME);
        }
    }
    public static void playWin() {
        if (WIN != null) {
            WIN.play();
        }
    }
}