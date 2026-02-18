import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JPanel {
    private Model model;

    public MenuView(Model model, ActionListener startAction) {
        // Use GridBagLayout to center everything perfectly
        this.model = model;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. Title Label
        JLabel title = new JLabel("ICE CAP-ADE");
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setForeground(Color.WHITE);

        // 2. Start Button
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(250, 60));
        startButton.setFocusPainted(false); // Removes the annoying box around text
        startButton.setBackground(new Color(255, 255, 255));

        // Link the button to the action passed from App.java
        startButton.addActionListener(startAction);

        // Add Title to layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0); // Add space below title
        this.add(title, gbc);

        // Add Button to layout
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(startButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // 1. Draw the Sky Gradient
        GradientPaint sky = new GradientPaint(0, 0, new Color(0, 102, 204),
                0, getHeight(), new Color(153, 204, 255));
        g2d.setPaint(sky);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw Clouds from the Model
        if (model.getClouds() != null) {
            for (Clouds c : model.getClouds()) {
                c.draw(g);
            }
        }

        // 3. Draw Frost overlay
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillRect(0, getHeight() - 100, getWidth(), 100);
    }
}