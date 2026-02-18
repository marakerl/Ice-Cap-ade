import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class View extends JPanel {

    private Model model;
    private JFrame frame;
    private JTextArea scoreBoard;
    private CardLayout cl;

    public View(Model model) {
        this.model = model;
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setFocusable(true);
        this.setBackground(Color.CYAN);

        scoreBoard = new JTextArea();
        this.add(scoreBoard);
    }


    public void init(Controller controller) {
        this.addKeyListener(controller); // attach controller to view
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // 1. Draw the Sky Gradient (MUST be first to clear the screen)
        // From dark blue at top to light blue at bottom
        GradientPaint sky = new GradientPaint(0, 0, new Color(0, 102, 204),
                0, getHeight(), new Color(153, 204, 255));
        g2d.setPaint(sky);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw Clouds (Behind the pipes)
        if (model.getClouds() != null) {
            for (Clouds c : model.getClouds()) {
                c.draw(g);
            }
        }

        // 3. Draw Obstacles (On top of clouds)
        for (Obstacle o : model.getObstacles()) {
            o.draw(g);
        }

        // 4. Draw Player (On top of everything)
        model.getPlayer().draw(g);

        // 5. THE DEATH SCREEN OVERLAY (Top layer)
        if (model.isGameOver()) {
            // A. Draw a semi-transparent "dimmer" over the whole screen
            g2d.setColor(new Color(0, 0, 0, 170)); // Black with 170/255 transparency
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // B. Set up text rendering (Anti-aliasing makes text look smooth)
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // C. Draw "GAME OVER"
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 70));
            String mainText = "ICE CRASHED!";
            drawCenteredString(g2d, mainText, getHeight() / 2 - 40);

            // D. Draw Final Score
            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
            String scoreText = "Final Score: " + model.getPlayer().score;
            drawCenteredString(g2d, scoreText, getHeight() / 2 + 30);

            // E. Draw Hint
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
