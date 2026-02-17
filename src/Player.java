import java.awt.*;

public class Player {
    int x, y;
    int WIDTH,HEIGHT;
    int score;

    private double vy = 0;
    private double gravity = 0.4;
    private double jumpForce = -10;

    public Player(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.score = 0;
    }

    public void draw (Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x,y, WIDTH,HEIGHT);
    }

    public void updateY (){
        vy += gravity;   // fall acceleration
        y += vy;         // apply movement
    }

    public void jump() {
        vy = jumpForce;  // upward impulse
    }

    public void giveScore() {
        score++;
    }
}
