package game.object.tank;

import game.ai.AI;
import game.battlefield.BattleField;
import game.object.AbstractBFObject;
import game.panel.battle.BattleFieldPanel;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static game.action.Engine.IMAGE;

public abstract class AbstractTank extends AbstractBFObject implements Tank {

    private static final int SPAWNED_IMAGE_DELAY = 50;
    private static final int SPAWNED_Y = 96;
    private static final int[] SPAWNED_X_ORDER = new int[]{304, 288, 272, 256, 272, 288, 304, 288, 272, 256, 272, 288, 304};

    private static final int FLASHING_DELAY = 100;
    private static final int FLASHING_PERIOD = 30_000;
    private static final TankColor FLASHING_COLOR = TankColor.RED;

    private static final int FORCE_FIELD_SIZE = 16;
    private static final int FORCE_FIELD_DELAY = 300;
    private static final int FORCE_FIELD_PERIOD = 10_000;
    private static final int FORCE_FIELD_IMAGE_Y = 144;
    private static final int FORCE_FIELD_FIRST_IMAGE_X = 256;
    private static final int FORCE_FIELD_SECOND_IMAGE_X = 272;

    protected long spawnedImageTime;
    protected int spawnedXOrderIdx = 0;
    private boolean flashing;
    private long flashingTime;
    private long forceFieldActivatedBorderTime;
    private boolean changedFlashingColor;
    private long pointsImageTime;
    private final int directionDelay = 1000;
    private long lastDirectionMillis = System.currentTimeMillis();

    protected int speed = 0;
    protected int armor = 0;
    protected int bulletSpeedMultiplier = 1;
    protected Action action;
    protected Queue<Bullet> ownBullets = new ConcurrentLinkedQueue<>();
    protected int ownBulletsCount = 1;
    protected long recharge;
    protected int imageIdx = 0;
    protected boolean acting = false;
    protected boolean spawned = false;
    protected Direction direction;
    protected TankColor tankColor;
    protected final String team;
    protected final String teamPosition;
    protected final BattleFieldPanel battleFieldPanel;
    protected final List<Tank> tanks;
    protected final List<Tank> aggressorTanks;
    protected final List<Bullet> bullets;

    protected AI ai;
    protected int pointsMultiplier;
    protected boolean destroyRock;

    private static int TANK_WIDTH;
    private static int TANK_HEIGHT = TANK_WIDTH = BattleField.SQUARE_SIZE;

    public AbstractTank(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction,
                        TankColor tankColor) {
        this.x = x;
        this.y = y;
        this.team = team;
        this.battleFieldPanel = battleFieldPanel;
        this.teamPosition = teamPosition;
        this.tanks = battleFieldPanel.getTanks();
        this.aggressorTanks = battleFieldPanel.getAggressiveTanks();
        this.bullets = battleFieldPanel.getBullets();
        this.direction = direction;
        this.tankColor = tankColor;
        MAX_EXPLOSIONS = 5;
    }

    @Override
    public void move() {
        if (imageIdx == 0) {
            imageIdx = 1;
        } else {
            imageIdx = 0;
        }
        direction.move(this);
    }

    @Override
    protected void doDraw(Graphics g) {
        if (spawned) {
            drawTank(g);
        } else {
            drawRespawn(g);
        }
    }

    private void drawRespawn(Graphics g) {
        if (spawnedImageTime == 0) {
            spawnedImageTime = System.currentTimeMillis() + SPAWNED_IMAGE_DELAY;
        } else {
            if (spawnedXOrderIdx < SPAWNED_X_ORDER.length) {
                g.drawImage(IMAGE, x, y, x + TANK_WIDTH, y + TANK_HEIGHT,
                        SPAWNED_X_ORDER[spawnedXOrderIdx], SPAWNED_Y,
                        SPAWNED_X_ORDER[spawnedXOrderIdx] + 16, SPAWNED_Y + 16, null);

                if (spawnedImageTime < System.currentTimeMillis()) {
                    spawnedImageTime = System.currentTimeMillis() + SPAWNED_IMAGE_DELAY;
                    spawnedXOrderIdx++;
                }
            } else {
                spawned = true;
            }
        }
    }

    private void drawTank(Graphics g) {
        int imageX = imageIdx == 0 ? direction.getFirstTankImageX() : direction.getSecondTankImageX();
//        imageX += 128;

        int verticalOffset = Direction.LEFT.equals(direction) || Direction.RIGHT.equals(direction) ?
                getVerticalImageOffset() : 0;

        if (flashing) {
            doFlashing();
        }

        TankColor currentColor = changedFlashingColor ? FLASHING_COLOR : tankColor; //Why did I do that?

        g.drawImage(IMAGE, x, y, x + TANK_WIDTH, y + TANK_HEIGHT,
                currentColor.getBaseX() + imageX, currentColor.getBaseY() + getImagesY(),
                currentColor.getBaseX() + imageX + direction.getTankImageWidth(),
                currentColor.getBaseY() + getImagesY() + direction.getTankImageHeight() + verticalOffset,
                null);

        if (forceFieldActivatedBorderTime > System.currentTimeMillis()) {
            int forceFieldX = System.currentTimeMillis() % FORCE_FIELD_DELAY < FORCE_FIELD_DELAY / 2
                    ? FORCE_FIELD_FIRST_IMAGE_X : FORCE_FIELD_SECOND_IMAGE_X;

            g.drawImage(IMAGE, x, y, x + TANK_WIDTH, y + TANK_HEIGHT,
                    forceFieldX, FORCE_FIELD_IMAGE_Y, forceFieldX + FORCE_FIELD_SIZE, FORCE_FIELD_IMAGE_Y + FORCE_FIELD_SIZE,
                    null);
        }
    }

