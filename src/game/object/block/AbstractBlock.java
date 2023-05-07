package game.object.block;

import game.object.AbstractBFObject;

import java.awt.*;

import static game.action.Engine.IMAGE;
import static game.battlefield.BattleField.SQUARE_SIZE;

public abstract class AbstractBlock extends AbstractBFObject {

    public AbstractBlock(int x, int y, int imageX, int imageY) {
        this.x = x;
        this.y = y;
        this.imageX = imageX;
        this.imageY = imageY;
    }

    @Override
    protected void doDraw(Graphics g) {
//        g.drawImage(IMAGE, x, y, x + imageSize, y + imageSize,
        g.drawImage(IMAGE, x, y, x + SQUARE_SIZE, y + SQUARE_SIZE,
                imageX, imageY, imageX + imageSize, imageY + imageSize, null);
    }

    @Override
    protected void doRemove(Graphics g) {
        drawEmptyBlock(g);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public int getSize() {
        return SQUARE_SIZE;
    }
}
