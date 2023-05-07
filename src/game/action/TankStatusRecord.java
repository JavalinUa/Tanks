package game.action;

import game.object.tank.Action;
import game.object.tank.Direction;
import game.object.tank.Tank;

import java.io.Serializable;

public class TankStatusRecord implements Serializable {

    private final long time;
    private final Action action;
    private final Direction direction;
    private final String teamPosition;

    public TankStatusRecord(long time, Tank tank) {
        this.time = time;
        this.action = tank.getAction();
        this.direction = tank.getDirection();
        this.teamPosition = tank.getTeamPosition();
    }

    public long getTime() {
        return time;
    }

    public Action getAction() {
        return action;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getTeamPosition() {
        return teamPosition;
    }
}
