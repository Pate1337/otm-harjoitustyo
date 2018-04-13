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
    
    public static void playSound(String fileName) {
//        Media sound = new Media(new File(fileName).toURI().toString());
//        MediaPlayer soundPlayer = new MediaPlayer(sound);
//        soundPlayer.setVolume(0.0);
//        soundPlayer.play();
        
        //Audioclippi PALJON parempi!!
        try {
            AudioClip clip = new AudioClip(new File(fileName).toURI().toString());
            if (!muted) {
                clip.play();
            }
        } catch (Exception e) {
            System.out.println("Ei voi toistaa");
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
}
