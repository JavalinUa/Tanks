package game.ai;

import game.action.Engine;
import game.object.BFObject;
import game.object.block.Brick;
import game.object.block.Rock;
import game.object.block.Water;
import game.object.tank.Action;
import game.object.tank.Direction;
import game.object.tank.Tank;
import game.object.tank.Player;

public class DestroyOpponent extends AbstractAI {

    public DestroyOpponent(Tank tank, Engine engine, BFObject target) {
        super(tank, engine, target);
    }

    @Override
    public void action() {
        if (tank.getX() == target.getX() && tank.getY() > target.getY()) {
            tank.setDirection(Direction.UP);
        } else if (tank.getX() == target.getX() && tank.getY() < target.getY()) {
            tank.setDirection(Direction.DOWN);
        } else if (tank.getX() > target.getX() && tank.getY() == target.getY()) {
            tank.setDirection(Direction.LEFT);
        } else if (tank.getX() < target.getX() && tank.getY() == target.getY()) {
            tank.setDirection(Direction.RIGHT);
        } else {
            doAction();
        }

        doRandomAction();

        tank.setAction(action);
    }

    private void doAction() {
        if (Math.abs(tank.getX() - target.getX()) < Math.abs(tank.getY() - target.getY())) {
            if (tank.getX() > target.getX()) {
                if (engine.getBFObjectLeft(tank) instanceof Water) {
                    tank.setDirection(Direction.DOWN);
                    if (canDestroyBelow()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                } else
                    tank.setDirection(Direction.LEFT);
                if (canDestroyLeft()) {
                    action = Action.FIRE;
                } else {
                    action = Action.MOVE;
                }
            } else {
                if (engine.getBFObjectRight(tank) instanceof Water) {
                    tank.setDirection(Direction.DOWN);
                    if (canDestroyBelow()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                } else {
                    tank.setDirection(Direction.RIGHT);
                    if (canDestroyRight()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                }
            }
        } else {
            if (tank.getY() > target.getY()) {
                if (engine.getBFObjectAbove(tank) instanceof Water) {
                    tank.setDirection(Direction.RIGHT);
                    if (canDestroyRight()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                } else {
                    tank.setDirection(Direction.UP);
                    if (canDestroyAbove()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                }
            } else {
                if (engine.getBFObjectBelow(tank) instanceof Water) {
                    tank.setDirection(Direction.RIGHT);
                    if (canDestroyRight()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                } else {
                    tank.setDirection(Direction.DOWN);
                    if (canDestroyBelow()) {
                        action = Action.FIRE;
                    } else {
                        action = Action.MOVE;
                    }
                }
            }
        }
    }

    private boolean canDestroyAbove() {
        return (engine.getBFObjectAbove(tank) instanceof Brick || (engine.getBFObjectAbove(tank) instanceof Rock && tank instanceof Player)) && !engine.getBFObjectAbove(tank).isDestroyed();
    }

    private boolean canDestroyBelow() {
        return (engine.getBFObjectBelow(tank) instanceof Brick || (engine.getBFObjectBelow(tank) instanceof Rock && tank instanceof Player)) && !engine.getBFObjectBelow(tank).isDestroyed();
    }

    private boolean canDestroyLeft() {
        return (engine.getBFObjectLeft(tank) instanceof Brick || (engine.getBFObjectLeft(tank) instanceof Rock && tank instanceof Player)) && !engine.getBFObjectLeft(tank).isDestroyed();
    }

    private boolean canDestroyRight() {
        return (engine.getBFObjectRight(tank) instanceof Brick || (engine.getBFObjectRight(tank) instanceof Rock && tank instanceof Player)) && !engine.getBFObjectRight(tank).isDestroyed();
    }

    private void doRandomAction() {
        if (random.nextInt(100) > 10) {
            randomDirection();
//            action = Action.MOVE;
        } else if (random.nextInt(100) > 20) {
            action = Action.NONE;
        } else if (random.nextInt(100) > 30) {
            action = Action.FIRE;
        }
    }

    private void randomDirection() {
        Direction direction = Direction.values()[random.nextInt(Direction.values().length - 1)];
        tank.setDirection(direction);
    }
}
