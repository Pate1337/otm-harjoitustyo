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
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class MainApp extends Application {
    private String backGroundImage;
    private Scene startScene;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        
        String backGroundPicture = properties.getProperty("backGround");
        backGroundImage = backGroundPicture;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
//        
//         theScene.setOnKeyReleased(
//            new EventHandler<KeyEvent>()
//            {
//                public void handle(KeyEvent e)
//                {
//                    String code = e.getCode().toString();
//                    input.remove( code );
//                }
//            });
        
        //Alkunäytön asetus
        Group root = new Group();
        Image image = new Image(new FileInputStream(backGroundImage));
        ImageView imageView = new ImageView(image);
        imageView.setX(0); 
        imageView.setY(0);
        imageView.setFitHeight(800); 
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(false);
        root.getChildren().add(imageView);
        
        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add( canvas );
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText("Press any key to start!", 50, 370);
        gc.strokeText("Press any key to start!", 50, 370);
        
        startScene = new Scene(root);
        
        startScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    System.out.println("Toimii");
                    
                }
            });
        startScene.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    System.out.println("Toimii");
                    
                }
            });
        
        stage.setTitle("Space Invaders!!!!");
        stage.setScene(startScene);
        stage.show();
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
