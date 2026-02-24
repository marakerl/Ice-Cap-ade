import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{

    Model model;
    View view;
    Timer tick;
    private int tickSpeed = 17;
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
            model.moveClouds(model.getClouds());

            if (!model.isGameOver() && view.isShowing()) {
                model.moveObstacle(model.getObstacles());
                model.getPlayer().updateY();
                model.score();
                view.updateScoreText();
                model.timeForNewObstacle(model.getObstacles());
                model.removeFirst(model.getObstacles());

                if (model.inHitBox(model.getObstacles(), model.getPlayer())) {
                    model.setGameOver(true);
                }
            }

            container.repaint();
        });

        tick.start();
    }

    private void stopGameAndReturnToMenu() {
        tick.stop();
        layout.show(container, "MENU");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            // If on Death Screen -> Go to Menu
            if (model.isGameOver()) {
                layout.show(container, "MENU");
                model.setGameOver(false); // Clean the state

                container.requestFocusInWindow();
            }

            // If on Menu -> Start Game
            else if (!view.isShowing()) {
                model.reset();
                layout.show(container, "GAME");

                view.requestFocusInWindow();
            }
            //if playing jump
            else {
                model.playerJump();
            }

            container.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {/*System.out.println("release");*/}
    @Override public void keyTyped(KeyEvent e) {/*System.out.println("Typed");*/}
}
