package com.mycompany.spaceinvaders;

import java.io.FileInputStream;
import javafx.scene.paint.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import spaceInvaders.domain.Key;
import spaceInvaders.domain.KeyService;
import spaceinvaders.dao.FileKeyDao;


public class MainApp extends Application {
    private String backGroundImage;
    private Scene startScene;
    private Scene menuScene;
    private Scene gameScene;
    private ImageView backGround;
    private Stage mainStage;
    private Group mainGroup;
    private String selected = "Play";
    private String prevSelected = "none";
    private boolean gamePaused = false;
    private Group menuGroup;
    private boolean cameFromGame = false;
    private Image image;
    private KeyService keyService;
    private String selectedKey;
    private String changeKey;
    private String prevSelectedKey;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        
        String backGroundPicture = properties.getProperty("backGround");
        backGroundImage = backGroundPicture;
        image = new Image(new FileInputStream(backGroundImage));
        ImageView imageView = new ImageView(image);
        imageView.setX(0); 
        imageView.setY(0);
        imageView.setFitHeight(800); 
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(false);
        backGround = imageView;
        String keyFile = properties.getProperty("keyFile");
        FileKeyDao keyDao = new FileKeyDao(keyFile);
        keyService = new KeyService(keyDao);
    }
    
    public void drawMenu() {
        System.out.println("Moro");
        final Rectangle behind = new Rectangle(0, 0, 800, 800);
        behind.setFill(Color.BLACK);
        behind.setOpacity(0.9);
        if (gamePaused) {
            //Tää on tod näk se mikä kusee kun tullaan settingeistä
            if (cameFromGame) {
                menuGroup = mainGroup;
                menuScene = gameScene;
                cameFromGame = false;
            }
            menuGroup.getChildren().add(behind);
        } else {
            menuGroup = new Group();
            menuGroup.getChildren().add(backGround);
            menuScene = new Scene(menuGroup);
            mainStage.setScene(menuScene); 
        }
        
        Rectangle menuTarget1 = new Rectangle(200, 400, 400, 50);
        menuTarget1.setOpacity(0.0);
        Rectangle menuTarget2 = new Rectangle(200, 460, 400, 50);
        menuTarget2.setOpacity(0.0);
        Rectangle menuTarget3 = new Rectangle(200, 520, 400, 50);
        menuTarget3.setOpacity(0.0);
        Rectangle menuTarget4 = new Rectangle(200, 580, 400, 50);
        menuTarget4.setOpacity(0.0);
 
        final VBox menu = new VBox();
        menu.setSpacing(10);
        if (gamePaused) {
            menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
        } else {
            menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
        }
        menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
        menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
        if (gamePaused) {
            menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
        } else {
            menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
        }
        menu.setLayoutX(200);
        menu.setLayoutY(400);
        menuGroup.getChildren().add(menu); 
        menuGroup.getChildren().add(menuTarget1);
        menuGroup.getChildren().add(menuTarget2);
        menuGroup.getChildren().add(menuTarget3);
        menuGroup.getChildren().add(menuTarget4);
        
        menuTarget1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!gamePaused) {
                    System.out.println("PLAY");
                    selected = "Play";
                } else {
                    System.out.println("RESUME");
                    selected = "Resume";
                }
            }
        });
        
        menuTarget1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("NONE");
                selected = "none";
            }
        });
        
        menuTarget1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "stop";
            }
        });
        
        menuTarget2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("HIGHSCORES");
                selected = "Highscores";
            }
        });
        
        menuTarget2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("NONE");
                selected = "none";
            }
        });
        
        menuTarget3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("SETTINGS");
                selected = "Settings";
            }
        });
        
        menuTarget3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("NONE");
                selected = "none";
            }
        });
        
        menuTarget3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "GoToSettings";
            }
        });
        
        menuTarget4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!gamePaused) {
                    System.out.println("QUIT");
                    selected = "Quit";
                } else {
                    System.out.println("Exit to main menu");
                    selected = "Exit to main menu";
                }
            }
        });
        
        menuTarget4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("NONE");
                selected = "none";
            }
        });
        
        menuTarget4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (gamePaused) {
                    selected = "ExitToMenu";
                } else {
                    selected = "QuitApp";
                }
            }
        });
        
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.DOWN.getKeyCode()) && !gamePaused) {
                    System.out.println("S painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Settings";
                    } else if (selected.equals("Settings")){
                        selected = "Quit";
                    } else if (selected.equals("Quit")) {
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals(Key.UP.getKeyCode()) && !gamePaused) {
                    System.out.println("W painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Quit";
                    } else if (selected.equals("Quit")) {
                        selected = "Settings";
                    } else if (selected.equals("Settings")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals(Key.DOWN.getKeyCode()) && gamePaused) {
                    System.out.println("S painettu");
                    if (selected.equals("none")) {
                        selected = "Resume";
                    } else if (selected.equals("Resume")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Settings";
                    } else if (selected.equals("Settings")) {
                        selected = "Exit to main menu";
                    } else if (selected.equals("Exit to main menu")) {
                        selected = "Resume";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals(Key.UP.getKeyCode()) && gamePaused) {
                    System.out.println("W painettu");
                    if (selected.equals("none")) {
                        selected = "Resume";
                    } else if (selected.equals("Resume")) {
                        selected = "Exit to main menu";
                    } else if (selected.equals("Exit to main menu")) {
                        selected = "Settings";
                    } else if (selected.equals("Settings")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Resume";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("ENTER") && (selected.equals("Play") || selected.equals("Resume"))) {
                    selected = "stop";
                } else if (code.equals("ENTER") && selected.equals("Quit")) {
                    selected = "QuitApp";
                } else if (code.equals("ENTER") && selected.equals("Exit to main menu")) {
                    selected = "ExitToMenu";
                } else if (code.equals("ENTER") && selected.equals("Settings")) {
                    selected = "GoToSettings";
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (selected.equals("stop")) {
                    System.out.println("Lopetettu");
                    gamePaused = false;
                    drawGame();
                    this.stop();
                } else if (selected.equals("QuitApp")) {
                    drawConfirm("Quit");
                    this.stop();
                } else if (selected.equals("ExitToMenu")) {
                    if (menuGroup.getChildren().contains(behind)) {
                        menuGroup.getChildren().remove(behind);
                    }
                    drawConfirm("Exit to main menu");
                    this.stop();
                } else if (selected.equals("GoToSettings")) {
                    prevSelected = selected;
                    //Laitetaan se mikä halutaan olla valittuna kun mennään settings
                    selected = "Keyboard";
                    if (menuGroup.getChildren().contains(behind)) {
                        menuGroup.getChildren().remove(behind);
                    }
                    drawSettings();
                    this.stop();
                } else if (!gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) { // && !inputs.isEmpty()
                    System.out.println("Piirretään koska !== none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (!gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska !== none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                } else if (gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                }
                //Piirretään vain kun tapahtuu muutos, ei turhaan
                prevSelected = selected;
            }
        }.start();

        mainStage.show();
    }
    
    public void drawSettings() {
        System.out.println("Terve, ollaan settingseissä!");
        try {
            image = new Image(new FileInputStream(backGroundImage));
        } catch (Exception e) {
            System.out.println(e);
        }
        final ImageView view = new ImageView(image);
        view.setX(0);
        view.setY(0);
        view.setFitHeight(800);
        view.setFitWidth(800);
        view.setPreserveRatio(false);
        menuGroup.getChildren().add(view);
        
        
        final VBox settingsMenu = new VBox();
        settingsMenu.setSpacing(10);
        settingsMenu.getChildren().add(createMenuButton("Keyboard", 400, 50, 40));
        settingsMenu.getChildren().add(createMenuButton("Back to menu", 400, 50, 40));
        final Rectangle settingsTarget1 = new Rectangle(200, 400, 400, 50);
        final Rectangle settingsTarget2 = new Rectangle(200, 460, 400, 50);
        settingsTarget1.setOpacity(0.0);
        settingsTarget2.setOpacity(0.0);
        settingsMenu.setLayoutX(200);
        settingsMenu.setLayoutY(400);
        menuGroup.getChildren().add(settingsMenu);
        menuGroup.getChildren().add(settingsTarget1);
        menuGroup.getChildren().add(settingsTarget2);
        
        settingsTarget1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "Keyboard";
            }
        });
        
        settingsTarget1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        settingsTarget1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "KeyboardSettings";
            }
        });
        
        settingsTarget2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "Back to menu";
            }
        });
        
        settingsTarget2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        settingsTarget2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "BackToMenu";
            }
        });
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.DOWN.getKeyCode())) {
                    if (selected.equals("none")) {
                        selected = "Keyboard";
                    } else if (selected.equals("Keyboard")) {
                        selected = "Back to menu";
                    } else if (selected.equals("Back to menu")) {
                        selected = "Keyboard";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals(Key.UP.getKeyCode())) {
                    if (selected.equals("none")) {
                        selected = "Keyboard";
                    } else if (selected.equals("Keyboard")) {
                        selected = "Back to menu";
                    } else if (selected.equals("Back to menu")) {
                        selected = "Keyboard";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("ENTER")) {
                    if (selected.equals("Keyboard")) {
                        selected = "KeyboardSettings";
                    } else if (selected.equals("Back to menu")) {
                        selected = "BackToMenu";
                    }
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (selected.equals("KeyboardSettings")) {
                    prevSelected = selected;
                    selected = "Keyboard";
                    //keyboardSettingissä ei käytetä samaa selectediä
                    menuGroup.getChildren().remove(view);
                    menuGroup.getChildren().remove(settingsMenu);
                    menuGroup.getChildren().remove(settingsTarget1);
                    menuGroup.getChildren().remove(settingsTarget2);
                    drawKeyboardSettings();
                    this.stop();
                } else if (selected.equals("BackToMenu")) {
                    prevSelected = selected;
                    selected = "Settings";
                    menuGroup.getChildren().remove(view);
                    menuGroup.getChildren().remove(settingsMenu);
                    menuGroup.getChildren().remove(settingsTarget1);
                    menuGroup.getChildren().remove(settingsTarget2);
                    drawMenu();
                    this.stop();
                } else if (selected.equals("Keyboard") && !prevSelected.equals(selected)) {
                    settingsMenu.getChildren().clear();
                    settingsMenu.getChildren().add(createMenuButton("Keyboard", 400, 50, 40));
                    settingsMenu.getChildren().add(createMenuButton("Back to menu", 400, 50, 40));
                } else if (selected.equals("Back to menu") && !prevSelected.equals(selected)) {
                    settingsMenu.getChildren().clear();
                    settingsMenu.getChildren().add(createMenuButton("Keyboard", 400, 50, 40));
                    settingsMenu.getChildren().add(createMenuButton("Back to menu", 400, 50, 40));
                }
                prevSelected = selected;
            }
        }.start();
        
//        mainStage.show();
    }
    
    public void drawKeyboardSettings() {
        System.out.println("Moi");
        selectedKey = "none";
        prevSelectedKey = "none";
        changeKey = "none";
        try {
            image = new Image(new FileInputStream(backGroundImage));
        } catch (Exception e) {
            System.out.println(e);
        }
        final ImageView view = new ImageView(image);
        view.setX(0);
        view.setY(0);
        view.setFitHeight(800);
        view.setFitWidth(800);
        view.setPreserveRatio(false);
        menuGroup.getChildren().add(view);
        
        final ArrayList<Key> keys = keyService.getKeys();
        final ArrayList<String> keyNames = keyService.getKeyNames();
        
        final VBox keyButtons = new VBox();
        keyButtons.setSpacing(10);
        keyButtons.setLayoutX(100);
        keyButtons.setLayoutY(200);
        for (int i = 0; i < keys.size(); i++) {
            keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
        }
        //Vielä painikkeet Back to Settings ja Apply settings
        keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
        keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
        menuGroup.getChildren().add(keyButtons);

        Rectangle rec = null;
        for (int i = 0; i < keys.size(); i++) {
            final int indx = i;
            if (i == 0) {
                rec = new Rectangle(100, 200, 600, 100);
            } else {
                rec = new Rectangle(100, (200 + (i * 110)), 600, 100);
            }
            rec.setOpacity(0.0);
            rec.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    selectedKey = keys.get(indx).getKeyName();
                }
            });
            rec.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    selectedKey = "none";
                }
            });
            rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    changeKey = keys.get(indx).getKeyName();
                    prevSelectedKey = "none";
                }
            });
            menuGroup.getChildren().add(rec);
        }
        for (int i = 0; i < 2; i++) {
            final int indx = i;
            double first = 310 + ((keys.size() - 1) * 110);
            rec = new Rectangle(100, first + (i * 60), 600, 50);
            rec.setOpacity(0.0);
            rec.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (indx == 0) {
                        selected = "Apply settings";
                    } else {
                        selected = "Back to settings";
                    }
                    System.out.println("Ollaan " + selected + " päällä");
                }
            });
            rec.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    selected = "none";
                    System.out.println("Ei olla enää mnikään päällä");
                }
            });
            rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (indx == 0) {
                        selected = "SaveSettings";
                    } else {
                        selected = "BackToSettings";
                    }
                    System.out.println("selected= " + selected);
                }
            });
            menuGroup.getChildren().add(rec);
        }
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!changeKey.equals("none")) {
                    //Nyt pitää asettaa uusi näppäin!!
                    //Tarkistetaan onko jollain jo se näppäin
                    for (int i = 0; i < keys.size(); i++) {
                        if (keys.get(i).getKeyCode().equals(code)) {
                            //asetetaan tyhjäksi
                            keys.get(i).setKeyCode("");
                        }
                    }
                    //Asetetaan uusi
                    int index = keyNames.indexOf(changeKey);
                    Key key = keys.get(index);
                    key.setKeyCode(code);
                    changeKey = "none";
                    selectedKey = "none";
                    prevSelected = "jotain";
                } else if (code.equals(Key.DOWN.getKeyCode())) {
                    if (!selectedKey.equals("none")) {
                        int index = keyNames.indexOf(selectedKey);
                        if (index == keyNames.size() - 1) {
                            selectedKey = keyNames.get(0);
                        } else {
                            selectedKey = keyNames.get(index + 1);
                        }
                    } else {
                        selectedKey = keyNames.get(0);
                    }
                } else if (code.equals(Key.UP.getKeyCode())) {
                    if (!selectedKey.equals("none")) {
                        int index = keyNames.indexOf(selectedKey);
                        if (index == 0) {
                            selectedKey = keyNames.get(keyNames.size() - 1);
                        } else {
                            selectedKey = keyNames.get(index - 1);
                        }
                    } else {
                        selectedKey = keyNames.get(0);
                    }
                } else if (code.equals("ENTER")) {
                    if (selectedKey.equals("none")) {
                        changeKey = "none";
                        prevSelectedKey = "jotain";
                    } else {
                        changeKey = selectedKey;
                        prevSelectedKey = "none";
                    }
                }
            }
        });
        //Jos tarvii nii tän voi poistaa ku tekee EventHandler = new EventHanddler..
        menuScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Menusceneen asetettu onclick");
                if (selectedKey.equals("none")) {
                    changeKey = "none";
                    prevSelectedKey = "jotain";
                }
            }
        });
