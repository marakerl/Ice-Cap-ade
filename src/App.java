
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args){
        //Setup MVC
        Model model = new Model();
        View gameView = new View(model);
        Controller controller = new Controller(model, gameView);
        gameView.init(controller);

        //Setup the Window and layout
        JFrame frame = new JFrame("Ice Cap-ade");
        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);


        //Menu and define start action
        MenuView menuView = new MenuView(e -> {
            layout.show(container, "GAME");
            gameView.requestFocusInWindow();
            controller.initTick();
        });

        //Add containers
        container.add(menuView, "MENU");
        container.add(gameView, "GAME");

        //Frame setup
        frame.add(container);
        frame.pack(); // This makes the inner area EXACTLY 1000x1000
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Show menu first
        layout.show(container, "MENU");
    }
}
