import java.awt.*;
import java.util.Random;

public class Clouds {
    int x;
    int y;
    int WIDTH;
    int HEIGHT;
    Random rand = new Random();
    int speed = 5;

    public Clouds(){
        x = 1000;
        y = 1000;
        WIDTH = 100;
        HEIGHT = 100;
    }

    public void draw (Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y, WIDTH,HEIGHT);//Nere
    }

    public void updateX (){
        this.x -= speed;
    }
}
