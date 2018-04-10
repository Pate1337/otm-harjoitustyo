/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.spaceinvaders;

import java.io.File;
import javafx.scene.media.AudioClip;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

/**
 *
 * @author paavo
 */
public class Utils {
    
    public static void playSound(String fileName) {
//        Media sound = new Media(new File(fileName).toURI().toString());
//        MediaPlayer soundPlayer = new MediaPlayer(sound);
//        soundPlayer.play();
        
        //Audioclippi PALJON parempi!!
        AudioClip clip = new AudioClip(new File(fileName).toURI().toString());
        clip.play();
    }
}
