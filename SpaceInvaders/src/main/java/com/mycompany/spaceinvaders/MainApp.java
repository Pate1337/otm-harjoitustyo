package com.mycompany.spaceinvaders;

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
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import spaceinvaders.dao.FileHiScoresDao;
import spaceinvaders.domain.Game;
import spaceinvaders.domain.Key;
import spaceinvaders.domain.KeyService;
import spaceinvaders.dao.FileKeyDao;
import spaceinvaders.domain.ScoreService;


public class MainApp extends Application {
    private Image backGroundImage;
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
    private ScoreService scoreService;
    private String selectedKey;
    private String changeKey;
    private String prevSelectedKey;
    private String menuSound = "jotainVaan";
    private ImageView soundOnView;
    private ImageView soundOffView;
    private ImageView soundView;
    private boolean mutePressed = false;
    private Game game;
    private long lastNanoTime = 0;
    private boolean allowedToShoot = true;
    
    @Override
    public void init() throws Exception {
        
        backGroundImage = new Image(this.getClass().getResource("/resources/images/space1.jpg").toString());
        ImageView imageView = new ImageView(backGroundImage);
        imageView.setX(0); 
        imageView.setY(0);
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(false);
        backGround = imageView;

        FileKeyDao keyDao = new FileKeyDao("keys.txt");
        keyService = new KeyService(keyDao);
        FileHiScoresDao scoreDao = new FileHiScoresDao("hiscores.txt");
        scoreService = new ScoreService(scoreDao);
        Utils.initSounds();
        
        Image soundOnIcon = new Image(this.getClass().getResource("/resources/images/whitesoundon.png").toString());
        ImageView soundIsOnView = new ImageView(soundOnIcon);
        soundIsOnView.setX(700); 
        soundIsOnView.setY(700);
        soundIsOnView.setFitHeight(64);
        soundIsOnView.setFitWidth(64);
        soundIsOnView.setPreserveRatio(false);
        soundOnView = soundIsOnView;
        
        Image soundOffIcon = new Image(this.getClass().getResource("/resources/images/whitemute.png").toString());
        ImageView muteView = new ImageView(soundOffIcon);
        muteView.setX(690); 
        muteView.setY(698);
        muteView.setFitHeight(72);
        muteView.setFitWidth(64);
        muteView.setPreserveRatio(false);
        soundOffView = muteView;
        if (!Utils.getSoundState()) {
            soundView = soundOnView;
        } else {
            soundView = soundOffView;
        }
    }
    
    public void toggleSounds() {
        if (!Utils.getSoundState()) {
            Utils.mute();
            soundView = soundOffView;
        } else {
            Utils.unMute();
            soundView = soundOnView;
        }
    }
    
