import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    int x, y;
    int WIDTH,HEIGHT;
    int score;

    private double vy = 0;
    private double gravity = 0.4;
    private double jumpForce = -10;

    private BufferedImage imgUp, imgStraigt, imgDown;

    public Player(int x, int y, int WIDTH) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = (int) (WIDTH * (10.0/12.0));
        this.score = 0;

        try {
            imgUp = ImageIO.read(new File("/home/marakerl/IdeaProjects/Ice Cap-ade/src/wing_up.png"));
            imgStraigt = ImageIO.read(new File("/home/marakerl/IdeaProjects/Ice Cap-ade/src/wing_straight.png"));
            imgDown = ImageIO.read(new File("/home/marakerl/IdeaProjects/Ice Cap-ade/src/wing_down.png"));
        } catch (IOException e) {
            System.out.println("Could not load sprites, using fallback colors.");
        }
    }

    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage currentSprite = imgStraigt;
        if (vy < -1) {
            currentSprite = imgUp;
        } else if (vy > 1) {
            currentSprite = imgDown;
        }

        // 2. Logic: Calculate rotation angle (clamped between -20 and 45 degrees)
        double angle = Math.toRadians(Math.max(-20, Math.min(vy * 3, 45)));

        // 3. Transformation: Move to center, rotate, draw, then move back
        g2d.translate(x + WIDTH / 2, y + HEIGHT / 2);
        g2d.rotate(angle);

        if (currentSprite != null) {
            g2d.drawImage(currentSprite, -WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT, null);
        } else {
            // Fallback if image fails
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(-WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT);
        }

        // Reset transformation for the next draw call
        g2d.rotate(-angle);
        g2d.translate(-(x + WIDTH / 2), -(y + HEIGHT / 2));

        // DEBUG: Draw the hitbox in red
        g.setColor(Color.RED);
        Rectangle r = getBounds();
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    public Rectangle getBounds() {
        // 1. Shifting the box back (away from the nose)
        // Try a value like 5 or 10 pixels, or a percentage of the width
        int xOffset = (int) (WIDTH * 0.125); // Moves the box back by 15% of its width

        // 2. Shrink the width slightly so the box doesn't stick out the back
        int boxWidth = WIDTH - (int)(WIDTH * 0.42);

        // 3. Keep the height centered
        int vPadding = (int) (HEIGHT * 0.4);
        int boxHeight = HEIGHT - (vPadding * 2);

        // x + xOffset pushes the red box to the right (toward the tail)
        return new Rectangle(x + boxWidth/2 - xOffset, y + vPadding, boxWidth, boxHeight);
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
