/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.spaceinvaders;

import java.io.File;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
//import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

/**
 *
 * @author paavo
 */
public class Utils {
    private static boolean muted = false;
    private static MediaPlayer alarm = null;
    private static boolean soundsPaused = false;
    private static AudioClip menuSound;
    
    public static void playMenuSound(String fileName) {
//        Media sound = new Media(new File(fileName).toURI().toString());
//        MediaPlayer soundPlayer = new MediaPlayer(sound);
//        soundPlayer.setVolume(0.0);
//        soundPlayer.play();
        
        
        //Audioclippi PALJON parempi!!
//        try {
//            AudioClip clip = new AudioClip(new File(fileName).toURI().toString());
//            if (!muted) {
//                clip.play();
//            }
//        } catch (Exception e) {
//            System.out.println("Ei voi toistaa");
//        }
        if (!muted) {
            menuSound.play();
        }
    }
    public static void initSounds() {
        menuSound = new AudioClip(Utils.class.getResource("/resources/sounds/menusound.wav").toString());
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
            alarm.play();
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
}
