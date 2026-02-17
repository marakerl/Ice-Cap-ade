import java.awt.*;
import java.util.LinkedList;

public class Model {
    private LinkedList<Obstacle> obstacles;
    private Player player;
    private LinkedList<Clouds> clouds;
    private int stepJump;
    private boolean jumping;
    boolean bool = false;
    private int playerWidth = 200;
    private int playerStartX = 200;
    private int playerStartY = 500;

    public Model (){
        clouds = new LinkedList<>();
        clouds.add(new Clouds(200, 100, 1)); // Slow cloud
        clouds.add(new Clouds(600, 250, 2)); // Slightly faster cloud
        clouds.add(new Clouds(900, 50, 1));
        obstacles = new LinkedList<>();
        player = new Player(playerStartX,playerStartY,playerWidth);
        addObstacle(obstacles);
    }

    public void addObstacle (LinkedList<Obstacle> os){
        Obstacle o = new Obstacle();
        os.add(o);
    }

    public void moveObstacle (LinkedList <Obstacle> os) {
        for (Obstacle o : os) {
            o.updateX();
        }
    }

    public void updateWorld() {
        moveObstacle(obstacles);
        moveClouds(clouds); // Move clouds every tick

        // Loop clouds: if a cloud leaves the screen, wrap it back to the right
        for (Clouds c : clouds) {
            if (c.x < -200) {
                c.x = 1100;
            }
        }
    }

    public void moveClouds(LinkedList<Clouds> clouds) {
        for (Clouds c : clouds) {
            c.updateX();
        }
    }

    public void movePlayer (int dy) {
        player.updateY();
    }

    public void playerJump () {
        player.jump();
    }

    public void timeForNewObstacle (LinkedList<Obstacle> os) {
        if (os.getFirst().x < 500-os.getFirst().WIDTH && os.getFirst().timeForNew == false) {
            addObstacle(os);
            os.getFirst().setTimeForNew(true);
        }
    }

    public void removeFirst (LinkedList<Obstacle> os) {
        if (os.getFirst().x < 0-os.getFirst().WIDTH ) {
            os.removeFirst();
        }
    }

    public boolean inHitBox (LinkedList<Obstacle> os, Player p) {
        Rectangle playerRect = p.getBounds();

        for (Obstacle o : os) {
            // If player hits the top pipe OR the bottom pipe of this obstacle
            if (playerRect.intersects(o.getTopBounds()) ||
                    playerRect.intersects(o.getBottomBounds())) {
                return true;
            }
        }

        // Check floor/ceiling
        if (p.y < 0 || p.y + p.HEIGHT > 1000) return true;

        return false;
    }

    public void score () {
        if (player.x > obstacles.getFirst().x && bool==false) {
            player.giveScore();
            bool = true;
        }
        if (player.x < obstacles.getFirst().x && bool==true) {
            bool = false;
        }
    }

    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public LinkedList<Clouds> getClouds() {return clouds;}

    public boolean isJumping() {
        return jumping;
    }

    public void reset() {
        obstacles.remove();
        obstacles = new LinkedList<>();
        player = new Player(playerStartX,playerStartY,playerWidth);
        addObstacle(obstacles);
    }

}
