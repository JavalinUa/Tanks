package game.battlefield;

import game.custom.Pair;
import game.object.BFObject;
import game.object.block.Void;
import game.object.block.*;
import game.object.powerup.*;
import game.panel.battle.BattleFieldPanel;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static game.action.Engine.FIRST_DEFENDER;

public class BattleField {

    public static final int WIDTH;
    public static final int HEIGHT = WIDTH = 576;

    public static final int SQUARE_SIZE = 64;

    public static final int MIN_SQUARE = 0;
    public static final int WIDTH_MAX_SQUARE = WIDTH - SQUARE_SIZE;
    public static final int HEIGHT_MAX_SQUARE = HEIGHT - SQUARE_SIZE;

    public static final String BRICK = "B";
    public static final String EAGLE = "E";
    public static final String ROCK = "R";
    public static final String WATER = "W";
    public static final String SHRUB = "S";
    public static final String VOID = "V";

    private static final Random RANDOM = new Random();
    //    private static final Class<PowerUp>[] powerUpClasses = new Class[]{Grenade.class, Helmet.class, Shovel.class, Star.class, Timer.class};
    private static final Class<PowerUp>[] powerUpClasses = new Class[]{Tank.class};

    private static final int ROCK_WALL_PERIOD = 5_000;
    private static final int ROCK_WALL_FLASHING_DELAY = 500;
    private static final int ROCK_WALL_FLASHING_PERIOD = 3_000;
    private static final Pair[] ROCK_WALL_COORDINATE_PAIRS =
            new Pair[]{new Pair(3, 7), new Pair(4, 7), new Pair(5, 7), new Pair(3, 8), new Pair(5, 8)};

    private Eagle eagle;
    private final BFObject[][] bfObjects = new BFObject[9][9];
    private final List<BFObject> shrubBlocks = new ArrayList<>();
    private final List<BFObject> voidOrDestroyedBlocks = new CopyOnWriteArrayList<>();

    private final BattleFieldMaps battleFieldMaps = new BattleFieldMaps();

    private long rockWallBorderTime;
    private boolean rockWallExists = false;

    private Pair firstDefenderCoordinates;

    public void setMap(int mapIdx) {
        shrubBlocks.clear();
        voidOrDestroyedBlocks.clear();

        String[][] map = battleFieldMaps.getMap(mapIdx);

        fillBattleFieldByMap(map);
    }

    public void fillBattleFieldByMap(String[][] map) {
        for (int v = 0; v < map.length; v++) {
            for (int h = 0; h < map[0].length; h++) {
                String block = map[v][h];

                if (BRICK.equals(block)) {
                    bfObjects[v][h] = new Brick(h * SQUARE_SIZE, v * SQUARE_SIZE);
                } else if (ROCK.equals(block)) {
                    bfObjects[v][h] = new Rock(h * SQUARE_SIZE, v * SQUARE_SIZE);
                } else if (WATER.equals(block)) {
                    bfObjects[v][h] = new Water(h * SQUARE_SIZE, v * SQUARE_SIZE);
                } else if (VOID.equals(block)) {
                    addVoidBlock(h, v);
                } else if (SHRUB.equals(block)) {
                    bfObjects[v][h] = new Void(h * SQUARE_SIZE, v * SQUARE_SIZE);
                    shrubBlocks.add(new Shrub(h * SQUARE_SIZE, v * SQUARE_SIZE));
                } else if (EAGLE.equals(block)) {
                    eagle = new Eagle(h * SQUARE_SIZE, v * SQUARE_SIZE);
                    bfObjects[v][h] = eagle;
                } else if (FIRST_DEFENDER.equals(block)) {
                    firstDefenderCoordinates = new Pair(h * SQUARE_SIZE, v * SQUARE_SIZE);
                    addVoidBlock(h, v);
                }
            }
        }
    }

    private void addVoidBlock(int h, int v) {
        Void voidBlock = new Void(h * SQUARE_SIZE, v * SQUARE_SIZE);
        bfObjects[v][h] = voidBlock;
        voidOrDestroyedBlocks.add(voidBlock);
    }

    public void draw(Graphics g) {
        if (rockWallExists) {
            setRockWallImageCoordinates();
        }

        for (BFObject[] block : bfObjects) {
            for (int h = 0; h < bfObjects[0].length; h++) {
                block[h].draw(g);
            }
        }
    }

    private void setRockWallImageCoordinates() {
        if (rockWallBorderTime > System.currentTimeMillis()) {
            if (rockWallBorderTime - System.currentTimeMillis() < ROCK_WALL_FLASHING_PERIOD) {
                for (Pair pair : ROCK_WALL_COORDINATE_PAIRS) {
                    BFObject bfObject = bfObjects[pair.y][pair.x];

                    if (System.currentTimeMillis() % ROCK_WALL_FLASHING_DELAY < ROCK_WALL_FLASHING_DELAY / 2) {
                        bfObject.setImageCoordinates(256, 0);
                    } else {
                        bfObject.setImageCoordinates(256, 16);
                    }
                }
            }
        } else {
            rockWallExists = false;

            for (Pair pair : ROCK_WALL_COORDINATE_PAIRS) {
                if (!bfObjects[pair.y][pair.x].isDestroyed()) {
                    bfObjects[pair.y][pair.x] = new Brick(pair.x * SQUARE_SIZE, pair.y * SQUARE_SIZE);
                }
            }
        }
    }

    public void drawShrubs(Graphics g) {
        for (BFObject shrub : shrubBlocks) {
            shrub.draw(g);
        }
    }

    public void initRockWall() {
        for (Pair pair : ROCK_WALL_COORDINATE_PAIRS) {
            bfObjects[pair.y][pair.x] = new Rock(pair.x * SQUARE_SIZE, pair.y * SQUARE_SIZE);
        }

        rockWallBorderTime = System.currentTimeMillis() + ROCK_WALL_PERIOD;
        rockWallExists = true;
    }

    public BFObject getBlock(int v, int h) {
        return bfObjects[v][h];
    }

    public void destroyBlock(int v, int h) {
        bfObjects[v][h].destroy();
        voidOrDestroyedBlocks.add(bfObjects[v][h]);
    }

    public Eagle getEagle() {
        return eagle;
    }

    public PowerUp getRandomPowerUp(BattleFieldPanel battleFieldPanel) {
        BFObject randomVoid = getRandomVoidOrDestroyedBlock();
        Class<PowerUp> powerUpClass = getRandomPowerUpClass();

        try {
            Constructor<PowerUp> powerUpConstructor = powerUpClass.getDeclaredConstructor(int.class, int.class, BattleFieldPanel.class);
            return powerUpConstructor.newInstance(randomVoid.getX(), randomVoid.getY(), battleFieldPanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BFObject getRandomVoidOrDestroyedBlock() {
        return voidOrDestroyedBlocks.get(RANDOM.nextInt(voidOrDestroyedBlocks.size()));
    }

    public Class<PowerUp> getRandomPowerUpClass() {
        return powerUpClasses[RANDOM.nextInt(powerUpClasses.length)];
    }

    public Pair getFirstDefenderCoordinates() {
        return firstDefenderCoordinates;
    }

    public int getMapSize() {
        return battleFieldMaps.getMapSize();
    }
}
