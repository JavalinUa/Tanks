package game.object.tank;

import game.ai.AI;
import game.object.BFObject;

public interface Tank extends BFObject {

    int MAX_SPEED = 100;

    String DEFENDER_TEAM = "DEFENDER_TEAM";
    String AGGRESSIVE_TEAM = "AGGRESSIVE_TEAM";

    void setX(int x);

    void setY(int y);

    boolean isSpawned();

    void setSpawned(boolean spawned);

    void setActing(boolean acting);

    void setAction(Action action);

    Action getAction();

    Bullet fire();

    boolean isRechargeEnded();

    void setOwnBulletsCount(int ownBulletsCount);

    void removeBullet();

    void setDirection(Direction direction);

    Direction getDirection();

    void move();

    void moveVertical(int y);

    void moveHorizontal(int x);

    int getSpeed();

    String getTeam();

    boolean isSameTeam(Tank tank);

    int getImagesY();

    void setAi(AI ai);

    AI getAi();

    String getTeamPosition();

    int getPointsMultiplier();

    void setBulletSpeedMultiplier(int bulletSpeedMultiplier);
    int getBulletSpeedMultiplier();

    void initFlashing();

    void removePoints();

    void initForceField();

    void setArmor(int armor);

    void setDestroyRock(boolean destroyRock);

    boolean isDestroyRock();
}
