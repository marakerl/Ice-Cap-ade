import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Obstacle {
    int x;
    int yTop = 0;
    int yBottom;
    int WIDTH = 160;
    int HEIGHT;
    Random rand = new Random();
    int TOMRUM = 250;
    Boolean timeForNew;
    int speed = 5;
    int fitSprite = 484;

    private BufferedImage iceSprite;

    public Obstacle () {
        this.HEIGHT = 200 + rand.nextInt(500);
        this.yBottom = HEIGHT+TOMRUM;
        this.x = 1000;
        this.timeForNew = false;

        try {
            iceSprite = ImageIO.read(new File("/home/marakerl/IdeaProjects/Ice Cap-ade/src/Ice.png"));
        } catch (IOException e) {
            System.out.println("Could not load sprites, using fallback colors.");
        }
    }

    public Obstacle(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public void draw (Graphics g) {
        if (iceSprite != null) {
            // DRAW TOP BLOCK (FLIPPED)
            g.drawImage(iceSprite, x-(fitSprite/2), yTop + HEIGHT, WIDTH+fitSprite, -HEIGHT, null);

            // DRAW BOTTOM BLOCK (NORMAL)
            g.drawImage(iceSprite, x-(fitSprite/2), yBottom, WIDTH+fitSprite, 1000 - yBottom, null);

        } else {
            // Fallback
            g.setColor(Color.BLUE);
            g.fillRect(x, yTop, WIDTH, HEIGHT);
            g.fillRect(x, yBottom, WIDTH, 1000 - yBottom);
        }

        //Hitbox debug
        /*
        g.setColor(Color.RED);
        Rectangle r = getTopBounds();
        Rectangle r2 = getBottomBounds();
        g.drawRect(r.x, r.y, r.width, r.height);
        g.drawRect(r2.x, r2.y, r2.width, r2.height);
        */
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, yTop, WIDTH, HEIGHT);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, yBottom, WIDTH, 1000 -HEIGHT-TOMRUM);
    }

    public void updateX (){
        this.x -= speed;
    }

    public void setTimeForNew(Boolean bool) {
        this.timeForNew = bool;
    }
}
