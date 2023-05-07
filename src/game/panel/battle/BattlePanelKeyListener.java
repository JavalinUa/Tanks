package game.panel.battle;

import game.action.Engine;
import game.object.tank.Action;
import game.object.tank.Direction;
import game.object.tank.Player;
import game.object.tank.Tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

import static game.action.Engine.FIRST_DEFENDER;
import static game.action.Engine.SECOND_DEFENDER;

public class BattlePanelKeyListener extends KeyAdapter {

    private final Map<String, Player> keyListenerTanks;
    private final Engine engine;
    private final ConcurrentSkipListSet<Integer> pressed = new ConcurrentSkipListSet<>();

    private final Map<Tank, Integer> eventByTank = new HashMap<>();

    private static final Map<Integer, Direction> directionByEvent = new HashMap<>();

    static {
        //first defender
        directionByEvent.put(KeyEvent.VK_UP, Direction.UP);
        directionByEvent.put(KeyEvent.VK_DOWN, Direction.DOWN);
        directionByEvent.put(KeyEvent.VK_LEFT, Direction.LEFT);
        directionByEvent.put(KeyEvent.VK_RIGHT, Direction.RIGHT);

        //second defender
        directionByEvent.put(KeyEvent.VK_W, Direction.UP);
        directionByEvent.put(KeyEvent.VK_S, Direction.DOWN);
        directionByEvent.put(KeyEvent.VK_A, Direction.LEFT);
        directionByEvent.put(KeyEvent.VK_D, Direction.RIGHT);
    }

    public BattlePanelKeyListener(Map<String, Player> keyListenerTanks, Engine engine) {
        this.keyListenerTanks = keyListenerTanks;
        this.engine = engine;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (engine.isWriteRecord())
//            processFirstDefender(e.getKeyCode());
            processKeyCode(e.getKeyCode());
    }

    private void processKeyCode(int keyCode) {
        if (isFirstDefenderAction(keyCode)) {
            processAction(getFirstDefender(), KeyEvent.VK_SPACE, keyCode);
//            if (keyCode == KeyEvent.VK_SPACE) {
//                eventByTank.put(getFirstDefender(), keyCode);
//                getFirstDefender().setAction(Action.FIRE);
//            } else if (getFirstDefender().getDirection().equals(directionByEvent.get(keyCode))) {
//                eventByTank.put(getFirstDefender(), keyCode);
//                getFirstDefender().setAction(Action.MOVE);
//            } else {
//                getFirstDefender().setDirection(directionByEvent.get(keyCode));
//                getFirstDefender().setAction(Action.NONE);
//            }
        } else if (isSecondDefenderAction(keyCode)) {
            processAction(getSecondDefender(), KeyEvent.VK_E, keyCode);
        }
    }

    private void processAction(Tank tank, int fireKeyCode, int pressedKeyCode) {
        if (tank.isSpawned()) {
            if (pressedKeyCode == fireKeyCode) {
                eventByTank.put(tank, pressedKeyCode);
                tank.setAction(Action.FIRE);
            } else if (tank.getDirection().equals(directionByEvent.get(pressedKeyCode))) {
                eventByTank.put(tank, pressedKeyCode);
                tank.setAction(Action.MOVE);
            } else {
                tank.setDirection(directionByEvent.get(pressedKeyCode));
                tank.setAction(Action.NONE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (engine.isWriteRecord()) {
            if (isFirstDefenderAction(e.getKeyCode()) && eventByTank.remove(getFirstDefender()) != null) {
                getFirstDefender().setAction(Action.NONE);
            } else if (isSecondDefenderAction(e.getKeyCode()) && eventByTank.remove(getSecondDefender()) != null) {
                getSecondDefender().setAction(Action.NONE);
            }
        }
    }

    private boolean isFirstDefenderAction(int keyCode) {
        return keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_SPACE;
    }

    private boolean isSecondDefenderAction(int keyCode) {
        return keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S ||
                keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_E;
    }

    private Tank getFirstDefender() {
        return keyListenerTanks.get(FIRST_DEFENDER);
    }

    private Tank getSecondDefender() {
        return keyListenerTanks.get(SECOND_DEFENDER);
    }
}