//        //Loppuun
//        int index = menuGroup.getChildren().size() - 1;
//        for (int i = 0; i < keys.size(); i++) {
//            menuGroup.getChildren().remove(index);
//            index--;
//        } 
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (!selectedKey.equals(prevSelectedKey)) {
                    selected = "none";
                    System.out.println("Piirretään selectedKey=" + selectedKey);
                    keyButtons.getChildren().clear();
                    for (int i = 0; i < keys.size(); i++) {
                        keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
                    }
                    keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
                    keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
                } else if (!selected.equals(prevSelected)) {
                    selectedKey = "none";
                    System.out.println("Piirretään menunpainikkeiden takia");
//                    keyButtons.getChildren().remove(keyButtons.getChildren().size() - 1);
//                    keyButtons.getChildren().remove(keyButtons.getChildren().size() - 1);
//                    
//                    keyButtons.getChildren().add(createMenuButton("Apply settings", 400, 50, 40));
//                    keyButtons.getChildren().add(createMenuButton("Back to settings", 400, 50, 40));
                    keyButtons.getChildren().clear();
                    for (int i = 0; i < keys.size(); i++) {
                        keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
                    }
                    keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
                    keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
                }
                prevSelectedKey = selectedKey;
                prevSelected = selected;
            }
        }.start();
    }
    
    public Node createKeyButton(final Key key, double width, double height, double fontSize) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (selectedKey.equals(key.getKeyName())) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.GREY);
            gc.fillRect(4, 4, width - 8, height - 8);
        } else {
            gc.setFill(Color.GREY);
            gc.fillRect(0, 0, width, height);
        }
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, fontSize );
        gc.setFont( theFont );
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(key.getKeyName(), 20, canvas.getHeight() / 2);
        gc.strokeText(key.getKeyName(), 20, canvas.getHeight() / 2);
        
        //Nyt vielä pikkulaatikko johon näppäin
        gc.setFill(Color.BLACK);
        gc.fillRect(Math.round(canvas.getWidth() / 2), 
            Math.round(canvas.getHeight() / 4), 
            Math.round((canvas.getWidth() / 2) - 20), 
            Math.round((canvas.getHeight() / 4) * 2)
        );
        
        gc.setFill(Color.WHITE);
        gc.setLineWidth(2);
        theFont = Font.font( "Times New Roman", FontWeight.BOLD, fontSize );
        gc.setFont( theFont );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        if (!changeKey.equals(key.getKeyName())) {
            gc.fillText(
                key.getKeyCode(), 
                Math.round((canvas.getWidth() / 4) * 3), 
                Math.round((canvas.getHeight() / 4) * 2)
            );
        } else {
            gc.fillText(
                "Press a key", 
                Math.round((canvas.getWidth() / 4) * 3), 
                Math.round((canvas.getHeight() / 4) * 2)
            );
        }
        return canvas;
    }
    
    public Node createMenuButton(final String text, double width, double height, double fontSize) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (selected.equals(text)) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(0, 0, width, height);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }

        gc.setFill(Color.GREY);
        gc.fillRect(4, 4, width - 8, height - 8);
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, fontSize );
        gc.setFont( theFont );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
            text, 
            Math.round(canvas.getWidth()  / 2), 
            Math.round(canvas.getHeight() / 2)
        ); 
        gc.strokeText(text, 
            Math.round(canvas.getWidth()  / 2), 
            Math.round(canvas.getHeight() / 2)
        );

        return canvas;
    }
    
    public void drawConfirm(final String text) {
        selected = text;
        final Node areYouSure = areYouSure(text);
        areYouSure.setLayoutX(200);
        areYouSure.setLayoutY(300);
        
        final Rectangle behind = new Rectangle(0, 0, 800, 800);
        behind.setFill(Color.BLACK);
        behind.setOpacity(0.9);
        menuGroup.getChildren().add(behind);
        menuGroup.getChildren().add(areYouSure);
        
        final HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().add(createMenuButton(text, 160, 40, 15));
        buttons.getChildren().add(createMenuButton("Cancel", 160, 40, 15));
        buttons.setLayoutX(230);
        buttons.setLayoutY(400);
        menuGroup.getChildren().add(buttons);
        
        Rectangle menuTarget1 = new Rectangle(230, 400, 160, 40);
        menuTarget1.setOpacity(0.0);
        Rectangle menuTarget2 = new Rectangle(410, 400, 160, 40);
        menuTarget2.setOpacity(0.0);
        menuGroup.getChildren().add(menuTarget1);
        menuGroup.getChildren().add(menuTarget2);
        
        menuTarget1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Ollaan tekstin päällä");
                if (text.equals("Quit")) {
                    selected = "Quit";
                } else {
                    selected = "Exit to main menu";
                }
            }
        });
        menuTarget1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Ei olla enää tekstin päällä");
                selected = "none";
            }
        });
        menuTarget1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (text.equals("Quit")) {
                    selected = "QuitApp";
                } else {
                    selected = "ExitToMenu";
                }
            }
        });
        menuTarget2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Ollaan Cancelin päällä");
                selected = "Cancel";
            }
        });
        menuTarget2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Ei olla enää Cancelin päällä");
                selected = "none";
            }
        });
        menuTarget2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "goBack";
            }
        });
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.LEFT.getKeyCode())) {
                    System.out.println("A painettu");
                    if (selected.equals("none")) {
                        selected = text;
                    } else if (selected.equals(text)) {
                        selected = "Cancel";
                    } else if (selected.equals("Cancel")) {
                        selected = text;
                    }
                } else if (code.equals(Key.RIGHT.getKeyCode())) {
                    System.out.println("D painettu");
                    if (selected.equals("none")) {
                        selected = text;
                    } else if (selected.equals(text)) {
                        selected = "Cancel";
                    } else if (selected.equals("Cancel")) {
                        selected = text;
                    }
                } else if (code.equals("ENTER")) {
                    if (selected.equals("Cancel")) {
                        selected = "goBack";
                    } else if (selected.equals("Quit")) {
                        selected = "QuitApp";
                    } else if (selected.equals("Exit to main menu")) {
                        selected = "ExitToMenu";
                    }
                }
            }
        });
        
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (selected.equals("Quit") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Quit", 160, 40, 15));
                    buttons.getChildren().add(createMenuButton("Cancel", 160, 40, 15));
                } else if (selected.equals("Exit to main menu") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Exit to main menu", 160, 40, 15));
                    buttons.getChildren().add(createMenuButton("Cancel", 160, 40, 15));
                } else if (selected.equals("Cancel") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton(text, 160, 40, 15));
                    buttons.getChildren().add(createMenuButton("Cancel", 160, 40, 15));
                } else if (selected.equals("none") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton(text, 160, 40, 15));
                    buttons.getChildren().add(createMenuButton("Cancel", 160, 40, 15));
                } else if (selected.equals("QuitApp")) {
                    System.out.println("Bye bye");
                    Platform.exit();
                    System.exit(0);
                } else if (selected.equals("ExitToMenu")) {
                    prevSelected = selected;
                    selected = "Play";
                    gamePaused = false;
                    drawMenu();
                    this.stop();
                } else if (selected.equals("goBack")) {
                    menuGroup.getChildren().remove(behind);
                    menuGroup.getChildren().remove(areYouSure);
                    prevSelected = selected;
                    if (text.equals("Quit")) {
                        selected = "Quit";
                    } else {
                        selected = "Exit to main menu";
                    }
                    drawMenu();
                    this.stop();
                }
                prevSelected = selected;
            }
        }.start();
    }
    
    public Node areYouSure(String text) {
        Canvas canvas = new Canvas(400, 150);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 400, 150);
        gc.setFill(Color.GREY);
        gc.fillRect(6, 6, 388, 138);
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 20 );
        gc.setFont( theFont );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
            "Are you sure you want to", 
            Math.round(canvas.getWidth()  / 2), 
            Math.round(canvas.getHeight() / 4)
        );
        gc.fillText(text + "?",
            Math.round(canvas.getWidth() / 2),
            Math.round(canvas.getHeight() / 2.5)
        );
        gc.strokeText("Are you sure you want to", 
            Math.round(canvas.getWidth()  / 2), 
            Math.round(canvas.getHeight() / 4)
        );
        gc.strokeText(text + "?", 
            Math.round(canvas.getWidth()  / 2), 
            Math.round(canvas.getHeight() / 2.5)
        );
        
        return canvas;
    }
    
    public void drawGame() {
        System.out.println("Piirretään peli");
        mainGroup.getChildren().clear();
        mainGroup = new Group();
        mainGroup.getChildren().add(backGround);
        gameScene = new Scene(mainGroup);
        mainStage.setScene(gameScene);
        
        Canvas canvas = new Canvas(800, 800);
        mainGroup.getChildren().add( canvas );
        
        //Tähän eventit
        final ArrayList<String> input = new ArrayList<String>();
        gameScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) {
                        input.add(code);
                    }
                }
            });
        
        gameScene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    input.remove(code);
                }
            });
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                //Tänne mitä piirretään
                gc.clearRect(0, 0, 800, 800);
                gc.setFill(Color.BLUE);
                gc.fillRect(100, 100, 50, 50);
                if (input.contains(Key.LEFT.getKeyCode())) {
                    gc.setFill(Color.RED);
                    gc.fillRect(200, 400, 30, 30);
                } else if (input.contains(Key.RIGHT.getKeyCode())) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(600, 400, 30, 30);
                } else if (input.contains(Key.UP.getKeyCode())) {
                    gc.setFill(Color.GREEN);
                    gc.fillRect(400, 200, 30, 30);
                } else if (input.contains(Key.DOWN.getKeyCode())) {
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(400, 600, 30, 30);
                } else if (input.contains("ESCAPE")) {
                    gamePaused = true;
                    selected = "Resume";
                    cameFromGame = true;
                    drawMenu();
                    //Laitetaan kaikki pauselle ja piirretään menu päälle
                    this.stop();
                }
                
            }
        }.start();
        
        mainStage.show();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        //Alkunäytön asetus
        mainStage = stage;
        mainGroup = new Group();
        
        mainGroup.getChildren().add(backGround);
        
        Canvas canvas = new Canvas(800, 800);
        mainGroup.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText("Press any key to start!", 70, 370);
        gc.strokeText("Press any key to start!", 70, 370);
        
        startScene = new Scene(mainGroup);
        
        startScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    System.out.println("Toimii");                   
//                    stage.setScene(menuScene);
                    drawMenu();
                }
            });
        startScene.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Toimii");
//                    stage.setScene(menuScene);
                    drawMenu();
                }
            });
        
   
        mainStage.setTitle("Space Invaders!!!!");
        mainStage.setScene(startScene);
        mainStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(MainApp.class);
    }

}
