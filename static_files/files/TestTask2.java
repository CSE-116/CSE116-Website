package app.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import app.gameengine.Level;
import app.gameengine.LevelParser;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.Player;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.gameengine.model.physics.PhysicsEngineWithGravity;
import app.gameengine.model.physics.Vector2D;
import app.games.mario.Block;
import app.games.mario.Flag;
import app.games.mario.Goomba;
import app.games.mario.HiddenBlock;
import app.games.mario.Koopa;
import app.games.mario.MarioGame;
import app.games.mario.MarioLevel;
import app.games.mario.PipeEnd;
import app.games.mario.PipeStem;
import app.games.mario.QuestionBlock;
import app.games.topdownobjects.Demon;
import app.games.topdownobjects.TopDownLevel;

public class TestTask2 {

    public static final double EPSILON = 1e-5;

    // PhysicsEngineWithGravity

    @Test
    public void testPhysicsEngineSimple() {
        PhysicsEngineWithGravity engine = new PhysicsEngineWithGravity(25);
        Demon d1 = new Demon(0, 0);
        d1.setOnGround(false);

        engine.updateObject(1, d1);
        assertEquals(new Vector2D(0, 25), d1.getLocation());
        assertEquals(new Vector2D(0, 25), d1.getVelocity());

        engine.updateObject(1, d1);
        assertEquals(new Vector2D(0, 75), d1.getLocation());
        assertEquals(new Vector2D(0, 50), d1.getVelocity());

        engine.updateObject(1, d1);
        assertEquals(new Vector2D(0, 150), d1.getLocation());
        assertEquals(new Vector2D(0, 75), d1.getVelocity());

        d1 = new Demon(0, 0);
        d1.setOnGround(false);

        d1.getVelocity().setY(-10);
        engine.updateObject(10, d1);
        assertEquals(new Vector2D(0, 2400), d1.getLocation());
        assertEquals(new Vector2D(0, 240), d1.getVelocity());

        d1 = new Demon(0, 0);
        d1.setOnGround(false);

        d1.getVelocity().setY(10);
        engine.updateObject(10, d1);
        assertEquals(new Vector2D(0, 2600), d1.getLocation());
        assertEquals(new Vector2D(0, 260), d1.getVelocity());

    }

    @Test
    public void testGravityGetSet() {
        PhysicsEngineWithGravity engine = new PhysicsEngineWithGravity();
        assertEquals(40, engine.getGravity(), EPSILON);

        Demon d = new Demon(0, 0);
        d.setOnGround(false);

        engine.updateObject(1, d);
        assertEquals(new Vector2D(0, 40), d.getVelocity());

        engine.setGravity(10);
        assertEquals(10, engine.getGravity(), EPSILON);

        engine.updateObject(1, d);
        assertEquals(new Vector2D(0, 50), d.getVelocity());
    }

    @Test
    public void testPhysicsEngineIgnoresPlayer() {
        PhysicsEngineWithGravity engine = new PhysicsEngineWithGravity(25);
        Player p1 = new Player(0, 0, 10);
        p1.setOnGround(false);

        engine.updateObject(1, p1);
        assertEquals(new Vector2D(0, 0), p1.getLocation());
        assertEquals(new Vector2D(0, 0), p1.getVelocity());

        engine.updateObject(1, p1);
        assertEquals(new Vector2D(0, 0), p1.getLocation());
        assertEquals(new Vector2D(0, 0), p1.getVelocity());

        p1.setVelocity(10, 20);

        engine.updateObject(0.5, p1);
        assertEquals(new Vector2D(5, 10), p1.getLocation());
        assertEquals(new Vector2D(10, 20), p1.getVelocity());

        p1 = new Player(0, 0, 10);
        p1.setOnGround(true);
        assertEquals(new Vector2D(0, 0), p1.getLocation());
        assertEquals(new Vector2D(0, 0), p1.getVelocity());

        engine.updateObject(1, p1);
        assertEquals(new Vector2D(0, 0), p1.getLocation());
        assertEquals(new Vector2D(0, 0), p1.getVelocity());

        engine.updateObject(1, p1);
        assertEquals(new Vector2D(0, 0), p1.getLocation());
        assertEquals(new Vector2D(0, 0), p1.getVelocity());
    }

