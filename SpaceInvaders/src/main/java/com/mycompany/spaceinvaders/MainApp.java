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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;


public class MainApp extends Application {
    private String backGroundImage;
    private Scene startScene;
    private ImageView backGround;
    private Stage mainStage;
    private Group mainGroup;
    private String selected;
    private String prevSelected = "none";
    
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
        mainGroup.getChildren().clear();
        mainGroup = new Group();
        mainGroup.getChildren().add(backGround);
        Scene menuScene = new Scene(mainGroup);
        mainStage.setScene(menuScene);
        
        
        Rectangle menuTarget1 = new Rectangle(200, 400, 400, 50);
        menuTarget1.setOpacity(0.0);
        Rectangle menuTarget2 = new Rectangle(200, 460, 400, 50);
        menuTarget2.setOpacity(0.0);
 
        final VBox menu = new VBox();
        menu.setSpacing(10);
        menu.getChildren().add(createMenuButton("Play"));
        menu.getChildren().add(createMenuButton("Highscores"));
        menu.setLayoutX(200);
        menu.setLayoutY(400);
        mainGroup.getChildren().add(menu); 
        mainGroup.getChildren().add(menuTarget1);
        mainGroup.getChildren().add(menuTarget2);
        
        menuTarget1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("PLAY");
                selected = "Play";
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
        
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (code.equals("S")) {
                    System.out.println("S painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("W")) {
                    System.out.println("W painettu");
                    if (selected.equals("none")) {
                        selected = "Play";
                    } else if (selected.equals("Play")) {
                        selected = "Highscores";
                    } else if (selected.equals("Highscores")) {
                        selected = "Play";
                    } else {
                        selected = "none";
                    }
                } else if (code.equals("ENTER")) {
                    selected = "stop";
                }
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (selected.equals("stop")) {
                    System.out.println("Lopetettu");
                    drawGame();
                    this.stop();
                } else if (!selected.equals("none") && !prevSelected.equals(selected)) { // && !inputs.isEmpty()
                    System.out.println("Piirretään koska !== none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play"));
                    menu.getChildren().add(createMenuButton("Highscores"));
                } else if (selected.equals("none") && !prevSelected.equals(selected)) {
                    System.out.println("Piirretään koska none");
                    menu.getChildren().clear();
                    menu.getChildren().add(createMenuButton("Play"));
                    menu.getChildren().add(createMenuButton("Highscores"));
                }
                //Piirretään vain kun tapahtuu muutos, ei turhaan
                prevSelected = selected;
            }
        }.start();

        mainStage.show();
    }
    
    public Node createMenuButton(final String text) {
        Canvas canvas = new Canvas(400, 50);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (selected.equals(text)) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(0, 0, 400, 50);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 400, 50);
        }

        gc.setFill(Color.GREY);
        gc.fillRect(4, 4, 392, 42);
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 40 );
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
                // Clear the canvas
                //Tänne mitä piirretään
                gc.clearRect(0, 0, 800, 800);
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
                    drawMenu();
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
