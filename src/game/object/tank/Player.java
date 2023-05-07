package game.object.tank;

import game.panel.battle.BattleFieldPanel;

import java.awt.*;

public class Player extends AbstractTank {

    private int startX;
    private int startY;
    private Direction startDirection;
    private int tierIdx;
    private int life = 1;

    public Player(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.YELLOW);
        speed = 10;
        imageY = 0;

        initStartPosition();
    }

    private void initStartPosition() {
        startX = x;
        startY = y;
        startDirection = direction;
    }

    @Override
    protected void doRemove(Graphics g) {
        if (life > 1) {
            life--;
            respawnOnStartPosition();
        } else {
            super.doRemove(g);
        }
    }

    @Override
    public int getImagesY() {
        return imageY;
    }

    public int getTierIdx() {
        return tierIdx;
    }

    public void increaseTierIdx() {
        this.tierIdx++;
    }

    public void addExtraLife() {
        if (life < 9) {
            life++;
        }
    }

    public int getLife() {
        return life;
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed() && life > 0;
    }

    public void respawnOnStartPosition() {
        destroyed = false;
        x = startX;
        y = startY;
        direction = startDirection;
        spawned = false;
        spawnedImageTime = 0;
        spawnedXOrderIdx = 0;
        imageY = 0;
        bulletSpeedMultiplier = 1;
        ownBulletsCount = 1;
        destroyRock = false;
    }
}
