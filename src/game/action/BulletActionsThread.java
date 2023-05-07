package game.action;

import game.battlefield.BattleField;
import game.object.BFObject;
import game.object.block.Rock;
import game.object.block.Void;
import game.object.block.Water;
import game.object.tank.Bullet;
import game.object.tank.Direction;
import game.object.tank.Tank;
import game.panel.battle.BattleFieldPanel;

import java.util.List;

public class BulletActionsThread extends Thread {

    private final BattleFieldPanel battleFieldPanel;
    private final Bullet bullet;
    private final List<Tank> tanks;
    private final List<Bullet> bullets;

    public BulletActionsThread(BattleFieldPanel battleFieldPanel, Bullet bullet) {
        this.battleFieldPanel = battleFieldPanel;
        this.bullet = bullet;
        this.tanks = battleFieldPanel.getTanks();
        this.bullets = battleFieldPanel.getBullets();
        bullets.add(bullet);
    }

    @Override
    public void run() {
        while (true) {
            bullet.move();

            if (isDestroyed()) {
                break;
            }
            Engine.sleep(10 / ((long) bullet.getSpeed() * bullet.getTank().getBulletSpeedMultiplier()));
        }
    }

    private boolean isDestroyed() {
        return isBorderCollision() || isAnotherBulletCollision() || isTankCollision() || isBlockCollision();
    }

    private boolean isBorderCollision() {
        boolean destroyed;

        if (bullet.getDirection() == Direction.UP) {
            destroyed = bullet.getY() == 0;
        } else if (bullet.getDirection() == Direction.DOWN) {
            destroyed = bullet.getY() + Bullet.BULLET_SIZE == BattleField.HEIGHT;
        } else if (bullet.getDirection() == Direction.LEFT) {
            destroyed = bullet.getX() == 0;
        } else {
            destroyed = bullet.getX() + Bullet.BULLET_SIZE == BattleField.WIDTH;
        }

        if (destroyed) {
            bullet.destroy();
        }
        return destroyed;
    }

    private boolean isAnotherBulletCollision() {
        for (Bullet otherBullet : bullets) {
            if (bullet != otherBullet && Math.abs(bullet.getX() - otherBullet.getX()) <
                    Bullet.BULLET_SIZE && Math.abs(bullet.getY() - otherBullet.getY()) < Bullet.BULLET_SIZE) {
                bullet.destroy();
                otherBullet.destroy();
                return true;
            }
        }
        return false;
    }

    private boolean isTankCollision() {
        for (Tank tank : tanks) {
            if (tank.isSpawned() && !tank.isDestroyed() && !bullet.getTank().isSameTeam(tank) &&
                    Math.abs((bullet.getX() + 7) - (tank.getX() + 32)) < 39 &&
                    Math.abs((bullet.getY() + 7) - (tank.getY() + 32)) < 39) {
                tank.destroy();
                bullet.destroy();
                return true;
            }
        }
        return false;
    }

    private boolean isBlockCollision() {
        int v = bullet.getX() / BattleField.SQUARE_SIZE;
        int h = bullet.getY() / BattleField.SQUARE_SIZE;

        if (v >= 0 && v < 9 && h >= 0 && h < 9) {
            BFObject block = battleFieldPanel.getBlock(h, v);
            if (bullet.getTank().isDestroyRock()) {
                if (!block.isDestroyed() && !(block instanceof Void) && !(block instanceof Water)) {
                    battleFieldPanel.destroyBlock(h, v);
                    bullet.destroy();
                    return true;
                }
            } else {
                if (!block.isDestroyed() && !(block instanceof Void) && !(block instanceof Water)) {
                    if (!(block instanceof Rock)) {
                        battleFieldPanel.destroyBlock(h, v);
                    }
                    bullet.destroy();
                    return true;
                }
            }
        }
        return false;
    }
}
