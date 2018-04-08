/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders.domain;

/**
 *
 * @author paavo
 */
public enum Key {
    LEFT("A"),
    UP("W"),
    RIGHT("D"),
    DOWN("S");
    
    private String keyCode;
    
    private Key(String keyCode) {
        this.keyCode = keyCode;
    }
    
    public String getKeyCode() {
        return this.keyCode;
    }
    
    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
