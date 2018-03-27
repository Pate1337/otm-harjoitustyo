package com.mycompany.spaceinvaders;

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
//import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Space Invaders!!");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
        
        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText("Press PLAY to start playing!", 30, 370);
        gc.strokeText("Press PLAY to start playing!", 30, 370);
//        Image space = new Image("space.png");
//        gc.drawImage( space, 0, 0 );
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
