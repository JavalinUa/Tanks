package game.object.powerup;

import game.object.BFObject;

public interface PowerUp extends BFObject {

    void activate(game.object.tank.Tank tank);
}
