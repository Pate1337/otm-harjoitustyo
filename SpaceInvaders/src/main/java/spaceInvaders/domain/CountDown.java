/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import java.util.ArrayList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author paavo
 */
public class CountDown {
    private boolean done;
    private double velocity;
    private double fontSize;
    private ArrayList<String> texts;
    private int index;
    
    public CountDown() {
        this.done = false;
        this.velocity = -70;
        this.fontSize = 200;
        this.texts = new ArrayList<>();
        texts.add("3");
        texts.add("2");
        texts.add("1");
        texts.add("GO");
        this.index = 0;
    }
    public boolean ready() {
        return done;
    }
    public void update(double time) {
        fontSize += velocity * time;
        if (fontSize <= 100) {
            //Tähän voi laittaa äänen
            fontSize = 200;
            index++;
            if (index == 1) {
                Utils.playSound("utilities/sounds/two.wav");
            } else if (index == 2) {
                Utils.playSound("utilities/sounds/one.wav");
            } else if (index == 3) {
                Utils.playSound("utilities/sounds/zero.wav");
            } else if (index == texts.size()) {
                Utils.playSound("utilities/sounds/motion.wav");
                done = true;
            }
        }
    }
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, fontSize );
        gc.setFont( theFont );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
            texts.get(index), 
            Math.round(400), 
            Math.round(400)
        ); 
        gc.strokeText(texts.get(index), 
            Math.round(400), 
            Math.round(400)
        );
    }
   
}
