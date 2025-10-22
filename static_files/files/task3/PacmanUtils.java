package app.gameengine.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import app.gameengine.model.physics.Vector2D;
import app.games.pacman.Ghost;
import app.games.pacman.PacmanGame;
import app.games.pacman.PacmanLevel;

/**
 * Static class containing utilities for Pacman.
 * 
 * @see PacmanGame
 * @see PacmanLevel
 */
public class PacmanUtils {

    /**
     * Returns whether a ghost is allowed to take action given its current state.
     * Actions include chasing, scattering, fleeing, and returning home. A ghost may
     * act if two things are true:
     * <p>
     * 1) They are either near the center of their current tile, or they are not
     * moving, and
     * <p>
     * 2) The last whole tile they were on is either null or they are closer to
     * another tile than that tile.
     * 
     * @param ghost    the ghost
     * @param dt       the time elapsed since the last update, in seconds
     * @param lastTile the last whole tile the ghost was on
     * @return whether the ghost is allowed to act
     */
    public static boolean canAct(Ghost ghost, double dt, Vector2D lastTile) {
        return (PacmanUtils.isNearTile(ghost, dt) || ghost.getVelocity().equals(new Vector2D(0, 0)))
                && (lastTile == null || !Vector2D.round(ghost.getLocation()).equals(lastTile));
    }

}
