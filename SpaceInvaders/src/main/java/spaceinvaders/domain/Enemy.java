package spaceinvaders.domain;

import com.mycompany.spaceinvaders.Utils;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Luokka pitää kirjaa yhden vihollialuksen tiedoista.
 */
public class Enemy implements GameObject {
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean destroyed;
    private boolean explosion;
    private double explosionX;
    private double explosionY;
    private int count;
    private String type;
    private Image enemy;
    private ArrayList<Image> explosionImages;
    
    /**
     * Konstruktorissa alustetaan uusi vihollisalus.
     * Normaalin vihollisaluksen alku y-koordinaatti on 0 ja x-koordinaatti satunnainen
     * luku ruudulta.
     * Bonusaluksen alku y-koordinaatti on 150. Bonusalus lähtee ruudun vasemmalta tai oikealta.
     * @param type vihollisaluksen tyyppi ("bonus" tai "normal").
     * 
     * @see java.util.Random
     */
    public Enemy(String type) {
        this.type = type;
        this.height = 40;
        Random random = new Random();
        this.destroyed = false;
        this.explosion = false;
        this.count = 0;
        if (type.equals("bonus")) {
            try {
                enemy = new Image(this.getClass().getResource("/resources/images/ufo.png").toString());
            } catch (Exception e) {
            }
            this.width = 70;
            this.positionY = 150;
            this.velocityY = 0;
            if (random.nextInt(2) == 0) {
                this.positionX = 0 - width;
                this.velocityX = 200;
            } else {
                this.positionX = 800;
                this.velocityX = -200;
            }
        } else if (type.equals("normal")) {
            try {
                enemy = new Image(this.getClass().getResource("/resources/images/enemy.png").toString());
            } catch (Exception e) {
            }
            this.width = 50;
            this.positionY = 0;
            this.positionX = random.nextInt(801 - (int) width);
            this.velocityY = 200;
            this.velocityX = 0;
        }
        explosionImages = Utils.getExplosionImages();
    }
    
    /**
     * Metodi päivittää vihollisaluksen tilanteen.
     * Jos alus menee ruudun alareunaan se asetetaan räjähtämään.
     * @param time viimeisimmästä päivityksestä kulunut aika.
     */
    @Override
    public void update(double time) {
        if (!explosion) {
            positionY += velocityY * time;
            positionX += velocityX * time;
            if (positionY >= 800 - height) {
                explosion = true;
                explosionX = positionX;
                explosionY = 800 - width; //Jälkimmäinen on räjähdyksen koko
                positionX = 0;
                positionY = 800;
                try {
                    Utils.playExplosion();
                } catch (Exception e) {
                }
            }  else if (positionX < 0 - width) {
                destroyed = true;
            } else if (positionX > 800) {
                destroyed = true;
            }
        }
    }
    /**
     * Metodi piirtää vihollisaluksen ruudulle.
     * Jos alus on määritelty räjähtämään, metodi suoritetaan 16 kertaa, 
     * jolloin piirretään räjähdysanimaatio. Tämän jälkeen alus asetetaan tuhoutuneeksi.
     * @param gc käyttöliittymän piirtoalusta.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (!explosion) {
            gc.drawImage(enemy, positionX, positionY, width, height);
        } else {
            gc.drawImage(explosionImages.get(count), explosionX, explosionY, width, width);
            if (count == 15) {
                destroyed = true;
            } else {
                count++;
            }
        }
    }
    /**
     * Metodi palauttaa vihollisaluksen koordinaatit.
     * @return vihollisaluksen rajat
     */
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    /**
     * Metodi laskee, kohtaavatko alus ja parametrina annettuna olio.
     * @param o Pelin komponentti. (Missile)
     * @return palauttaa true tai false sen mukaan, kohtaavatko komponenttien rajat.
     */
    @Override
    public boolean intersects(GameObject o) {
        return o.getBoundary().intersects(this.getBoundary());
    }
    /**
     * Metodi palauttaa tiedon, onko alus tuhoutunut.
     * @return true tai false
     */
    public boolean destroyed() {
        return destroyed;
    }
    /**
     * Metodi asettaa aluksen tuhoutuneeksi.
     */
    public void explode() {
        explosion = true;
        explosionX = positionX;
        explosionY = positionY;
        positionX = 0;
        positionY = 850;
    }
    /**
     * Metodi palauttaa aluksen y-koordinaatin.
     * @return vihollisaluksen y-koordinaatti.
     */
    public double getPositionY() {
        return positionY;
    }
    /**
     * Metodi palauttaa vihollisaluksen tyypin.
     * @return "normal" tai "bonus"
     */
    public String getType() {
        return type;
    }
    
}
