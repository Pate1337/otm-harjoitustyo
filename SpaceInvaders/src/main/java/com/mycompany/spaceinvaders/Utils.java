package com.mycompany.spaceinvaders;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Utils {
    private static boolean muted = false;
    private static MediaPlayer alarm = null;
    private static boolean soundsPaused = false;
    private static AudioClip menuSound;
    private static ArrayList<Image> explosionImages;
    private static ArrayList<Image> hearts;
    private static AudioClip laser;
    private static String explosion;
    
    public static void playMenuSound(String fileName) {
        if (!muted) {
            menuSound.play();
        }
    }
    public static void initSounds() {
        menuSound = new AudioClip(Utils.class.getResource("/resources/sounds/menusound.wav").toString());
        explosionImages = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String name = "/resources/images/explosion" + i + ".png";
            explosionImages.add(new Image(Utils.class.getResource(name).toString()));
            explosionImages.add(new Image(Utils.class.getResource(name).toString()));
        }
    
        hearts = new ArrayList<>();
        hearts.add(new Image(Utils.class.getResource("/resources/images/heart1.png").toString()));
        hearts.add(new Image(Utils.class.getResource("/resources/images/heart2.png").toString()));
        
        laser = new AudioClip(Utils.class.getResource("/resources/sounds/laser.wav").toString());
        explosion = Utils.class.getResource("/resources/sounds/explosion.wav").toString();
    }
    public static void playExplosion() {
        if (!muted) {
            AudioClip explosionClip = new AudioClip(explosion);
            explosionClip.play();
        }
    }
    public static void playLaser() {
        if (!muted) {
            laser.play();
        }
    }
    public static void playOne() {
        AudioClip clip = new AudioClip(Utils.class.getResource("/resources/sounds/one.wav").toString());
        if (!muted) {
            clip.play();
        }
    }
    public static void playTwo() {
        AudioClip clip = new AudioClip(Utils.class.getResource("/resources/sounds/two.wav").toString());
        if (!muted) {
            clip.play();
        }
    }
    public static void playThree() {
        AudioClip clip = new AudioClip(Utils.class.getResource("/resources/sounds/three.wav").toString());
        if (!muted) {
            clip.play();
        }
    }
    public static void playZero() {
        AudioClip clip = new AudioClip(Utils.class.getResource("/resources/sounds/zero.wav").toString());
        if (!muted) {
            clip.play();
        }
    }
    public static void playMotion() {
        AudioClip clip = new AudioClip(Utils.class.getResource("/resources/sounds/motion.wav").toString());
        if (!muted) {
            clip.play();
        }
    }
    public static void playAlarm() {
        Media sound = new Media(Utils.class.getResource("/resources/sounds/alarm.wav").toString());
        alarm = new MediaPlayer(sound);
        if (!muted) {
            alarm.play();
        }
    }
    public static void pauseSounds() {
        if (alarm != null) {
            alarm.pause();
            soundsPaused = true;
        }
    }
    public static void continueSounds() {
        if (alarm != null) {
            if (!muted) {
                alarm.play();
            }
            soundsPaused = false;
        }
    }
    public static void mute() {
        muted = true;
    }
    
    public static void unMute() {
        muted = false;
    }
    
    public static boolean getSoundState() {
        return muted;
    }
    public static boolean getSoundsPaused() {
        return soundsPaused;
    }
    public static ArrayList<Image> getExplosionImages() {
        return explosionImages;
    }
    public static ArrayList<Image> getHearts() {
        return hearts;
    }
}
