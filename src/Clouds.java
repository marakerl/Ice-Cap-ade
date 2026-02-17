import java.awt.*;

public class Clouds {
    public int x, y;
    public int speed;

    public Clouds(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void updateX() {
        this.x -= speed;
    }

    public void draw(Graphics g) {
        // Use a semi-transparent white for a soft look
        g.setColor(new Color(255, 255, 255, 200));

        // Draw 3 overlapping ovals to create one cloud
        g.fillOval(x, y, 80, 50);
        g.fillOval(x + 25, y - 15, 100, 60);
        g.fillOval(x + 60, y, 80, 50);
    }
}