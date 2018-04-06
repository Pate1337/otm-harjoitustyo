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
import javafx.scene.text.TextAlignment;


public class MainApp extends Application {
    private String backGroundImage;
    private Scene startScene;
    private ImageView backGround;
    private Group rootJoo;
    private Group menuGroup;
    private Stage mainStage;
    
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
        rootJoo.getChildren().clear();
        menuGroup = new Group();
        menuGroup.getChildren().add(backGround);
 
        VBox menu = new VBox();
        menu.setSpacing(10);
        menu.getChildren().add(createMenuButton("Play"));
        menu.getChildren().add(createMenuButton("Highscores"));
        menu.setLayoutX(200);
        menu.setLayoutY(400);
        menuGroup.getChildren().add(menu);  
        
        Scene menuScene = new Scene(menuGroup);
        
//        menuScene.setOnMouseClicked(
//            new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    double x = e.getX();
//                    double y = e.getY();
//                    if ((x >= 200 && x <= 600) && (y >= 400 && y <= 450)) {
//                        System.out.println("PLAY PAINETTU");
//                    } else if ((x >= 200 && x <= 600) && (y >= 460 && y <= 510)) {
//                        System.out.println("HIGHSCORES PAINETTU");
//                    }
//                }
//            });
        
        mainStage.setScene(menuScene);
        mainStage.show();
    }
    
    public Node createMenuButton(final String text) {
        Canvas canvas = new Canvas(400, 50);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 400, 50);
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
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println(text + " painettu");
                    if (text.equals("Play")) {
                        drawGame();
                    } else if (text.equals("Highscores")) {
                        System.out.println("");
                    }
                }
            });
        return canvas;
    }
    
    public void drawGame() {
        System.out.println("Piirretään peli");
        menuGroup.getChildren().clear();
        Group gameGroup = new Group();
        gameGroup.getChildren().add(backGround);
        Scene gameScene = new Scene(gameGroup);
        mainStage.setScene(gameScene);
        
        Canvas canvas = new Canvas(800, 800);
        gameGroup.getChildren().add( canvas );
        
        //Tähän eventit
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                //Tänne mitä piirretään
                
            }
        }.start();
        
        mainStage.show();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        //Alkunäytön asetus
        mainStage = stage;
        rootJoo = new Group();
        
        rootJoo.getChildren().add(backGround);
        
        Canvas canvas = new Canvas(800, 800);
        rootJoo.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText("Press any key to start!", 70, 370);
        gc.strokeText("Press any key to start!", 70, 370);
        
        startScene = new Scene(rootJoo);
        
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
