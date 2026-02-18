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
            // 1. ALWAYS move clouds (this makes the menu look alive)
            model.moveClouds(model.getClouds());

            // 2. ONLY move game objects if the game is NOT over AND the menu isn't showing
            // We check a new flag or just if the game is active
            if (!model.isGameOver() && view.isShowing()) {
                model.moveObstacle(model.getObstacles());
                model.getPlayer().updateY();
                model.score();
                view.updateScoreText();
                model.timeForNewObstacle(model.getObstacles());
                model.removeFirst(model.getObstacles());

                if (model.inHitBox(model.getObstacles(), model.getPlayer())) {
                    model.setGameOver(true);
                    // We DON'T stop the timer anymore, so clouds keep drifting
                }
            }

            // 3. Repaint both to ensure the Menu OR the Game updates
            view.repaint();
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

            // 1. If on Death Screen -> Go to Menu
            if (model.isGameOver()) {
                layout.show(container, "MENU");
                model.setGameOver(false); // Clean the state

                // THE FIX: Tell the container to give the Menu the focus
                container.requestFocusInWindow();
            }

            // 2. If on Menu -> Start Game
            // We check if the game view is hidden to know we are on the menu
            else if (!view.isShowing()) {
                model.reset();
                layout.show(container, "GAME");

                // THE FIX: Tell the container to give the Game the focus
                view.requestFocusInWindow();
            }

            // 3. If playing -> Jump
            else {
                model.playerJump();
            }

            view.repaint();
            container.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {/*System.out.println("release");*/}
    @Override public void keyTyped(KeyEvent e) {/*System.out.println("Typed");*/}
}
