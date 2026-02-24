import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class View extends JPanel {

    private Model model;
    private JTextArea scoreBoard;
    private BufferedImage skyCache;

    public View(Model model) {
        this.model = model;
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setFocusable(true);

        scoreBoard = new JTextArea();
        scoreBoard.setEditable(false);
        scoreBoard.setFocusable(false);
        this.add(scoreBoard);
    }

    private void createSkyCache() {
        if (getWidth() <= 0 || getHeight() <= 0) return;

        skyCache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = skyCache.createGraphics();

        // Use high-quality rendering for the ONE-TIME draw
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint sky = new GradientPaint(0, 0, new Color(0, 102, 204),
                0, getHeight(), new Color(153, 204, 255));
        g2d.setPaint(sky);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }


    public void init(Controller controller) {
        this.addKeyListener(controller); // attach controller to view
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the Sky Gradient
        // From dark blue at top to light blue at bottom
        // Manage the Sky Cache
        if (skyCache == null || skyCache.getWidth() != getWidth() || skyCache.getHeight() != getHeight()) {
            createSkyCache();
        }

        // Draw the cached sky
        if (skyCache != null) {
            g2d.drawImage(skyCache, 0, 0, null);
        }

        // Set global performance hint for everything else
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        // Draw Clouds
        if (model.getClouds() != null) {
            for (Clouds c : model.getClouds()) {
                c.draw(g);
            }
        }

        // Draw Obstacles
        for (Obstacle o : model.getObstacles()) {
            o.draw(g);
        }

        // Draw Player
        model.getPlayer().draw(g);

        // THE DEATH SCREEN OVERLAY (Top layer)
        if (model.isGameOver()) {

            g2d.setColor(new Color(0, 0, 0, 170)); // Black with 170/255 transparency
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // "GAME OVER"
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 70));
            String mainText = "ICE CRASHED!";
            drawCenteredString(g2d, mainText, getHeight() / 2 - 40);

            // Final Score
            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
            String scoreText = "Final Score: " + model.getPlayer().score;
            drawCenteredString(g2d, scoreText, getHeight() / 2 + 30);

            // Hint
            g2d.setFont(new Font("Arial", Font.ITALIC, 18));
            g2d.setColor(Color.LIGHT_GRAY);
            drawCenteredString(g2d, "Press SPACE to return to Menu", getHeight() / 2 + 100);
        }
    }

    public void updateScoreText (){
        scoreBoard.setText("Score: "+model.getPlayer().score);
    }

    private void drawCenteredString(Graphics2D g2d, String text, int y) {
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        g2d.drawString(text, x, y);
    }
}
