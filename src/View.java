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
    }

    public void updateScoreText (){
        scoreBoard.setText("Score: "+model.getPlayer().score);
    }
}
