package game.panel.battle;

import game.action.Engine;
import game.action.TankActionsThread;
import game.action.TankSpawnsThread;
import game.battlefield.BattleField;
import game.object.BFObject;
import game.object.powerup.PowerUp;
import game.object.tank.Bullet;
import game.object.tank.Direction;
import game.object.tank.Tank;
import game.object.tank.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static game.action.Engine.*;

public class BattleFieldPanel extends JPanel implements Runnable {

    private final Engine engine;
    private final List<Tank> tanks = new CopyOnWriteArrayList<>();
    private final List<Tank> aggressiveTanks = new CopyOnWriteArrayList<>();
    private final Map<String, Player> keyListenerTanks = new ConcurrentHashMap<>();
    private final List<Bullet> bullets = new CopyOnWriteArrayList<>();
    private final List<PowerUp> powerUps = new CopyOnWriteArrayList<>();
    private final BattleField battleField;

    private boolean aggressorsDestroyed = false;
    private TankSpawnsThread tankSpawnsThread;
    private boolean drawing = true;

    public BattleFieldPanel(Engine engine, boolean secondPlayer) {
        this.engine = engine;
        this.battleField = engine.getBattleField();

        setPreferredSize(new Dimension(BattleField.WIDTH, BattleField.HEIGHT));

        addKeyListener(new BattlePanelKeyListener(keyListenerTanks, engine));

        init(secondPlayer);
    }

    private void init(boolean secondPlayer) {
        initTanks(secondPlayer);
        initDraw();
    }

    private void initTanks(boolean secondPlayer) {
        Player defenderTank = new Player(128, 0, Tank.DEFENDER_TEAM, "Deprecated", this, Direction.UP);
        tanks.add(defenderTank);
        keyListenerTanks.put(FIRST_DEFENDER, defenderTank);
        new TankActionsThread(defenderTank, this).start();

        if (secondPlayer) {
            Player secondDefenderTank = new Player(384, 512, Tank.DEFENDER_TEAM, "Deprecated", this, Direction.UP);
            tanks.add(secondDefenderTank);
            keyListenerTanks.put(SECOND_DEFENDER, secondDefenderTank);
            new TankActionsThread(secondDefenderTank, this).start();
        }

        tankSpawnsThread = new TankSpawnsThread(this);
        tankSpawnsThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        battleField.draw(g);

        for (Tank tank : tanks) {
            tank.draw(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }

        battleField.drawShrubs(g);
    }

    @Override
    public void run() {
        while (isGameInProgress()) {
            repaint();
            Engine.sleep(1000 / FPS);
        }

        long afterGameEndedMillis = System.currentTimeMillis() + 2000;

        while (afterGameEndedMillis > System.currentTimeMillis()) {
            repaint();
            Engine.sleep(1000 / FPS);
        }

        drawing = false;
        engine.startMainPanel();
    }

    public void spawnPowerUp() {
        powerUps.add(battleField.getRandomPowerUp(this));
    }

    public boolean isGameInProgress() {
        return !keyListenerTanks.get(FIRST_DEFENDER).isDestroyed() &&
                (keyListenerTanks.get(SECOND_DEFENDER) == null || !keyListenerTanks.get(SECOND_DEFENDER).isDestroyed()) &&
                (!aggressorsDestroyed || !aggressiveTanks.isEmpty()) && !battleField.getEagle().isDestroyed();
    }

    public void setAggressorsDestroyed(boolean aggressorsDestroyed) {
        this.aggressorsDestroyed = aggressorsDestroyed;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public List<Tank> getAggressiveTanks() {
        return aggressiveTanks;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public BFObject getBlock(int v, int h) {
        return battleField.getBlock(v, h);
    }

    public void destroyBlock(int v, int h) {
        battleField.destroyBlock(v, h);
    }

    public PowerUp findPowerUp(int x, int y) {
        for (PowerUp powerUp : powerUps) {
            if (powerUp.getX() == x && powerUp.getY() == y) {
                return powerUp;
            }
        }
        return null;
    }

    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }

    public void initRockWall() {
        battleField.initRockWall();
    }

    public void setCreateNewTank(boolean createNewTank) {
        tankSpawnsThread.setCreateNewTank(createNewTank);
    }

    public Engine getEngine() {
        return engine;
    }

    public Player getPlayer(String tankName) {
        return keyListenerTanks.get(tankName);
    }

    public BFObject getEagle() {
        return battleField.getEagle();
    }

    boolean isDrawing() {
        return drawing;
    }

    public int getTankAggressorSpawnedCount() {
        return tankSpawnsThread.getTankAggressorSpawnedCount();
    }

    private void initDraw() {
        new Thread(this).start();
    }
}

