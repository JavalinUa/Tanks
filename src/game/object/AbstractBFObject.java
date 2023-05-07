package game.object;

import java.awt.*;

import static game.action.Engine.IMAGE;
import static game.battlefield.BattleField.SQUARE_SIZE;

public abstract class AbstractBFObject implements BFObject {

    protected int x;
    protected int y;
    protected boolean destroyed = false;

    protected long explosionTime;
    protected int explosionIdx = 0;
    //    protected int explosionX = 1052;
    protected int explosionX = 256;
    //    protected int explosionY = 510;
    protected int explosionY = 128;
    protected final int explosionDelay = 200;
    protected int MAX_EXPLOSIONS = 3;

    protected int imageX;
    protected int imageY;
    protected int imageSize = 16;

    @Override
    public void draw(Graphics g) {
        if (destroyed) {
            if (MAX_EXPLOSIONS > explosionIdx + 1) {
                doExplosion(g);
            } else {
                doRemove(g);
            }
        } else {
            doDraw(g);
        }
    }

    protected abstract void doDraw(Graphics g);

    protected abstract void doRemove(Graphics g);

    protected void doExplosion(Graphics g) {
        drawEmptyBlock(g);

        int explosionSize = SQUARE_SIZE / 2 + (SQUARE_SIZE / 4 * explosionIdx);
//        int imagePosition = explosionIdx < 3 ? SQUARE_SIZE * explosionIdx : SQUARE_SIZE * 3 + (SQUARE_SIZE * 2 * (explosionIdx - 3));
        int imagePosition = explosionIdx < 3 ? imageSize * explosionIdx : imageSize * 3 + (imageSize * 2 * (explosionIdx - 3));
//        int imageSize = explosionIdx < 3 ? SQUARE_SIZE : SQUARE_SIZE * 2;
        int imageDisplaySize = explosionIdx < 3 ? imageSize : imageSize * 2;

        g.drawImage(IMAGE, (x + getSize() / 2) - explosionSize / 2, (y + getSize() / 2) - explosionSize / 2,
                ((x + getSize() / 2) - explosionSize / 2) + explosionSize, ((y + getSize() / 2) - explosionSize / 2) + explosionSize,
                explosionX + imagePosition, explosionY,
                explosionX + imagePosition + imageDisplaySize, explosionY + imageDisplaySize, null);

        if (explosionTime < System.currentTimeMillis()) {
            setExplosionDelay();
            explosionIdx++;
        }
    }

    protected void drawEmptyBlock(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, getSize(), getSize());
    }

    @Override
    public void setImageCoordinates(int imageX, int imageY) {
        this.imageX = imageX;
        this.imageY = imageY;
    }

    @Override
    public void destroy() {
        setExplosionDelay();

        destroyed = true;
    }

    protected void setExplosionDelay() {
        explosionTime = System.currentTimeMillis() + explosionDelay;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
