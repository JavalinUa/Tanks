package game.object.block;

import java.awt.*;

public class Water extends AbstractBlock {

    //    private static final int WATER_Y = 192;
    private static final int WATER_Y = 48;
    private static final int IMAGE_DELAY = 1000;
    //    private static final int FIRST_WATER_X = 1052;
    private static final int FIRST_WATER_X = 256;
    //    private static final int SECOND_WATER_X = 1116;
    private static final int SECOND_WATER_X = 272;

    public Water(int x, int y) {
        super(x, y, FIRST_WATER_X, WATER_Y);
    }

    @Override
    public void draw(Graphics g) {
        if (System.currentTimeMillis() % IMAGE_DELAY * 2 > IMAGE_DELAY) {
            imageX = FIRST_WATER_X;
        } else {
            imageX = SECOND_WATER_X;
        }
        super.draw(g);
    }
}