    @Test
    public void testPhysicsEngineIgnoresOnGround() {
        PhysicsEngineWithGravity engine = new PhysicsEngineWithGravity(25);
        Demon d1 = new Demon(0, 0);
        d1.setOnGround(true);

        engine.updateObject(1, d1);
        assertEquals(new Vector2D(0, 0), d1.getLocation());
        assertEquals(new Vector2D(0, 0), d1.getVelocity());

        engine.updateObject(1, d1);
        assertEquals(new Vector2D(0, 0), d1.getLocation());
        assertEquals(new Vector2D(0, 0), d1.getVelocity());

        d1.setVelocity(10, 20);

        engine.updateObject(0.5, d1);
        assertEquals(new Vector2D(5, 10), d1.getLocation());
        assertEquals(new Vector2D(10, 20), d1.getVelocity());
    }

    @Test
    public void testPhysicsEngineComplex() {
        PhysicsEngineWithGravity engine = new PhysicsEngineWithGravity(9.81);
        Demon d1 = new Demon(5.25, -12.2);
        d1.getVelocity().setX(8.9);

        engine.updateObject(0.12, d1);
        assertEquals(new Vector2D(6.318, -12.058736), d1.getLocation());
        assertEquals(new Vector2D(8.9, 1.1772), d1.getVelocity());

        engine.updateObject(6.45, d1);
        assertEquals(new Vector2D(63.723, 403.654729), d1.getLocation());
        assertEquals(new Vector2D(8.9, 64.4517), d1.getVelocity());

        engine.updateObject(0.0, d1);
        assertEquals(new Vector2D(63.723, 403.654729), d1.getLocation());
        assertEquals(new Vector2D(8.9, 64.4517), d1.getVelocity());

        d1.getVelocity().setX(-5.78);
        engine.updateObject(0.5, d1);
        assertEquals(new Vector2D(60.833, 438.333079), d1.getLocation());
        assertEquals(new Vector2D(-5.78, 69.3567), d1.getVelocity());
    }

    // LevelParser

    @Test
    public void testReadDynamicObject() {
        MarioGame game = new MarioGame();
        MarioLevel level = new MarioLevel(game, 100, 100, "test level");
        DynamicGameObject object = LevelParser.readDynamicObject(game, level,
                new ArrayList<>(Arrays.asList("DynamicGameObject", "Goomba", "25", "2")));
        assertTrue(object instanceof Goomba);
        assertEquals(new Vector2D(25, 2), object.getLocation());

        object = LevelParser.readDynamicObject(game, level,
                new ArrayList<>(Arrays.asList("DynamicGameObject", "Koopa", "25", "2")));
        assertTrue(object instanceof Koopa);
        assertEquals(new Vector2D(25, 2), object.getLocation());
    }

    @Test
    public void testReadStaticObject() {
        MarioGame game = new MarioGame();
        MarioLevel level = new MarioLevel(game, 100, 100, "test level");
        StaticGameObject object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "Block", "17", "12")));
        assertTrue(object instanceof Block);
        assertEquals(new Vector2D(17, 12), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "Bricks", "15", "2")));
        assertTrue(object instanceof Block);
        assertEquals(new Vector2D(15, 2), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "Ground", "6", "5")));
        assertTrue(object instanceof Block);
        assertEquals(new Vector2D(6, 5), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "QuestionBlock", "12", "14")));
        assertTrue(object instanceof QuestionBlock);
        assertEquals(new Vector2D(12, 14), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "HiddenBlock", "5", "10")));
        assertTrue(object instanceof HiddenBlock);
        assertEquals(new Vector2D(5, 10), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "PipeEnd", "23", "11")));
        assertTrue(object instanceof PipeEnd);
        assertEquals(new Vector2D(23, 11), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "PipeStem", "7", "4")));
        assertTrue(object instanceof PipeStem);
        assertEquals(new Vector2D(7, 4), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "Flag", "6", "7")));
        assertTrue(object instanceof Flag);
        assertEquals(new Vector2D(6, 7), object.getLocation());
        assertEquals(game, ((Flag) object).getGame());
    }

    @Test
    public void testParseMarioLevel() {
        MarioGame game = new MarioGame();
        Level level = LevelParser.parseLevel(game, "testing/mario1.csv");
        assertNotNull(level);
        assertTrue(level instanceof MarioLevel);

        level = LevelParser.parseLevel(game, "testing/mario2.csv");
        assertNotNull(level);
        assertTrue(level instanceof MarioLevel);

        level = LevelParser.parseLevel(game, "testing/medium.csv");
        assertNotNull(level);
        assertTrue(level instanceof TopDownLevel);
    }

}
