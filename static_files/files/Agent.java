package app.gameengine.model.gameobjects;

import java.util.ArrayList;

import app.gameengine.Level;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.Timer;
import app.games.roguelikeobjects.PathTile;

public abstract class Agent extends DynamicGameObject {

    private Timer timer = new Timer(1);
    private ArrayList<PathTile> tiles = new ArrayList<>();

    @Override
    public void update(double dt, Level level) {
        if (timer.tick(dt)) {
            level.getStaticObjects().removeAll(tiles);
            tiles.clear();
            LinkedListNode<Vector2D> copy = this.path;
            for (double i = 0; copy != null; copy = copy.getNext(), i++) {
                PathTile tile = new PathTile(copy.getValue().getX(), copy.getValue().getY());
                level.getStaticObjects().add(tile);
                tiles.add(tile);
            }
        }
    }

}
