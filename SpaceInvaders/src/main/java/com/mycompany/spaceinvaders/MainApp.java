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
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Space Invaders!!");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
        
//        Canvas canvas = new Canvas(800, 800);
//        root.getChildren().add( canvas );
        
        Image image = new Image(new FileInputStream("/home/paavo/otm-harjoitustyo/SpaceInvaders/space1.jpg"));
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
        gc.fillText("Press PLAY to start playing!", 30, 370);
        gc.strokeText("Press PLAY to start playing!", 30, 370);
        final ArrayList<String> input = new ArrayList<String>();
 
        theScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
 
                    // only add once... prevent duplicates
                    if ( !input.contains(code) ) {
                        input.add( code );
                        gc.clearRect(0, 0, 800, 800);
                        System.out.println("Painettu " + code);
                    }
                    
                }
            });
        
         theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            });
        
        //Tehdään menupainike
        gc.setFill(Color.GREY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.fillRect(0, 0, 300, 50);
        gc.strokeRect(0, 0, 300, 50);
        gc.setFill(Color.RED);
        theFont = Font.font( "Times New Roman", FontWeight.BOLD, 20 );
        gc.setFont( theFont );
        gc.fillText("PLAY", 10, 20);
         
        VBox valikko = new VBox();
        valikko.setSpacing(10);
//        valikko.getChildren().add(jotain)
        
        
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
