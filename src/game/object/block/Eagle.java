package game.object.block;

import java.awt.*;

import static game.action.Engine.IMAGE;
import static game.battlefield.BattleField.SQUARE_SIZE;

public class Eagle extends AbstractBlock {

    public Eagle(int x, int y) {
//        super(x, y, 1244, 128);
        super(x, y, 304, 32);
    }

    @Override
    public void draw(Graphics g) {
        drawEmptyBlock(g);
        super.draw(g);
    }

    @Override
    protected void doRemove(Graphics g) {
        drawEmptyBlock(g);
//        g.drawImage(IMAGE, x, y, x + SQUARE_SIZE, y + SQUARE_SIZE, 1308, 128, 1308 + SQUARE_SIZE, 128 + SQUARE_SIZE, null);
        g.drawImage(IMAGE, x, y, x + SQUARE_SIZE, y + SQUARE_SIZE, 320, 32, 320 + imageSize, 32 + imageSize, null);
    }
}