    public Node muteButton() {
        Rectangle button = new Rectangle(700, 700, 64, 64);
        button.setOpacity(0.0);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mutePressed = true;
            }
        });
        return button;
    }
    
    public void drawMenu() {
        final Rectangle behind = new Rectangle(0, 0, 800, 800);
        behind.setFill(Color.BLACK);
        behind.setOpacity(0.9);
        if (gamePaused) {
            if (cameFromGame) {
                menuGroup = mainGroup;
                menuScene = gameScene;
                cameFromGame = false;
            }
            menuGroup.getChildren().add(behind);
            menuGroup.getChildren().add(soundView);
        } else {
            menuGroup = new Group();
            menuGroup.getChildren().add(backGround);
            menuGroup.getChildren().add(soundView);
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
        
        final Node muteButton = muteButton();
        menuGroup.getChildren().add(muteButton);
        
        menuTarget1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                if (!gamePaused) {
                    selected = "Play";
                } else {
                    selected = "Resume";
                }
            }
        });
        
        menuTarget1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        menuTarget1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                if (!gamePaused) {
                    selected = "newGame";
                } else {
                    selected = "continueGame";
                }
            }
        });
        
        menuTarget2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "Highscores";
            }
        });
        
        menuTarget2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        menuTarget2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "GoToScores";
            }
        });
        
        menuTarget3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "Settings";
            }
        });
        
        menuTarget3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        menuTarget3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "GoToSettings";
            }
        });
        
        menuTarget4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                if (!gamePaused) {
                    selected = "Quit";
                } else {
                    selected = "Exit to main menu";
                }
            }
        });
        
        menuTarget4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        
        menuTarget4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
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
                    Utils.playMenuSound(menuSound);
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
                    Utils.playMenuSound(menuSound);
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
                    Utils.playMenuSound(menuSound);
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
                    Utils.playMenuSound(menuSound);
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
                } else if (code.equals(Key.SHOOT.getKeyCode()) && (selected.equals("Play"))) {
                    Utils.playMenuSound(menuSound);
                    selected = "newGame";
                } else if (code.equals(Key.SHOOT.getKeyCode()) && selected.equals("Resume")) {
                    Utils.playMenuSound(menuSound);
                    selected = "continueGame";
                } else if (code.equals(Key.SHOOT.getKeyCode()) && selected.equals("Quit")) {
                    Utils.playMenuSound(menuSound);
                    selected = "QuitApp";
                } else if (code.equals(Key.SHOOT.getKeyCode()) && selected.equals("Exit to main menu")) {
                    Utils.playMenuSound(menuSound);
                    selected = "ExitToMenu";
                } else if (code.equals(Key.SHOOT.getKeyCode()) && selected.equals("Settings")) {
                    Utils.playMenuSound(menuSound);
                    selected = "GoToSettings";
                } else if (code.equals(Key.SHOOT.getKeyCode()) && selected.equals("Highscores")) {
                    Utils.playMenuSound(menuSound);
                    selected = "GoToScores";
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (mutePressed) {
                    mutePressed = false;
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    toggleSounds();
                    menuGroup.getChildren().add(soundView);
                    menuGroup.getChildren().add(muteButton());
                }
                if (selected.equals("newGame")) {
                    gamePaused = false;
                    game = new Game();
                    drawGame();
                    this.stop();
                } else if (selected.equals("continueGame")) {
                    gamePaused = false;
                    game.unPause();
                    if (Utils.getSoundsPaused()) {
                        Utils.continueSounds();
                    }
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
                    selected = "Keyboard";
                    if (menuGroup.getChildren().contains(behind)) {
                        menuGroup.getChildren().remove(behind);
                    }
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    drawSettings();
                    this.stop();
                } else if (selected.equals("GoToScores")) {
                    prevSelected = selected;
                    selected = "Back to menu";
                    if (menuGroup.getChildren().contains(behind)) {
                        menuGroup.getChildren().remove(behind);
                    }
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    drawScores();
                    this.stop();
                } else if (!gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) { // && !inputs.isEmpty()
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (!gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) {
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                } else if (gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Settings", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                }
                prevSelected = selected;
            }
        }.start();

        mainStage.show();
    }
    
    public void drawScores() {
        try {
            backGroundImage = new Image(this.getClass().getResource("/resources/images/space1.jpg").toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        final ImageView view = new ImageView(backGroundImage);
        view.setX(0);
        view.setY(0);
        view.setFitHeight(800);
        view.setFitWidth(800);
        view.setPreserveRatio(false);
        menuGroup.getChildren().add(view);
        menuGroup.getChildren().add(soundView);
        final Node muteButton = muteButton();
        menuGroup.getChildren().add(muteButton);
        
        final ArrayList<String> scores = scoreService.getScores();
        final VBox scoreList = new VBox();
        scoreList.setLayoutX(100);
        scoreList.setLayoutY(100);
        scoreList.getChildren().add(createScoreListing(0, "Name;Score", 600, 50, 30));
        for (int i = 0; i < scores.size(); i++) {
            scoreList.getChildren().add(createScoreListing(i + 1, scores.get(i), 600, 50, 30));
        }
        scoreList.getChildren().add(createMenuButton("Back to menu", 600, 50, 40));
        menuGroup.getChildren().add(scoreList);
        final Rectangle menuTarget = new Rectangle(100, 150 + (scores.size() * 50), 600, 50);
        menuTarget.setOpacity(0.0);
        menuGroup.getChildren().add(menuTarget);
        menuTarget.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "Back to menu";
            }
        });
        menuTarget.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        menuTarget.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "BackToMenu";
            }
        });
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.DOWN.getKeyCode()) || code.equals(Key.UP.getKeyCode())) {
                    if (selected.equals("none")) {
                        Utils.playMenuSound(menuSound);
                        selected = "Back to menu";
                    }
                } else if (code.equals(Key.SHOOT.getKeyCode())) {
                    if (selected.equals("Back to menu")) {
                        Utils.playMenuSound(menuSound);
                        selected = "BackToMenu";
                    }
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (mutePressed) {
                    mutePressed = false;
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    toggleSounds();
                    menuGroup.getChildren().add(soundView);
                    menuGroup.getChildren().add(muteButton());
                }
                if (selected.equals("BackToMenu")) {
                    prevSelected = selected;
                    selected = "Highscores";
                    menuGroup.getChildren().remove(view);
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    menuGroup.getChildren().remove(scoreList);
                    menuGroup.getChildren().remove(menuTarget);
                    drawMenu();
                    this.stop();
                } else if (selected.equals("Back to menu") && !prevSelected.equals(selected)) {
                    scoreList.getChildren().clear();
                    scoreList.getChildren().add(createScoreListing(0, "Name;Score", 600, 50, 30));
                    for (int i = 0; i < scores.size(); i++) {
                        scoreList.getChildren().add(createScoreListing(i + 1, scores.get(i), 600, 50, 30));
                    }
                    scoreList.getChildren().add(createMenuButton("Back to menu", 600, 50, 40));
                } else if (selected.equals("none") && !prevSelected.equals(selected)) {
                    scoreList.getChildren().clear();
                    scoreList.getChildren().add(createScoreListing(0, "Name;Score", 600, 50, 30));
                    for (int i = 0; i < scores.size(); i++) {
                        scoreList.getChildren().add(createScoreListing(i + 1, scores.get(i), 600, 50, 30));
                    }
                    scoreList.getChildren().add(createMenuButton("Back to menu", 600, 50, 40));
                }
                prevSelected = selected;
            }
        }.start();
    }
    public Node createScoreListing(int place, String text, double width, double height, double fontSize) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (place != 0) {
            gc.setFill(Color.GREY);
            gc.fillRect(0, 0, width, height);
        }
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, fontSize );
        gc.setFont( theFont );
        gc.setTextBaseline(VPos.CENTER);
        String[] parts = text.split(";");
        if (place == 0) {
            gc.fillText(parts[0], 20, canvas.getHeight() / 2);
            gc.strokeText(parts[0], 20, canvas.getHeight() / 2);
            gc.fillText(parts[1], (canvas.getWidth() / 2) + 20, canvas.getHeight() / 2);
            gc.strokeText(parts[1], (canvas.getWidth() / 2) + 20, canvas.getHeight() / 2);
        } else {
            gc.fillText(place + ". " + parts[0], 20, canvas.getHeight() / 2);
            gc.strokeText(place + ". " + parts[0], 20, canvas.getHeight() / 2);
            gc.fillText(parts[1], (canvas.getWidth() / 2) + 20, canvas.getHeight() / 2);
            gc.strokeText(parts[1], (canvas.getWidth() / 2) + 20, canvas.getHeight() / 2);
        }
        
        return canvas;
    }
    
    public void drawSettings() {
        try {
            backGroundImage = new Image(this.getClass().getResource("/resources/images/space1.jpg").toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        final ImageView view = new ImageView(backGroundImage);
        view.setX(0);
        view.setY(0);
        view.setFitHeight(800);
        view.setFitWidth(800);
        view.setPreserveRatio(false);
        menuGroup.getChildren().add(view);
        menuGroup.getChildren().add(soundView);
        final Node muteButton = muteButton();
        menuGroup.getChildren().add(muteButton);
        
        
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
                Utils.playMenuSound(menuSound);
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
                Utils.playMenuSound(menuSound);
                selected = "KeyboardSettings";
            }
        });
        
        settingsTarget2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
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
                Utils.playMenuSound(menuSound);
                selected = "BackToMenu";
            }
        });
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.DOWN.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
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
                    Utils.playMenuSound(menuSound);
                    if (selected.equals("none")) {
                        selected = "Keyboard";
                    } else if (selected.equals("Keyboard")) {
                        selected = "Back to menu";
                    } else if (selected.equals("Back to menu")) {
                        selected = "Keyboard";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals(Key.SHOOT.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
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
                if (mutePressed) {
                    mutePressed = false;
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    toggleSounds();
                    menuGroup.getChildren().add(soundView);
                    menuGroup.getChildren().add(muteButton());
                }
                if (selected.equals("KeyboardSettings")) {
                    prevSelected = selected;
                    selected = "Keyboard";
                    menuGroup.getChildren().remove(view);
                    menuGroup.getChildren().remove(settingsMenu);
                    menuGroup.getChildren().remove(settingsTarget1);
                    menuGroup.getChildren().remove(settingsTarget2);
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    drawKeyboardSettings();
                    this.stop();
                } else if (selected.equals("BackToMenu")) {
                    prevSelected = selected;
                    selected = "Settings";
                    menuGroup.getChildren().remove(view);
                    menuGroup.getChildren().remove(settingsMenu);
                    menuGroup.getChildren().remove(settingsTarget1);
                    menuGroup.getChildren().remove(settingsTarget2);
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
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
    }
    
    public void drawKeyboardSettings() {
        final int kidsAtStart = menuGroup.getChildren().size();
        selectedKey = "none";
        prevSelectedKey = "none";
        changeKey = "none";
        try {
            backGroundImage = new Image(this.getClass().getResource("/resources/images/space1.jpg").toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        final ImageView view = new ImageView(backGroundImage);
        view.setX(0);
        view.setY(0);
        view.setFitHeight(800);
        view.setFitWidth(800);
        view.setPreserveRatio(false);
        
        final EventHandler onClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selectedKey = "none";
                prevSelectedKey = "jotain";
                changeKey = "none";
                selected = "none";
                prevSelected = "jotain";
            }
        };
        view.setOnMouseClicked(onClick);
        
        menuGroup.getChildren().add(view);
        
        final ArrayList<Key> keys = keyService.getKeys();
        final ArrayList<String> keyNames = keyService.getKeyNames();
        
        final VBox keyButtons = new VBox();
        keyButtons.setSpacing(10);
        keyButtons.setLayoutX(100);
        keyButtons.setLayoutY(100);
        for (int i = 0; i < keys.size(); i++) {
            keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
        }
        keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
        keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
        menuGroup.getChildren().add(keyButtons);

        Rectangle rec = null;
        for (int i = 0; i < keys.size(); i++) {
            final int indx = i;
            if (i == 0) {
                rec = new Rectangle(100, 100, 600, 100);
            } else {
                rec = new Rectangle(100, (100 + (i * 110)), 600, 100);
            }
            rec.setOpacity(0.0);
            rec.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Utils.playMenuSound(menuSound);
                    selected = "none";
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
                    Utils.playMenuSound(menuSound);
                    changeKey = keys.get(indx).getKeyName();
                    prevSelectedKey = "none";
                }
            });
            menuGroup.getChildren().add(rec);
        }
        for (int i = 0; i < 2; i++) {
            final int indx = i;
            double first = 210 + ((keys.size() - 1) * 110);
            rec = new Rectangle(100, first + (i * 60), 600, 50);
            rec.setOpacity(0.0);
            rec.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Utils.playMenuSound(menuSound);
                    selectedKey = "none";
                    if (indx == 0) {
                        selected = "Apply settings";
                    } else {
                        selected = "Back to settings";
                    }
                }
            });
            rec.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    selected = "none";
                }
            });
            rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Utils.playMenuSound(menuSound);
                    if (indx == 0) {
                        selected = "SaveSettings";
                    } else {
                        selected = "BackToSettings";
                    }
                }
            });
            menuGroup.getChildren().add(rec);
        }
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!changeKey.equals("none")) {
                    Utils.playMenuSound(menuSound);
                    for (int i = 0; i < keys.size(); i++) {
                        if (keys.get(i).getKeyCode().equals(code)) {
                            keys.get(i).setKeyCode("");
                        }
                    }
                    int index = keyNames.indexOf(changeKey);
                    Key key = keys.get(index);
                    key.setKeyCode(code);
                    changeKey = "none";
                    prevSelected = "jotain";
                } else if (code.equals(Key.DOWN.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (!selectedKey.equals("none")) {
                        int index = keyNames.indexOf(selectedKey);
                        if (index == keyNames.size() - 1) {
                            selectedKey = "none";
                            selected = "Apply settings";
                            prevSelected = "jotain";
                        } else {
                            selectedKey = keyNames.get(index + 1);
                        }
                    } else if (selected.equals("Apply settings")) {
                        selected = "Back to settings";
                    } else if (selected.equals("Back to settings")) {
                        selected = "none";
                        selectedKey = keyNames.get(0);
                    } else {
                        selectedKey = keyNames.get(0);
                    }
                } else if (code.equals(Key.UP.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (!selectedKey.equals("none")) {
                        int index = keyNames.indexOf(selectedKey);
                        if (index == 0) {
                            selectedKey = "none";
                            selected = "Back to settings";
                            prevSelected = "jotain";
                        } else {
                            selectedKey = keyNames.get(index - 1);
                        }
                    } else if (selected.equals("Back to settings")) {
                        selected = "Apply settings";
                    } else if (selected.equals("Apply settings")) {
                        selected = "none";
                        selectedKey = keyNames.get(keyNames.size() - 1);
                    } else {
                        selectedKey = keyNames.get(0);
                    }
                } else if (code.equals(Key.SHOOT.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (selectedKey.equals("none")) {
                        changeKey = "none";
                        prevSelectedKey = "jotain";
                        if (selected.equals("Apply settings")) {
                            selected = "SaveSettings";
                        } else if (selected.equals("Back to settings")) {
                            selected = "BackToSettings";
                        }
                    } else {
                        changeKey = selectedKey;
                        prevSelectedKey = "none";
                    }
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (selected.equals("BackToSettings")) {
                    int kidsNow = menuGroup.getChildren().size();
                    int kidsAdded = kidsNow - kidsAtStart;
                    for (int i = 0; i < kidsAdded; i++) {
                        menuGroup.getChildren().remove(menuGroup.getChildren().size() - 1);
                    }
                    selected = "Keyboard";
                    try {
                        init();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    drawSettings();
                    this.stop();
                } else if (selected.equals("SaveSettings")) {
                    selected = "Back to settings";
                    keyService.updateFile();
                    keyButtons.getChildren().clear();
                    for (int i = 0; i < keys.size(); i++) {
                        keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
                    }
                    keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
                    keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
                } else if (!selectedKey.equals(prevSelectedKey)) {
                    keyButtons.getChildren().clear();
                    for (int i = 0; i < keys.size(); i++) {
                        keyButtons.getChildren().add(createKeyButton(keys.get(i), 600, 100, 40));
                    }
                    keyButtons.getChildren().add(createMenuButton("Apply settings", 600, 50, 40));
                    keyButtons.getChildren().add(createMenuButton("Back to settings", 600, 50, 40));
                } else if (!selected.equals(prevSelected)) {
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
                Utils.playMenuSound(menuSound);
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
                selected = "none";
            }
        });
        menuTarget1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
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
                Utils.playMenuSound(menuSound);
                selected = "Cancel";
            }
        });
        menuTarget2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        menuTarget2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "goBack";
            }
        });
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.LEFT.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (selected.equals("none")) {
                        selected = text;
                    } else if (selected.equals(text)) {
                        selected = "Cancel";
                    } else if (selected.equals("Cancel")) {
                        selected = text;
                    }
                } else if (code.equals(Key.RIGHT.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (selected.equals("none")) {
                        selected = text;
                    } else if (selected.equals(text)) {
                        selected = "Cancel";
                    } else if (selected.equals("Cancel")) {
                        selected = text;
                    }
                } else if (code.equals(Key.SHOOT.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
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
                    menuGroup.getChildren().remove(soundView);
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
        mainGroup.getChildren().clear();
        mainGroup = new Group();
        mainGroup.getChildren().add(backGround);
        gameScene = new Scene(mainGroup);
        mainStage.setScene(gameScene);
        
        Image earth = new Image(this.getClass().getResource("/resources/images/earth.png").toString());
        ImageView earthView = new ImageView(earth);
        earthView.setX(0); 
        earthView.setY(753);
        earthView.setFitHeight(47);
        earthView.setFitWidth(800);
        mainGroup.getChildren().add(earthView);
        
        Canvas canvas = new Canvas(800, 800);
        mainGroup.getChildren().add( canvas );
        
        final ArrayList<String> input = new ArrayList<String>();
        gameScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (code.equals(Key.SHOOT.getKeyCode()) && allowedToShoot) {
                        input.add(code);
                        allowedToShoot = false;
                    } else if (!input.contains(code) && !code.equals(Key.SHOOT.getKeyCode())) {
                        input.add(code);
                    }
                }
            });
        
        gameScene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!code.equals(Key.SHOOT.getKeyCode())) {
                        input.remove(code);
                    } else {
                        allowedToShoot = true;
                    }
                }
            });
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 30 );
        gc.setFont( theFont );
        
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {   
                double elapsedTime;
                if (lastNanoTime != 0) {
                    elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                } else {
                    elapsedTime = 0;
                }
                lastNanoTime = currentNanoTime;
                game.addPlayerVelocity(0);
                if (input.contains(Key.SHOOT.getKeyCode())) {
                    game.playerShoot();
                    input.remove(Key.SHOOT.getKeyCode());
                }
                if (input.contains(Key.LEFT.getKeyCode())) {
                    game.addPlayerVelocity(-700);
                } else if (input.contains(Key.RIGHT.getKeyCode())) {
                    game.addPlayerVelocity(700);
                } else if (input.contains("ESCAPE")) {
                    gamePaused = true;
                    selected = "Resume";
                    cameFromGame = true;
                    lastNanoTime = 0;
                    game.pause();
                    Utils.pauseSounds();
                    drawMenu();
                    this.stop();
                }
                game.update(elapsedTime);
                gc.clearRect(0, 0, 800, 800);
                game.render(gc);
                gc.setFill(Color.RED);
                String pointsText = "Score: " + game.getScore();
                gc.fillText(pointsText, 560, 50);
                gc.strokeText(pointsText, 560, 50);
                String lifesText = "Lives:";
                gc.fillText(lifesText, 30, 50);
                gc.strokeText(lifesText, 30, 50);
                drawHearts(gc, game.getLifes());
                if (game.getLifes() == 0) {
                    game.endGame();
                    selected = "none";
                    lastNanoTime = 0;
                    drawEndScreen();
                    this.stop();
                }
                game.collisions();
            }
        }.start();
        
        mainStage.show();
    }
    
    public void drawEndScreen() {
        Rectangle behind = new Rectangle(0, 0, 800, 800);
        behind.setFill(Color.BLACK);
        behind.setOpacity(0.9);
        menuGroup = mainGroup;
        menuScene = gameScene;
        menuGroup.getChildren().add(behind);
        menuGroup.getChildren().add(soundView);
        
        Rectangle screenOuter = new Rectangle(200, 200, 400, 400);
        screenOuter.setFill(Color.RED);
        Rectangle screenInner = new Rectangle(205, 205, 390, 390);
        screenInner.setFill(Color.GREY);
        menuGroup.getChildren().add(screenOuter);
        menuGroup.getChildren().add(screenInner);
        if (scoreService.getLimit() > game.getScore()) {
            drawGameOver();
        } else {
            drawSubmit();
        }
    }
    
    public void drawSubmit() {
        Canvas canvas = new Canvas(800, 800);
        menuGroup.getChildren().add(canvas);
        
        final Node muteButton = muteButton();
        menuGroup.getChildren().add(muteButton);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        Font font = Font.font("Times New Roman", FontWeight.BOLD, 60);
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.RED);
        gc.fillText("Game Over", Math.round(canvas.getWidth() / 2), 280);
        gc.strokeText("Game Over", Math.round(canvas.getWidth() / 2), 280);
        font = Font.font("Times New Roman", FontWeight.BOLD, 30);
        gc.setFont(font);
        gc.setLineWidth(2);
        gc.fillText("You made it to top10!", Math.round(canvas.getWidth() / 2), 330);
        gc.strokeText("You made it to top10!", Math.round(canvas.getWidth() / 2), 330);
        
        font = Font.font("Times New Roman", FontWeight.BOLD, 30);
        gc.setFont(font);
        gc.setLineWidth(2);
        gc.fillText("Your score", Math.round(canvas.getWidth() / 2), 370);
        gc.strokeText("Your score", Math.round(canvas.getWidth() / 2), 370);
        font = Font.font("Times New Roman", FontWeight.BOLD, 50);
        gc.setFont(font);
        String score = "" + game.getScore();
        gc.setLineWidth(5);
        gc.fillText(score, Math.round(canvas.getWidth() / 2), 430);
        gc.strokeText(score, Math.round(canvas.getWidth() / 2), 430);
        
        final HBox submit = new HBox();
        submit.setSpacing(5);
        submit.setLayoutX(205);
        submit.setLayoutY(450);
        menuGroup.getChildren().add(submit);
        Label name = new Label("Name:");
        font = Font.font("Times New Roman", FontWeight.BOLD, 20);
        name.setFont(font);
        submit.getChildren().add(name);
        final TextField nameField = new TextField();
        nameField.setPrefWidth(150);
        nameField.setPrefHeight(40);
        nameField.setFont(font);
        selected = "none";
        
        nameField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "textField";
                nameField.setEditable(true);
            }
        });
        
        nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (selected.equals("textField")) {
                    if (code.equals(Key.SHOOT.getKeyCode())) {
                        nameField.setEditable(false);
                        selected = "Submit";
                    }
                } else {
                    if (code.equals(Key.DOWN.getKeyCode())) {
                        Utils.playMenuSound(menuSound);
                        if (selected.equals("none")) {
                            selected = "Submit";
                        } else if (selected.equals("Submit")) {
                            selected = "Play again";
                        } else if (selected.equals("Play again")) {
                         selected = "Back to menu";
                        } else if (selected.equals("Back to menu")) {
                         selected = "Submit";
                        }
                    } else if (code.equals(Key.UP.getKeyCode())) {
                        Utils.playMenuSound(menuSound);
                        if (selected.equals("none")) {
                            selected = "Submit";
                        } else if (selected.equals("Submit")) {
                            selected = "Back to menu";
                        } else if (selected.equals("Play again")) {
                            selected = "Submit";
                        } else if (selected.equals("Back to menu")) {
                            selected = "Play again";
                        }
                    } else if (code.equals(Key.LEFT.getKeyCode()) && selected.equals("Submit")) {
                        selected = "textField";
                        nameField.setEditable(true);
                    } else if (code.equals(Key.SHOOT.getKeyCode())) {
                        if (selected.equals("Play again")) {
                            Utils.playMenuSound(menuSound);
                            selected = "PlayAgain";
                        } else if (selected.equals("Back to menu")) {
                            Utils.playMenuSound(menuSound);
                            selected = "BackToMenu";
                        } else if (selected.equals("Submit")) {
                            Utils.playMenuSound(menuSound);
                            selected = "SubmitScore";
                        }
                    }
                }
            }
        });
        submit.getChildren().add(nameField);
        Node submitButton = createMenuButton("Submit", 150, 40, 30);
        submit.getChildren().add(submitButton);
        Rectangle submitTarget = new Rectangle(440, 450, 150, 40);
        submitTarget.setOpacity(0.0);
        submitTarget.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "Submit";
            }
        });
        submitTarget.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        });
        submitTarget.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                selected = "SubmitScore";
            }
        });
        menuGroup.getChildren().add(submitTarget);
        
        final VBox buttons = new VBox();
        drawEndScreenButtons(buttons, 205, 510);
        
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (mutePressed) {
                    mutePressed = false;
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    toggleSounds();
                    menuGroup.getChildren().add(soundView);
                    menuGroup.getChildren().add(muteButton());
                }
                if (selected.equals("BackToMenu")) {
                    prevSelected = selected;
                    selected = "Play";
                    drawMenu();
                    this.stop();
                } else if (selected.equals("PlayAgain")) {
                    game = new Game();
                    drawGame();
                    this.stop();
                } else if (selected.equals("SubmitScore")) {
                    String toSubmit = nameField.getCharacters() + ";" + game.getScore();
                    scoreService.update(toSubmit);
                    prevSelected = selected;
                    selected = "none";
                    menuGroup.getChildren().clear();
                    drawScores();
                    this.stop();
                } else if (selected.equals("Play again") && !prevSelected.equals(selected)) {
                    submit.getChildren().remove(submit.getChildren().size() - 1);
                    submit.getChildren().add(createMenuButton("Submit", 150, 40, 30));
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("Back to menu") && !prevSelected.equals(selected)) {
                    submit.getChildren().remove(submit.getChildren().size() - 1);
                    submit.getChildren().add(createMenuButton("Submit", 150, 40, 30));
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("none") && !prevSelected.equals(selected)) {
                    submit.getChildren().remove(submit.getChildren().size() - 1);
                    submit.getChildren().add(createMenuButton("Submit", 150, 40, 30));
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("Submit") && !prevSelected.equals(selected)) {
                    submit.getChildren().remove(submit.getChildren().size() - 1);
                    submit.getChildren().add(createMenuButton("Submit", 150, 40, 30));
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("textField") && !prevSelected.equals(selected)) {
                    submit.getChildren().remove(submit.getChildren().size() - 1);
                    submit.getChildren().add(createMenuButton("Submit", 150, 40, 30));
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                }
                
                prevSelected = selected;
            }
        }.start();
    }
    
    
    public void drawGameOver() {
        Canvas canvas = new Canvas(800, 800);
        menuGroup.getChildren().add(canvas);
        
        final Node muteButton = muteButton();
        menuGroup.getChildren().add(muteButton);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        Font font = Font.font("Times New Roman", FontWeight.BOLD, 60);
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.RED);
        gc.fillText("Game Over", Math.round(canvas.getWidth() / 2), 280);
        gc.strokeText("Game Over", Math.round(canvas.getWidth() / 2), 280);
        font = Font.font("Times New Roman", FontWeight.BOLD, 30);
        gc.setFont(font);
        gc.setLineWidth(2);
        gc.fillText("Your score", Math.round(canvas.getWidth() / 2), 360);
        gc.strokeText("Your score", Math.round(canvas.getWidth() / 2), 360);
        font = Font.font("Times New Roman", FontWeight.BOLD, 50);
        gc.setFont(font);
        String score = "" + game.getScore();
        gc.setLineWidth(5);
        gc.fillText(score, Math.round(canvas.getWidth() / 2), 420);
        gc.strokeText(score, Math.round(canvas.getWidth() / 2), 420);
        
        final VBox buttons = new VBox();
        drawEndScreenButtons(buttons, 205, 510);
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals(Key.DOWN.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (selected.equals("none")) {
                        selected = "Play again";
                    } else if (selected.equals("Play again")) {
                        selected = "Back to menu";
                    } else if (selected.equals("Back to menu")) {
                        selected = "Play again";
                    }
                } else if (code.equals(Key.UP.getKeyCode())) {
                    Utils.playMenuSound(menuSound);
                    if (selected.equals("none")) {
                        selected = "Play again";
                    } else if (selected.equals("Play again")) {
                        selected = "Back to menu";
                    } else if (selected.equals("Back to menu")) {
                        selected = "Play again";
                    }
                } else if (code.equals(Key.SHOOT.getKeyCode())) {
                    if (selected.equals("Play again")) {
                        Utils.playMenuSound(menuSound);
                        selected = "PlayAgain";
                    } else if (selected.equals("Back to menu")) {
                        Utils.playMenuSound(menuSound);
                        selected = "BackToMenu";
                    }
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (mutePressed) {
                    mutePressed = false;
                    menuGroup.getChildren().remove(soundView);
                    menuGroup.getChildren().remove(muteButton);
                    toggleSounds();
                    menuGroup.getChildren().add(soundView);
                    menuGroup.getChildren().add(muteButton());
                }
                if (selected.equals("BackToMenu")) {
                    prevSelected = selected;
                    selected = "Play";
                    drawMenu();
                    this.stop();
                } else if (selected.equals("PlayAgain")) {
                    game = new Game();
                    drawGame();
                    this.stop();
                } else if (selected.equals("Play again") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("Back to menu") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                } else if (selected.equals("none") && !prevSelected.equals(selected)) {
                    buttons.getChildren().clear();
                    buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
                    buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
                }
                prevSelected = selected;
            }
        }.start(); 
    }
    public void drawEndScreenButtons(VBox buttons, double x, double y) {
        final double startY = y;
        buttons.setSpacing(5);
        buttons.setLayoutX(x);
        buttons.setLayoutY(startY);
        menuGroup.getChildren().add(buttons);
        buttons.getChildren().add(createMenuButton("Play again", 390, 40, 30));
        buttons.getChildren().add(createMenuButton("Back to menu", 390, 40, 30));
        Rectangle menuTarget1 = new Rectangle(x, startY + 5, 390, 40);
        menuTarget1.setOpacity(0.0);
        Rectangle menuTarget2 = new Rectangle(x, startY + 50, 390, 40);
        menuTarget2.setOpacity(0.0);
        menuGroup.getChildren().add(menuTarget1);
        menuGroup.getChildren().add(menuTarget2);
        EventHandler mouseEnter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                if (e.getY() >= startY + 50) {
                    selected = "Back to menu";
                } else {
                    selected = "Play again";
                }
            }
        };
        menuTarget1.setOnMouseEntered(mouseEnter);
        menuTarget2.setOnMouseEntered(mouseEnter);
        EventHandler mouseExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selected = "none";
            }
        };
        menuTarget1.setOnMouseExited(mouseExit);
        menuTarget2.setOnMouseExited(mouseExit);
        EventHandler onClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Utils.playMenuSound(menuSound);
                if (e.getY() >= startY + 50) {
                    selected = "BackToMenu";
                } else {
                    selected = "PlayAgain";
                }
            }
        };
        menuTarget1.setOnMouseClicked(onClick);
        menuTarget2.setOnMouseClicked(onClick);
    }
    
    public void drawHearts(GraphicsContext gc, int lifes) {
        double positionX = 140;
        double positionY = 20;
        ArrayList<Image> hearts = Utils.getHearts();
        for (int i = 0; i < lifes; i++) {
            gc.drawImage(hearts.get(0), positionX, positionY, 25, 25);
            positionX += 30;
        }
        for (int i = 0; i < 3 - lifes; i++) {
            gc.drawImage(hearts.get(1), positionX, positionY, 25, 25);
            positionX += 30;
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
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
                        Utils.playMenuSound(menuSound);
                    drawMenu();
                }
            });
        startScene.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Utils.playMenuSound(menuSound);
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
