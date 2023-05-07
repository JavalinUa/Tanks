package game.object.powerup;

import game.battlefield.BattleField;
import game.object.tank.Tank;
import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

import java.awt.*;

import static game.action.Engine.IMAGE;

public abstract class AbstractPowerUp implements PowerUp {

    private static final int IMAGE_Y = 112;
    private static final int IMAGE_WIDTH = 15;
    private static final int IMAGE_HEIGHT = 14;
    private static final int IMAGE_FLASHING_DELAY = 500;

    private final int x;
    private final int y;
    protected final BattleFieldPanel battleFieldPanel;

    private boolean activated;
    private long pointsImageTime;

    public AbstractPowerUp(int x, int y, BattleFieldPanel battleFieldPanel) {
        this.x = x;
        this.y = y;
        this.battleFieldPanel = battleFieldPanel;
    }

    @Override
    public void draw(Graphics g) {
        if (activated) {
            drawPoints(g);
        } else if (System.currentTimeMillis() % IMAGE_FLASHING_DELAY < IMAGE_FLASHING_DELAY / 2) {
            g.drawImage(IMAGE, x, y, x + getSize(), y + getSize(),
                    getImageX(), IMAGE_Y, getImageX() + IMAGE_WIDTH, IMAGE_Y + IMAGE_HEIGHT,
                    null);
        }
    }

    private void drawPoints(Graphics g) {
        if (pointsImageTime == 0) {
            pointsImageTime = System.currentTimeMillis() + POINTS_IMAGE_DELAY;
        } else if (pointsImageTime > System.currentTimeMillis()) {
            int pointsX = x + BattleField.SQUARE_SIZE / 2 - POINTS_IMAGE_WIDTH;
            int pointsY = y + BattleField.SQUARE_SIZE / 2 - POINTS_IMAGE_HEIGHT;
            int pointsImageX = 4 * POINTS_IMAGE_WIDTH + POINTS_IMAGE_START_X;

            g.drawImage(IMAGE, pointsX - 5, pointsY, pointsX - 5 + POINTS_IMAGE_WIDTH * 3, pointsY + POINTS_IMAGE_HEIGHT * 3,
                    pointsImageX, POINTS_IMAGE_START_Y,
                    pointsImageX + POINTS_IMAGE_WIDTH, POINTS_IMAGE_START_Y + POINTS_IMAGE_HEIGHT, null);
        } else {
            battleFieldPanel.removePowerUp(this);
        }
    }

    abstract int getImageX();

    @Override
    public void setImageCoordinates(int imageX, int imageY) {
    }

    @Override
    public void activate(Tank tank) {
        activated = true;
        if (tank instanceof Player) {
            execute((Player) tank);
        }
    }

    abstract void execute(Player player);

    @Override
    public void destroy() {
    }

    @Override
    public int getSize() {
        return BattleField.SQUARE_SIZE;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
