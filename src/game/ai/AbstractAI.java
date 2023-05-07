package game.ai;

import game.action.Engine;
import game.object.BFObject;
import game.object.tank.Action;
import game.object.tank.Tank;

import java.util.Random;

public abstract class AbstractAI implements AI {

    protected Action action;
    protected final Tank tank;
    protected final Engine engine;
    protected final BFObject target;

    protected final Random random = new Random();

    public AbstractAI(Tank tank, Engine engine, BFObject target) {
        this.tank = tank;
        this.engine = engine;
        this.target = target;
    }
}
