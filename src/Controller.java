import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{

    Model model;
    View view;
    Timer tick;
    private int tickSpeed = 10;
    private JPanel container;
    private CardLayout layout;


    public Controller (Model m, View v, JPanel container, CardLayout layout) {
        this.model = m;
        this.view = v;
        this.container = container;
        this.layout = layout;
    }

    public void initTick () {
        tick = new Timer(tickSpeed, e -> {
            model.moveObstacle(model.getObstacles());
            model.moveClouds(model.getClouds());
            model.getPlayer().updateY();
            model.score();
            view.updateScoreText();
            model.timeForNewObstacle(model.getObstacles());
            model.removeFirst(model.getObstacles());

            if(model.inHitBox(model.getObstacles(),model.getPlayer())) {
                stopGameAndReturnToMenu();
            }
            view.repaint();
        });
    }

    private void stopGameAndReturnToMenu() {
        tick.stop();
        layout.show(container, "MENU");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            /*if (!tick.isRunning()) {
                tick.start();
                model.reset();
            }*/
            model.playerJump();
            view.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {/*System.out.println("release");*/}
    @Override public void keyTyped(KeyEvent e) {/*System.out.println("Typed");*/}
}
