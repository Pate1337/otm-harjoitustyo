package com.mycompany.spaceinvaders;

import java.io.FileInputStream;
import javafx.scene.paint.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.Properties;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;


public class MainApp extends Application {
    private String backGroundImage;
    private Scene startScene;
    private Scene menuScene;
    private ImageView backGround;
    private Stage mainStage;
    private Group mainGroup;
    private String selected;
    private String prevSelected = "none";
    private boolean gamePaused = false;
    private Group menuGroup;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        
        String backGroundPicture = properties.getProperty("backGround");
        backGroundImage = backGroundPicture;
        Image image = new Image(new FileInputStream(backGroundImage));
        ImageView imageView = new ImageView(image);
        imageView.setX(0); 
        imageView.setY(0);
        imageView.setFitHeight(800); 
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(false);
        backGround = imageView;
    }
    
    public void drawMenu() {
        System.out.println("Moro");
        selected = "none";
//        mainGroup.getChildren().clear();
        menuGroup = new Group();
        menuGroup.getChildren().add(backGround);
        menuScene = new Scene(menuGroup);
        mainStage.setScene(menuScene);
        
        
        Rectangle menuTarget1 = new Rectangle(200, 400, 400, 50);
        menuTarget1.setOpacity(0.0);
        Rectangle menuTarget2 = new Rectangle(200, 460, 400, 50);
        menuTarget2.setOpacity(0.0);
        Rectangle menuTarget3 = new Rectangle(200, 520, 400, 50);
        menuTarget3.setOpacity(0.0);
 
        final VBox menu = new VBox();
        menu.setSpacing(10);
        if (gamePaused) {
            menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
        } else {
            menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
        }
        menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
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
                if (!gamePaused) {
                    System.out.println("QUIT");
                    selected = "Quit";
                } else {
                    System.out.println("Exit to main menu");
                    selected = "Exit to main menu";
                }
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
                if (code.equals("S") && !gamePaused) {
                    System.out.println("S painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Quit";
                    } else if (selected.equals("Quit")){
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("W") && !gamePaused) {
                    System.out.println("W painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Quit";
                    } else if (selected.equals("Quit")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("S") && gamePaused) {
                    System.out.println("S painettu");
                    if (selected.equals("none")) {
                        selected = "Resume";
                    } else if (selected.equals("Resume")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Exit to main menu";
                    } else if (selected.equals("Exit to main menu")) {
                        selected = "Resume";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("W") && gamePaused) {
                    System.out.println("W painettu");
                    if (selected.equals("none")) {
                        selected = "Resume";
                    } else if (selected.equals("Resume")) {
                        selected = "Exit to main menu";
                    } else if (selected.equals("Exit to main menu")) {
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
//                    System.out.println("Nyt pitäis apin sulkeutua");
//                    Platform.exit();
//                    System.exit(0);
                    drawConfirm("Quit");
                    this.stop();
                } else if (selected.equals("ExitToMenu")) {
//                    System.out.println("Exit to menu");
//                    gamePaused = false;
//                    drawMenu();
                    drawConfirm("Exit to main menu");
                    this.stop();
                } else if (!gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) { // && !inputs.isEmpty()
                    System.out.println("Piirretään koska !== none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (!gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Quit", 400, 50, 40));
                } else if (gamePaused && !selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska !== none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                } else if (gamePaused && selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Resume", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Highscores", 400, 50, 40));
                    menu.getChildren().add(createMenuButton("Exit to main menu", 400, 50, 40));
                }
                //Piirretään vain kun tapahtuu muutos, ei turhaan
                prevSelected = selected;
            }
        }.start();

        mainStage.show();
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
        selected = "none";
        Node areYouSure = areYouSure(text);
        areYouSure.setLayoutX(200);
        areYouSure.setLayoutY(300);
        
        Rectangle behind = new Rectangle(0, 0, 800, 800);
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
                if (code.equals("A")) {
                    System.out.println("A painettu");
                    if (selected.equals("none")) {
                        selected = text;
                    } else if (selected.equals(text)) {
                        selected = "Cancel";
                    } else if (selected.equals("Cancel")) {
                        selected = text;
                    }
                } else if (code.equals("D")) {
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
                    gamePaused = false;
                    drawMenu();
                    this.stop();
                } else if (selected.equals("goBack")) {
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
        Scene gameScene = new Scene(mainGroup);
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
                if (input.contains("A")) {
                    gc.setFill(Color.RED);
                    gc.fillRect(200, 400, 30, 30);
                } else if (input.contains("D")) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(600, 400, 30, 30);
                } else if (input.contains("W")) {
                    gc.setFill(Color.GREEN);
                    gc.fillRect(400, 200, 30, 30);
                } else if (input.contains("S")) {
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(400, 600, 30, 30);
                } else if (input.contains("ESCAPE")) {
                    gamePaused = true;
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
