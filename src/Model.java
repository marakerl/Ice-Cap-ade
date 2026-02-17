import java.util.LinkedList;

public class Model {
    private LinkedList<Obstacle> obstacles;
    private Player player;
    private LinkedList<Clouds> clouds;
    private int stepJump;
    private boolean jumping;
    boolean bool = false;

    public Model (){
        obstacles = new LinkedList<>();
        player = new Player(200,500,50,50);
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

    public void addClouds(LinkedList<Clouds> clouds){
        Clouds c = new Clouds();
        clouds.add(c);
    }

    public void moveClouds(LinkedList<Clouds> clouds){
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
        Obstacle o = os.getFirst();

        boolean horizontal = p.x + p.WIDTH > o.x && p.x < o.x + o.WIDTH;
        boolean hitsTopPipe = p.y < o.yTop + o.HEIGHT;
        boolean hitsBottomPipe = p.y + p.HEIGHT > o.yBottom;

        return horizontal && (hitsTopPipe || hitsBottomPipe);
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
        player = new Player(200,500,50,50);
        addObstacle(obstacles);
    }

}
