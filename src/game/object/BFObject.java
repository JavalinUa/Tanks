package game.object;

import java.awt.*;

public interface BFObject {

    int POINTS_IMAGE_WIDTH = 16;
    int POINTS_IMAGE_HEIGHT = 7;
    int POINTS_IMAGE_START_X = 288;
    int POINTS_IMAGE_START_Y = 164;
    int POINTS_IMAGE_DELAY = 1000;

    void draw(Graphics g);

    void setImageCoordinates(int imageX, int imageY);

    void destroy();

    int getSize();

    boolean isDestroyed();

    int getX();

    int getY();
}
