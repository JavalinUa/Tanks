package game.action;

import game.object.BFObject;
import game.object.block.Void;
import game.object.powerup.PowerUp;
import game.object.tank.Action;
import game.object.tank.Direction;
import game.object.tank.Tank;
import game.panel.battle.BattleFieldPanel;

import static game.battlefield.BattleField.*;

public class TankActionsThread extends Thread {

    private final Tank tank;
    private final BattleFieldPanel battleFieldPanel;

    private Action lastAction;
    private Direction lastDirection;
//    private final TankRecordActions tankRecordActions;

    public TankActionsThread(Tank tank, BattleFieldPanel battleFieldPanel) {
        this.tank = tank;
        this.battleFieldPanel = battleFieldPanel;
        this.lastAction = tank.getAction();
        this.lastDirection = tank.getDirection();
//        this.tankRecordActions = engine.getTankRecordActions();
    }

    @Override
    public void run() {
//        if (engine.isWriteRecord()) {
        if (true) {
            writeGame();
        } else {
            readGame();
        }

        System.out.println("Destroyed: " + tank.getClass().getSimpleName());
    }

    private void writeGame() {
        while (!interrupted() && !tank.isDestroyed() && battleFieldPanel.isGameInProgress()) {
            if (tank.isSpawned() && tank.getAi() != null && tank.getAction() != Action.STOP) {
                tank.getAi().action();
//                System.out.println("acting");
            }

            if (tank.getAction() != lastAction || tank.getDirection() != lastDirection) {
//                tankRecordActions.write(tank);
                lastAction = tank.getAction();
                lastDirection = tank.getDirection();
            }

            tank.setActing(true);

            if (Action.MOVE.equals(tank.getAction())) {
//                System.out.println(System.currentTimeMillis() + " " + tank.getDirection());
                processMove();
            } else if (Action.FIRE.equals(tank.getAction()) && tank.isRechargeEnded()) {
//                System.out.println("Fired");
                processFire();
            }

            tank.setActing(false);

            Engine.sleep(50);
        }
    }

    private void readGame() {
        while (!interrupted() && battleFieldPanel.isGameInProgress()) {
//            tank.setActing(true);

            if (Action.MOVE.equals(tank.getAction())) {
//                System.out.println(System.currentTimeMillis() + " " + tank.getDirection());
                processMove();
            } else if (Action.FIRE.equals(tank.getAction()) && tank.isRechargeEnded()) {
//                System.out.println("Fired");
                processFire();
            }

//            tank.setActing(false);

            Engine.sleep(50);
        }
    }

    private void processMove() {
        int v = tank.getY() / SQUARE_SIZE;
        int h = tank.getX() / SQUARE_SIZE;

        if (Direction.UP == tank.getDirection()) {
            v--;
        } else if (Direction.DOWN == tank.getDirection()) {
            v++;
        } else if (Direction.RIGHT == tank.getDirection()) {
            h++;
        } else if (Direction.LEFT == tank.getDirection()) {
            h--;
        }

        if (isAbsentLimitsCollision() && isEmptySquareToMove(v, h)) {
            activatePowerUp(v, h);
            moveTank();
        }
    }

    private boolean isEmptySquareToMove(int v, int h) {
        return isAbsentTankCollision(v, h) && isAbsentBlockCollision(v, h);
    }

    private boolean isAbsentTankCollision(int v, int h) {
        for (Tank t : battleFieldPanel.getTanks()) {
            if (tank != t && !t.isDestroyed() &&
                    Math.abs(h * SQUARE_SIZE - t.getX()) < SQUARE_SIZE &&
                    Math.abs(v * SQUARE_SIZE - t.getY()) < SQUARE_SIZE) {
                return false;
            }
        }
        return true;
    }

    private boolean isAbsentBlockCollision(int v, int h) {
        BFObject block = battleFieldPanel.getBlock(v, h);

        return block instanceof Void || block.isDestroyed();
    }

    private boolean isAbsentLimitsCollision() {
        return (Direction.UP != tank.getDirection() || tank.getY() != MIN_SQUARE)
                && (Direction.DOWN != tank.getDirection() || tank.getY() < HEIGHT_MAX_SQUARE)
                && (Direction.LEFT != tank.getDirection() || tank.getX() != MIN_SQUARE)
                && (Direction.RIGHT != tank.getDirection() || tank.getX() < WIDTH_MAX_SQUARE);
    }

    private void activatePowerUp(int v, int h) {
        PowerUp powerUp = battleFieldPanel.findPowerUp(h * SQUARE_SIZE, v * SQUARE_SIZE);

        if (powerUp != null) {
            powerUp.activate(tank);
        }
    }

    private void moveTank() {
        int i = 0;

        while (i++ < SQUARE_SIZE && !tank.isDestroyed()) {
            tank.move();
            Engine.sleep(Tank.MAX_SPEED / tank.getSpeed());
        }
    }

    private void processFire() {
        new BulletActionsThread(battleFieldPanel, tank.fire()).start();
    }
}
