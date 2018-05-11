package spaceinvaders.domain;

public enum Key {
    LEFT("A", "Left"),
    UP("W", "Up"),
    RIGHT("D", "Right"),
    DOWN("S", "Down"),
    SHOOT("ENTER", "Shoot");
    
    private String keyCode;
    private String keyName;
    
    private Key(String keyCode, String keyName) {
        this.keyCode = keyCode;
        this.keyName = keyName;
    }
    
    public String getKeyCode() {
        return this.keyCode;
    }
    
    public String getKeyName() {
        return this.keyName;
    }
    
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    
    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
