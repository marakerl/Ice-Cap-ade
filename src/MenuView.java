import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage; // Added for caching

public class MenuView extends JPanel {
    private Model model;
    private BufferedImage menuSkyCache; // The "frozen" background image

    public MenuView(Model model, ActionListener startAction) {
        this.model = model;
        this.setLayout(new GridBagLayout());
        this.setFocusable(true); // Ensures it can grab keyboard focus

        GridBagConstraints gbc = new GridBagConstraints();

        // Title Label
        JLabel title = new JLabel("ICE CAP-ADE");
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setForeground(Color.WHITE);

        // Start Button
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(250, 60));
        startButton.setFocusable(false); // Keeps focus on the panel for Spacebar
        startButton.setFocusPainted(false);
        startButton.setBackground(new Color(255, 255, 255));

        startButton.addActionListener(startAction);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        this.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(startButton, gbc);
    }

    private void createMenuSkyCache() {
        if (getWidth() <= 0 || getHeight() <= 0) return;

        menuSkyCache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = menuSkyCache.createGraphics();

        // Draw the gradient ONCE
        GradientPaint sky = new GradientPaint(0, 0, new Color(0, 102, 204),
                0, getHeight(), new Color(153, 204, 255));
        g2d.setPaint(sky);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Optimization: Skip super.paintComponent(g) to avoid double-painting
        Graphics2D g2d = (Graphics2D) g;

        // Manage Cache
        if (menuSkyCache == null || menuSkyCache.getWidth() != getWidth()) {
            createMenuSkyCache();
        }

        // Draw background (Extremely fast)
        if (menuSkyCache != null) {
            g2d.drawImage(menuSkyCache, 0, 0, null);
        }

        // Draw Clouds from the Model
        // Use Speed hint for moving clouds
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        if (model.getClouds() != null) {
            for (Clouds c : model.getClouds()) {
                c.draw(g);
            }
        }

        // 4. Draw Frost overlay
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillRect(0, getHeight() - 100, getWidth(), 100);
    }
}