    private void doFlashing() {
        if (flashingTime == 0) {
            flashingTime = System.currentTimeMillis() + FLASHING_PERIOD;
        } else if (flashingTime > System.currentTimeMillis()) {
            if (System.currentTimeMillis() % FLASHING_DELAY < FLASHING_DELAY / 2) {
                changedFlashingColor = !changedFlashingColor;
            }
        } else {
            flashing = false;
            changedFlashingColor = false;
        }
    }

    @Deprecated
    public int getVerticalImageOffset() {
        return 0;
    }

    @Override
    protected void doRemove(Graphics g) {
        if (pointsMultiplier > 0) {
            drawPoints(g);
        } else {
            if (flashing) {
                battleFieldPanel.spawnPowerUp();
            }

            tanks.remove(this);

            if (AGGRESSIVE_TEAM.equals(team)) {
                aggressorTanks.remove(this);
            }
        }
    }

    private void drawPoints(Graphics g) {
        if (pointsImageTime == 0) {
            pointsImageTime = System.currentTimeMillis() + POINTS_IMAGE_DELAY;
        } else if (pointsImageTime > System.currentTimeMillis()) {
            int pointsX = x + TANK_WIDTH / 2 - POINTS_IMAGE_WIDTH;
            int pointsY = y + TANK_HEIGHT / 2 - POINTS_IMAGE_HEIGHT;
            int pointsImageX = (getPointsMultiplier() - 1) * POINTS_IMAGE_WIDTH + POINTS_IMAGE_START_X;

            g.drawImage(IMAGE, pointsX - 5, pointsY, pointsX - 5 + POINTS_IMAGE_WIDTH * 3, pointsY + POINTS_IMAGE_HEIGHT * 3,
                    pointsImageX, POINTS_IMAGE_START_Y,
                    pointsImageX + POINTS_IMAGE_WIDTH, POINTS_IMAGE_START_Y + POINTS_IMAGE_HEIGHT, null);
        } else {
            pointsMultiplier = 0;
        }
    }

    @Override
    public void destroy() {
        if (spawned) {
            setExplosionDelay();

            if (armor > 0 && !flashing) {
                armor--;
            } else if (forceFieldActivatedBorderTime < System.currentTimeMillis()) {
                destroyed = true;
            }
        }
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    @Override
    public void setActing(boolean acting) {
        this.acting = acting;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Bullet fire() {
        int bulletX;
        int bulletY;

        if (direction == Direction.UP) {
            bulletX = x + 25;
            bulletY = y - 15;
        } else if (direction == Direction.DOWN) {
            bulletX = x + 25;
            bulletY = y + 65;
        } else if (direction == Direction.LEFT) {
            bulletX = x - 15;
            bulletY = y + 25;
        } else { //RIGHT
            bulletX = x + 65;
            bulletY = y + 25;
        }
        recharge = System.currentTimeMillis() + 500;

//        return new Bullet(bulletX, bulletY, direction, this, bullets);
        Bullet newBullet = new Bullet(bulletX, bulletY, direction, this, bullets);
        ownBullets.add(newBullet);
        return newBullet;
    }

    @Override
    public boolean isRechargeEnded() {
        return recharge < System.currentTimeMillis() && ownBullets.size() < ownBulletsCount;
    }

    @Override
    public void setOwnBulletsCount(int ownBulletsCount) {
        this.ownBulletsCount = ownBulletsCount;
    }

    @Override
    public void removeBullet() {
        ownBullets.remove();
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        if (!acting && (ai == null || lastDirectionMillis < System.currentTimeMillis())) {
            lastDirectionMillis = System.currentTimeMillis() + directionDelay;

            this.direction = direction;
        } else {
//            System.out.println("Not");
        }
    }

    @Override
    public void moveVertical(int y) {
        this.y += y;
    }

    @Override
    public void moveHorizontal(int x) {
        this.x += x;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public String getTeam() {
        return team;
    }

    @Override
    public boolean isSameTeam(Tank tank) {
        return team.equals(tank.getTeam());
    }

    @Override
    public int getSize() {
        return TANK_WIDTH;
    }

    @Override
    public void setAi(AI ai) {
        this.ai = ai;
    }

    @Override
    public AI getAi() {
        return ai;
    }

    @Override
    public String getTeamPosition() {
        return teamPosition;
    }

    @Override
    public int getPointsMultiplier() {
        return pointsMultiplier;
    }

    @Override
    public void setBulletSpeedMultiplier(int bulletSpeedMultiplier) {
        this.bulletSpeedMultiplier = bulletSpeedMultiplier;
    }

    @Override
    public int getBulletSpeedMultiplier() {
        return bulletSpeedMultiplier;
    }

    @Override
    public void initFlashing() {
        flashing = true;
    }

    @Override
    public void removePoints() {
        pointsMultiplier = 0;
    }

    @Override
    public void initForceField() {
        forceFieldActivatedBorderTime = System.currentTimeMillis() + FORCE_FIELD_PERIOD;
    }

    @Override
    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public boolean isDestroyRock() {
        return destroyRock;
    }

    @Override
    public void setDestroyRock(boolean destroyRock) {
        this.destroyRock = destroyRock;
    }
}
