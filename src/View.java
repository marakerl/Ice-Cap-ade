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
        super.paintComponent(g);
        /*for (Clouds c : model.getClouds()) {
            c.draw(g);
        }*/
        for (Obstacle o : model.getObstacles()) {
            o.draw(g);
        }
        model.getPlayer().draw(g);
    }

    public void updateScoreText (){
        scoreBoard.setText("Score: "+model.getPlayer().score);
    }
}
