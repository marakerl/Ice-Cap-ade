import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JPanel {
    private JButton startButton = new JButton("Start Game");

    public MenuView(ActionListener startAction) {
        this.setLayout(new GridBagLayout()); // Centers the button
        this.setBackground(Color.DARK_GRAY);

        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(startAction);
        this.add(startButton);
    }
}