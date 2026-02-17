import java.awt.*;
import java.util.Random;

public class Obstacle {
    int x;
    int yTop = 0;
    int yBottom;
    int WIDTH = 150;
    int HEIGHT;
    Random rand = new Random();
    int TOMRUM = 250;
    Boolean timeForNew;
    int speed = 5;

    public Obstacle () {
        this.HEIGHT = 200 + rand.nextInt(500);
        this.yBottom = HEIGHT+TOMRUM;
        this.x = 1000;
        this.timeForNew = false;
    }

    public Obstacle(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public void draw (Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x,yBottom, WIDTH,1000-HEIGHT-TOMRUM);//Nere
        g.fillRect(x,yTop,WIDTH, HEIGHT);//UppeyBottom
        g.fillRect(x-20,yBottom,WIDTH+20+20,60);
        g.fillRect(x-20,HEIGHT-40,WIDTH+20+20,60);
    }

    public void updateX (){
        this.x -= speed;
    }

    public void setTimeForNew(Boolean bool) {
        this.timeForNew = bool;
    }
}
