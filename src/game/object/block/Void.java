package game.object.block;

import java.awt.*;

public class Void extends AbstractBlock {

    public Void(int x, int y) {
        super(x, y, 0, 0);
    }

    @Override
    public void draw(Graphics g) {
        drawEmptyBlock(g);
    }
}
