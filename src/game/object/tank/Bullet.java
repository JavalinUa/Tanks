package game.object.tank;

import game.object.AbstractBFObject;

import java.awt.*;
import java.util.List;

import static game.action.Engine.IMAGE;

public class Bullet extends AbstractBFObject {

    private final static int SPEED = 3;
    private final Tank tank;
    private final Direction direction;
    private final List<Bullet> bullets;

    public static final int BULLET_SIZE = 14;
    //    public static final int BULLET_IMAGE_Y = 408;
    public static final int BULLET_IMAGE_Y = 102;

    public Bullet(int x, int y, Direction direction, Tank tank, List<Bullet> bullets) {
        this.x = x;
        this.y = y;
        this.tank = tank;
        this.direction = direction;
        this.bullets = bullets;
    }

    @Override
    protected void doRemove(Graphics g) {
        tank.removeBullet();
        bullets.remove(this);
    }

    @Override
    protected void doDraw(Graphics g) {
        g.drawImage(IMAGE, x, y, x + BULLET_SIZE, y + BULLET_SIZE,
                direction.getBulletX(), BULLET_IMAGE_Y,
                direction.getBulletX() + direction.getBulletWidth(), BULLET_IMAGE_Y + direction.getBulletHeight(),
                null);
    }

    public int getSpeed() {
        return SPEED;
    }

    public Tank getTank() {
        return tank;
    }

    public Direction getDirection() {
        return direction;
    }

    public void move() {
        direction.move(this);
    }

    public void updateX(int x) {
        this.x += x;
    }

    public void updateY(int y) {
        this.y += y;
    }

    @Override
    public int getSize() {
        return BULLET_SIZE;
    }
}
