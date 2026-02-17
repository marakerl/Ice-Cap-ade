import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{

    Model model;
    View view;
    Timer tick;
    private int tickSpeed = 10;


    public Controller (Model m, View v) {
        this.model = m;
        this.view = v;
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
                tick.stop();
            }
            view.repaint();
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!tick.isRunning()) {
                tick.start();
                model.reset();
            }
            model.playerJump();
            view.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {/*System.out.println("release");*/}
    @Override public void keyTyped(KeyEvent e) {/*System.out.println("Typed");*/}
}
