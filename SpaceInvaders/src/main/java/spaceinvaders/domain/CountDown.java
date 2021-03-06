package spaceinvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import java.util.ArrayList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Luokka näyttää pelin alkaessa lähtölaskenta-animaation.
 *
 */
public class CountDown {
    private boolean done;
    private double velocity;
    private double fontSize;
    private ArrayList<String> texts;
    private int index;
    
    /**
     * Konstruktori alustaa lähtölaskennassa näytettävän tekstin ja nopeuden.
     */
    public CountDown() {
        this.done = false;
        this.velocity = -70;
        this.fontSize = 200;
        this.texts = new ArrayList<>();
        texts.add("3");
        texts.add("2");
        texts.add("1");
        texts.add("GO");
        this.index = 0;
    }
    /**
     * Metodi lähettää tiedon, kun lähtölaskenta on saatu päätökseen.
     * @return lähtölaskenta valmis (true tai false)
     */
    public boolean ready() {
        return done;
    }
    /**
     * Metodi päivittää lähtölaskennan tilannetta ja soittaa mahdolliset ääniefektit.
     * @param time viimeisimmästä päivityksestä kulunut aika
     * 
     * @see com.mycompany.spaceinvaders.Utils#playTwo()
     * @see com.mycompany.spaceinvaders.Utils#playOne()
     * @see com.mycompany.spaceinvaders.Utils#playZero()
     * @see com.mycompany.spaceinvaders.Utils#playMotion()
     */
    public void update(double time) {
        fontSize += velocity * time;
        if (fontSize <= 100) {
            fontSize = 200;
            index++;
            if (index == 1) {
                Utils.playTwo();
            } else if (index == 2) {
                Utils.playOne();
            } else if (index == 3) {
                Utils.playZero();
            } else if (index == texts.size()) {
                Utils.playMotion();
                done = true;
            }
        }
    }
    /**
     * Metodi piirtää ruudulle lähtölaskennan sen hetkisen tilanteen.
     * @param gc käyttöliittymän piirtoalusta
     */
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, fontSize);
        gc.setFont(theFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(texts.get(index), Math.round(400), Math.round(400)); 
        gc.strokeText(texts.get(index), Math.round(400), Math.round(400));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        theFont = Font.font("Times New Roman", FontWeight.BOLD, 30);
        gc.setFont(theFont);
    }
   
}
